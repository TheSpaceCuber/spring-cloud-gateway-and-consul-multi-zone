package com.example.microservicea;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "microservice-b")
public interface MicroserviceBClient {
    @GetMapping("/hello")
    public String hello();
}
