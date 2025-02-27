package com.eshopingzone.cartservice.service;


import java.math.BigDecimal;
import java.util.List;

import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.payload.CartDTO;


public interface CartService {

	CartDTO addProductsToCart(Long productId, int quantity);
	List<CartItem> getCartByUserId(int userId);
	void decreaseProductQuantity(int userId, int productId);
	void deleteProductFromCart(int userId, int productId);
	void emptyCart(int userId);
}
