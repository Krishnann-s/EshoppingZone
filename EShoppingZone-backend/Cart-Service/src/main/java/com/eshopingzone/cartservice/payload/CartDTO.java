package com.eshopingzone.cartservice.payload;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

	private Long cartId;
	private double totalPrice = 0.0;
	private List<ProductDTO> products = new ArrayList<>(); 
	private List<CartItemDTO> cartItems = new ArrayList<>();
}
