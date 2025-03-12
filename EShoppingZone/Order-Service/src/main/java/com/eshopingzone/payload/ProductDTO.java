package com.eshopingzone.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long productId;
	private String title;
	private String description;
	private double price;
	private String category;
	private String brand;
	private String image;
}
