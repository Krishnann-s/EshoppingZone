package com.eshopingzone.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "com.eshopingzone.profileservice")
@OpenAPIDefinition(
		info = @Info(
				title = "User Profile Microservice Rest API Documentation",
				description = "Eshoppingzone User Profile Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Krishnan S",
						email = "krishnan.spu@gmail.com"
						),
				license = @License(
						name = "Apache 2.0"
						)
				)
)
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

}
