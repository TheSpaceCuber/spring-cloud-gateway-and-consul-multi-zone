package com.example.microservicea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    MicroserviceBClient bClient;

    public Controller(MicroserviceBClient bClient) {
        this.bClient = bClient;
    }

    @GetMapping("/")
    public String hello() {
        LOGGER.info("/ endpoint for Microservice A: Hello from Microservice A");
        return "Microservice A: Hello from Microservice A";
    }

    @GetMapping("/getHelloFromB")
    public String sendToB() {
        LOGGER.info("/getHelloFromB endpoint for Microservice A: Hello from Microservice B");
        return "Microservice A: " + bClient.hello();
    }
}
