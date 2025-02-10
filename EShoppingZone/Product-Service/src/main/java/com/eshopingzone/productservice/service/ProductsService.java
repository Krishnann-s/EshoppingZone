package com.eshopingzone.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eshopingzone.productservice.Dto.ProductsDto;
import com.eshopingzone.productservice.Dto.RatingDto;
import com.eshopingzone.productservice.modal.Products;
import com.eshopingzone.productservice.modal.Rating;

@Service
public interface ProductsService {

	Products addProducts(Products products);
	List<Products> viewProducts();
	Products viewProductsById(int id);
	Products updateProducts(Products products, int id);
	void deleteProducts(int id);
	Rating addRatingToProduct(int productId, Rating rating);
}
