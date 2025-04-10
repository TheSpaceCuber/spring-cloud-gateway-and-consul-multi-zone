# Tencent Spring Framework Demo with Spring Cloud Gateway + Consul

## Prerequisites
- Docker Desktop with Kubernetes enabled
- Java 17
- Maven, settings.xml configured for Tencent dependencies as per [here](https://cloud.tencent.com/document/product/649/20231)

## Context
![Image](https://github.com/user-attachments/assets/61f595de-b2ab-47c3-85f5-96204b1f66ee)

Gateway directs traffic to microservice A.
### Microservice A
```java
@GetMapping("/")
public String hello() {
    return "Microservice A: Hello from Microservice A";
}

@GetMapping("/getHelloFromB")
public String sendToB() {
    return "Microservice A: " + bClient.hello();
}
```
Microservice A then makes a call to B.
### Microservice B
```java
@GetMapping("/")
public String hello() {
    return "Microservice B: Hello from Microservice B";
}
```


## Create JAR files and Docker images for each module
```bash
mvn clean package -DskipTests -f microservice-a/pom.xml
mvn clean package -DskipTests -f microservice-b/pom.xml
mvn clean package -DskipTests -f gateway/pom.xml

docker-compose build --no-cache
```

## Option 1: Running services without Kubernetes or Docker
You need consul running on localhost:8500. The easiest way is with docker.

```bash
docker run -d --name=consul-dev -p 8500:8500 hashicorp/consul agent -dev -client 0.0.0.0
```
You can now start each service with IntelliJ IDEA or your own IDE / CLI.


## Option 2: Deployment with Docker Compose

```bash
docker-compose up -d
```

Open consul at `localhost:8500`.
`localhost:8080/getHelloFromB` should return `Microservice A: Microservice B: Hello from Microservice B`.
Meaning that traffic went from Gateway -> Microservice A -> Microservice B and back.

## Option 3: Deployment with Kubernetes

Create 2 namespaces to simulate 2 datacenters: dc1 and dc2
```bash
kubectl create namespace dc1
kubectl create namespace dc2
```
2 folders for convenience:
- `kube-1-dc`: Deployed in default namespace - simulates 1 datacenter
- `kube-2-dc`: Deployed in dc1 and dc2 - simulates 2 datacenters
```bash
kubectl apply -f kube-1-dc/.
# or
kubectl apply -f kube-2-dc/.
```

Expose consul with port forwarding:
```bash
# access on web browser via localhost:8500
kubectl port-forward svc/consul 8500:8500 -n default
```
You should see all health checks pass in consul dashboard.

### Testing

#### Use nginx pod to test http calls
```bash
# create nginx pod and ssh into it
kubectl run nginx --image=nginx
kubectl exec pod/nginx -it -- /bin/bash
```

#### Single namespace
```bash
# returns "Microservice A: Hello from Microservice A"
curl http://gateway:8080/ 

# "returns Microservice A: Microservice B: Hello from Microservice B"
curl http://gateway:8080/getHelloFromB 
```

#### Double namespace (dc1 and dc2)
```bash
# returns "Microservice B: Hello from Microservice B"
curl http://gateway.dc1.svc.cluster.local:8080/getHelloFromB 

# scale down microservice-a in dc1 to 0
kubectl scale deployment/microservice-a --replicas=0 -n dc1

# at this point, you may get server error as the service is temporarily unavailable
# but in a few seconds the requests will be sent to microservice-a in dc2
# returns "Microservice A: Microservice B: Hello from Microservice B"
curl http://gateway.dc1.svc.cluster.local:8080/getHelloFromB 
```

### Resource cleanup
```bash
kubectl delete -f kube-1-dc/.
kubectl delete -f kube-2-dc/.
```

```bash
kubectl delete namespace dc1
kubectl delete namespace dc2
```

## Details
### Spring cloud gateway and consul dependencies
Do not use non tencent dependencies for spring cloud gateway and spring cloud consul.

### Connecting microservices to consul
For microservices to be able to connect to consul, we need to use `consul.default.svc.cluster.local` instead of 
just `consul` since consul is running in default namespace and not dc1 or dc2.

For some reason, to inject env variables for `tsf_consul_ip` and some other tsf related variables, 
we need to use JAVA_TOOL_OPTIONS.
```yaml
- name: JAVA_TOOL_OPTIONS
  value: >-
    -Dtsf_consul_ip=consul.default.svc.cluster.local
    -Dtsf_consul_port=8500
```

instead of

```yaml
# this would fail, testing shows that the microservice keeps defaulting to 127.0.0.1:8500 when trying to connect to consul
env:
  - name: TSF_CONSUL_IP
    value: "consul.default.svc.cluster.local"
  - name: TSF_CONSUL_PORT
    value: "8500"
```
It is possible that TSF SDK supports multiple config sources, likely in this order:
- System properties (-Dtsf_consul_ip=...)
- Environment variables (like TSF_CONSUL_IP)
- Spring Boot config files (application.yaml)
- Hardcoded defaults

If you pass values via application.yaml, they don’t take effect because:

In the Kubernetes runtime, TSF tries to read System.getProperty(...), and since no -D values were given, it defaults to something like 127.0.0.1.

And it might not even be wired to consult Spring's Environment the way you'd expect in a "Spring-native" config-driven app.
