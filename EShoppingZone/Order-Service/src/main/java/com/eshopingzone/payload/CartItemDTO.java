package com.eshopingzone.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

	private Long productId;
	private String productName;
	private int quantity;
	private double discount;
	private double productPrice;
}
