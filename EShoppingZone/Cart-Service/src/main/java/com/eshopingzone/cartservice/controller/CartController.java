package com.eshopingzone.cartservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.service.CartService;

@RestController
@RequestMapping("/eshoppingzone/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// adding products to car
	@PostMapping("/addProducts")
	public ResponseEntity<Void> addProductsToCart(@RequestParam("userId") int userId,
			@RequestParam("productId") int productId, @RequestParam String productName,
			@RequestParam String productImage, @RequestParam BigDecimal price, @RequestParam("quantity") int quantity) {

		cartService.addOrUpdateProductsInCart(userId, productId, productName, productImage, price, quantity);
		return new ResponseEntity<>(HttpStatus.OK);
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
