package com.example.microservicea;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    MicroserviceBClient bClient;

    public Controller(MicroserviceBClient bClient) {
        this.bClient = bClient;
    }

    @GetMapping("/")
    public String hello() {
        return "Microservice A: Hello from Microservice A";
    }

    @GetMapping("/getHelloFromB")
    public String sendToB() {
        return "Microservice A: " + bClient.hello();
    }
}
