package com.example.microservicea;

import org.springframework.boot.SpringApplication;

public class TestMicroserviceAApplication {

	public static void main(String[] args) {
		SpringApplication.from(MicroserviceAApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
