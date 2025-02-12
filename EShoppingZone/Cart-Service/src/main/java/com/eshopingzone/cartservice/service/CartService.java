package com.eshopingzone.cartservice.service;


import java.math.BigDecimal;
import java.util.List;

import com.eshopingzone.cartservice.model.CartItem;


public interface CartService {

	void addOrUpdateProductsInCart(int userId, int productId, String productName, String productImage, BigDecimal productPrice, int quantity);
	List<CartItem> getCartByUserId(int userId);
	void decreaseProductQuantity(int userId, int productId);
	void deleteProductFromCart(int userId, int productId);
	void emptyCart(int userId);
}
