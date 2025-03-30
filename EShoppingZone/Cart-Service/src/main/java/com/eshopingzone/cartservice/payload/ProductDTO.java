package com.eshopingzone.cartservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String productName;
	private double price;
	private double discount;
	private double specialPrice;
	 private String description;
	 private Integer quantity;
	private String imageUrl;
	private String imageId;
}
