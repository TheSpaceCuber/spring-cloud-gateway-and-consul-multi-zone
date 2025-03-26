package com.example.microserviceb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello() {
        return "Microservice B: Hello from Microservice B";
    }
}
