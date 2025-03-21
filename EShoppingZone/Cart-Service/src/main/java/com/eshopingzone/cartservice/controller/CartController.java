package com.eshopingzone.cartservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.cartservice.model.Cart;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.repository.CartRepository;
import com.eshopingzone.cartservice.service.CartService;
import com.eshopingzone.cartservice.util.AuthUtil;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private AuthUtil authUtil;

	// Add Products to Cart
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductsToCart(@PathVariable Long productId, @PathVariable Integer quantity) {

		CartDTO cartDto = cartService.addProductsToCart(productId, quantity);
		return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
	}

	// Get All Carts
	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> getCarts() {

		List<CartDTO> cartDTOs = cartService.getAllCarts();
		return new ResponseEntity<>(cartDTOs, HttpStatus.FOUND);
	}

	// Get Cart by ID
	@GetMapping("/carts/users/cart")
	public ResponseEntity<CartDTO> getCartById() {

		Long profileId = authUtil.loggedInUserId();
		Cart cart = cartRepo.findCartByProfileId(profileId);
		Long cartId = cart.getCartId();

		CartDTO cartDto = cartService.getCart(profileId, cartId);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	// Update products in cart
	@PutMapping("/cart/products/{productId}/quantity/{operation}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {

		CartDTO cartDto = cartService.updateProductQuantityInCart(productId,
				operation.equalsIgnoreCase("delete") ? -1 : 1);

		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}
	
	// Delete products in Cart
	@DeleteMapping("/carts/{cartId}/product/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
														@PathVariable Long productId) {
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	@GetMapping("/test-auth")
	public ResponseEntity<String> testAuth(@RequestHeader HttpHeaders headers) {
		System.out.println("Received Headers: " + headers);
		return new ResponseEntity<String>("Auth Headers Received", HttpStatus.OK);
	}
	
}
