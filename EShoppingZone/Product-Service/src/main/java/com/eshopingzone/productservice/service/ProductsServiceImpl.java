package com.eshopingzone.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshopingzone.productservice.Dto.ProductsDto;
import com.eshopingzone.productservice.Dto.RatingDto;
import com.eshopingzone.productservice.exception.ProductNotFoundException;
import com.eshopingzone.productservice.exception.ProductsAlreadyExistsException;
import com.eshopingzone.productservice.modal.Products;
import com.eshopingzone.productservice.modal.Rating;
import com.eshopingzone.productservice.repository.ProductsRepository;
import com.eshopingzone.productservice.repository.RatingRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepository prodRepo;

	@Autowired
	private RatingRepository ratingRepo;

	@Override
	@Transactional
	public Products addProducts(Products products) {
		return prodRepo.save(products);
	}

	@Override
	public List<Products> viewProducts() {
		List<Products> products = prodRepo.findAll();
		if (products.isEmpty()) {
			throw new ProductNotFoundException("No products found in the database.");
		}
		return products;
	}

	@Override
	public Products viewProductsById(int id) {
		Products prod = prodRepo.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product with product id: " + id + " not found."));
		return prod;
	}

	@Override
	@Transactional
	public Products updateProducts(Products products, int id) {
		Products existingProduct = prodRepo.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product with product_id: " + id + " not found."));

		existingProduct.setTitle(products.getTitle());
		existingProduct.setCategory(products.getCategory());
		existingProduct.setDescription(products.getDescription());
		existingProduct.setPrice(products.getPrice());
		existingProduct.setImage(products.getImage());

		if (products.getRating() != null) {
			existingProduct.setRating(products.getRating()); // Directly set the new rating
		}

		return prodRepo.save(existingProduct);
	}

	@Override
	@Transactional
	public void deleteProducts(int id) {
		Products product = prodRepo.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product with product id: " + id + " not found."));

		prodRepo.deleteById(id);
	}

	@Transactional
	public Rating addRatingToProduct(int productId, Rating rating) {
		Products product = prodRepo.findById(productId).orElseThrow(
				() -> new ProductNotFoundException("Product with product id: " + productId + " not found."));

		rating.setProducts(product);
		product.setRating(rating);

		prodRepo.save(product);
		return rating;
	}

}
