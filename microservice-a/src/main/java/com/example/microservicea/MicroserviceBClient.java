package com.example.microservicea;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "microservice-b:8082")
public interface MicroserviceBClient {
    @GetMapping("/")
    public String hello();
}
