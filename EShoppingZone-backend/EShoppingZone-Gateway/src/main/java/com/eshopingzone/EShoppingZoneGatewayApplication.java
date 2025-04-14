package com.eshopingzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class EShoppingZoneGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShoppingZoneGatewayApplication.class, args);

	}

	@Bean
	public RouteLocator eshoppingRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/eshoppingzone/user-service/**")
						.filters(f -> f.rewritePath("/eshoppingzone/user-service/(?<segment>.*)", "/${segment}")
								// how much time a particular request is sent and received can be monitored
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("userCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://USER-SERVICE"))
				.route(p -> p
						.path("/eshoppingzone/product-service/**")
						.filters(f -> f.rewritePath("/eshoppingzone/product-service/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryconfig -> retryconfig.setRetries(3)
										.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(1000), Duration.ofMillis(5000),2,true)))
						.uri("lb://PRODUCT-SERVICE"))
				.route(p -> p
						.path("/eshoppingzone/image-service/**")
						.filters(f -> f.rewritePath("/eshoppingzone/image-service/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://IMAGE-SERVICE"))
				.route(p -> p
						.path("/eshoppingzone/cart-service/**")
						.filters(f -> f.rewritePath("/eshoppingzone/cart-service/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CART-SERVICE")).build();
	}
}