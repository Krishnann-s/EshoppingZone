package com.eshopingzone.cartservice.controller;

import java.math.BigDecimal;
import java.util.List;

import com.eshopingzone.cartservice.payload.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	// adding products to car
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductsToCart(@PathVariable Long productId,
													 @PathVariable Integer quantity) {
		CartDTO cartDto = cartService.addProductsToCart(productId, quantity);
		return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
	}

	// view products in cart
	@GetMapping("/viewCart")
	public ResponseEntity<List<CartItem>> viewCart(@RequestParam int userId) {
		List<CartItem> cart = cartService.getCartByUserId(userId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	// delete products in cart
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<Void> deleteProductFromCart(@RequestParam int userId, @RequestParam int productId) {
		cartService.deleteProductFromCart(userId, productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @PostMapping("/decreaseQuantity")
    public ResponseEntity<Void> decreaseProductQuantity(@RequestParam int userId, @RequestParam int productId) {
        cartService.decreaseProductQuantity(userId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/emptyCart")
    public ResponseEntity<Void> emptyCart(@RequestParam int userId) {
        cartService.emptyCart(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
