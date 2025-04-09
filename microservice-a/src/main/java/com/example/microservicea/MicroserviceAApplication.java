package com.example.microservicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableTsf
public class MicroserviceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAApplication.class, args);
	}

}
