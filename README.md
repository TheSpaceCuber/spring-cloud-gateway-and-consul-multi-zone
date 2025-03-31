# Spring Cloud Gateway + Consul in a multi-zone environment with locality aware routing

## Deployment with Kubernetes
Create consul cluster
```bash
kubectl apply -f kube/consul.yml
```
Create 2 namespaces to simulate 2 datacenters: dc1 and dc2
```bash
kubectl create namespace dc1
kubectl create namespace dc2
```
```bash
kubectl apply -f kube/gateway-dc1.yml
kubectl apply -f kube/gateway-dc2.yml
kubectl apply -f kube/microservice-a-dc1.yml
kubectl apply -f kube/microservice-b-dc1.yml
kubectl apply -f kube/microservice-a-dc2.yml
kubectl apply -f kube/microservice-b-dc2.yml
```
### Resource cleanup
```bash
kubectl delete -f kube/gateway-dc1.yml
kubectl delete -f kube/gateway-dc2.yml
kubectl delete -f kube/microservice-a-dc1.yml
kubectl delete -f kube/microservice-b-dc1.yml
kubectl delete -f kube/microservice-a-dc2.yml
kubectl delete -f kube/microservice-b-dc2.yml
```

```bash
kubectl delete namespace dc1
kubectl delete namespace dc2
```

## Details
For microservices to be able to connect to consul, we need to use `consul.default.svc.cluster.local` instead of 
just `consul` since consul is running in default namespace and not dc1 or dc2.