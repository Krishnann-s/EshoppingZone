package com.eshopingzone.cartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.eshopingzone.cartservice.payload.ProductDTO;

@FeignClient(name = "product-service", url = "http://localhost:8002") 
// Using Feign client to communicate with product service
public interface ProductClient {

	@GetMapping("/api/public/products/{productId}")
	ProductDTO getProductById(@PathVariable Long productId, @RequestHeader("Authorization") String authHeader);
	
	@PutMapping("/api/products")
	void updateProduct(@RequestBody ProductDTO productDto);
}
