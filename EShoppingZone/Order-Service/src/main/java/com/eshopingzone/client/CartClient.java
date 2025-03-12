package com.eshopingzone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eshopingzone.payload.CartDTO;

@FeignClient(name = "cart-service")
public interface CartClient {

	@GetMapping("/api/carts/user/{email}")
	CartDTO getCartByEmail(@PathVariable String email);
}
