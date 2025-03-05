package com.eshopingzone.cartservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

	private Long cartItemId;
	private CartDTO cartDto;
	private ProductDTO productDto;
	private Integer quantity;
	private Double discount;
	private Double productPrice;
}
