package com.eshopingzone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.eshopingzone.payload.CartDTO;

@FeignClient(name = "cart-service")
public interface CartClient {

	@GetMapping("/api/carts/user/{email}")
    CartDTO getCartByEmail(@PathVariable String email, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
    
    @DeleteMapping("/api/carts/user/{email}/products")
    String deleteProductsForUserByEmail(@PathVariable String email, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
