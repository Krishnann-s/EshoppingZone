package com.eshopingzone.cartservice.service;


import java.util.List;

import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.payload.CartDTO;


public interface CartService {

	CartDTO addProductsToCart(Long productId, int quantity);
}
