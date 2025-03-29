package com.eshopingzone.cartservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItemDTO {

	private Long orderItemId;
	private Long productId;
	private Integer quantity;
	private double discount;
	private double orderedProductPrice;
}
