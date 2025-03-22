package com.eshopingzone.cartservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

	private Long cartItemId;
	@JsonIgnore
	private CartDTO cartDto;
	@JsonIgnore
	private ProductDTO productDto;
	private Integer quantity;
	private Double discount;
	private Double productPrice;
}
