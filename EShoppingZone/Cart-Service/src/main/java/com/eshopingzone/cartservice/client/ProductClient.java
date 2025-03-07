package com.eshopingzone.cartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eshopingzone.cartservice.payload.ProductDTO;

@FeignClient(name = "product-service")
public interface ProductClient {

	@GetMapping("/api/products/{productId}")
	ProductDTO getProductById(@PathVariable Long productId);

	@PutMapping("/api/products")
    void updateProduct(@RequestBody ProductDTO productDto);
}
