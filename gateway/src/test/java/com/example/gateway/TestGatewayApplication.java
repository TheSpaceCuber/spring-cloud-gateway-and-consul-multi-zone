package com.example.gateway;

import org.springframework.boot.SpringApplication;

public class TestGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.from(GatewayApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
