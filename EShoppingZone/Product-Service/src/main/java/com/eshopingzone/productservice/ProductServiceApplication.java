package com.eshopingzone.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {

	public static void main(String[] args) {
		 Dotenv dotenv = Dotenv.load();
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
