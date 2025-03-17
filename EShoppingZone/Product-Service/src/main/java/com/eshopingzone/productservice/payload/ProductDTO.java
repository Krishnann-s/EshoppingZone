package com.eshopingzone.productservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String productName;
	private String image;
	private Integer quantity;
    private String description;
	private double price;
	private double discount;
	private double specialPrice;
	private CategoryDTO category;
}
