package com.eshopingzone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eshopingzone.payload.ProductDTO;

@FeignClient(name = "product-service")
public interface ProductClient {

	@GetMapping("/api/product/{productId}")
	ProductDTO getProductById(@PathVariable Long productId);
}
