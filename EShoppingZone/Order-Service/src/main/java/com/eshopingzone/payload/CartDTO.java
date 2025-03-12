package com.eshopingzone.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

	private Long cartId;
	private String email;
	private List<CartItemDTO> cartItems;
	private double totalPrice;
}
