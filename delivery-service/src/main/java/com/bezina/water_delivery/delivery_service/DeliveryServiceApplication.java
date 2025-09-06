package com.bezina.water_delivery.delivery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bezina.water_delivery.delivery_service.DAO")
@EntityScan(basePackages = {
		"com.bezina.water_delivery.core.model.assignment",
		"com.bezina.water_delivery.core.model.enums"
})
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

}
