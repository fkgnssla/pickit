package com.ssafy.pickit_gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PickitGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickitGatewayServiceApplication.class, args);
	}

}
