package com.example.microserviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.tsf.annotation.EnableTsf;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTsf
public class MicroserviceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceBApplication.class, args);
	}

}
