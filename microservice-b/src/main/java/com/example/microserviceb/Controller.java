package com.example.microserviceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/")
    public String hello() {
        LOGGER.info("/hello endpoint for Microservice B: Hello from Microservice B");
        return "Microservice B: Hello from Microservice B";
    }
}
