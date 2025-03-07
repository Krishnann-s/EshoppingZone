package com.eshopingzone.cartservice.service;

import java.util.List;

import com.eshopingzone.cartservice.payload.CartDTO;


public interface CartService {

	CartDTO addProductsToCart(Long productId, int quantity);

	List<CartDTO> getAllCarts();

	CartDTO getCart(Long profileId, Long cartId);

	CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

	String deleteProductFromCart(Long cartId, Long productId);
}
