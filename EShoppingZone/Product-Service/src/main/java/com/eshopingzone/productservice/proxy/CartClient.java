package com.eshopingzone.productservice.proxy;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cart-service")
public interface CartClient {

	@PostMapping("/eshoppingzone/cart/addProducts")
	void addProductsToCart(@RequestParam int userId, @RequestParam int productId, @RequestParam String productName,
			@RequestParam String productImage, @RequestParam BigDecimal price, @RequestParam int quantity);
	
	@DeleteMapping("/eshoppingzone/cart/deleteProduct")
	void deleteProductFromCart(@RequestParam int userId, @RequestParam int productId);
}
