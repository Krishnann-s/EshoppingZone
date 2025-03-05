package com.eshopingzone.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductsToCart(@PathVariable Long productId, 
													@PathVariable Integer quantity) {
		
		CartDTO cartDto = cartService.addProductsToCart(productId, quantity);
		return new ResponseEntity<CartDTO>(cartDto, HttpStatus.CREATED);
	}
}
