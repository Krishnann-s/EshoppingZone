package com.eshopingzone.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.productservice.Dto.ProductsDto;
import com.eshopingzone.productservice.Dto.RatingDto;
import com.eshopingzone.productservice.modal.Products;
import com.eshopingzone.productservice.modal.Rating;
import com.eshopingzone.productservice.proxy.CartClient;
import com.eshopingzone.productservice.proxy.UserProfileClient;
import com.eshopingzone.productservice.service.ProductsService;
import com.eshopingzone.productservice.service.ProductsServiceImpl;

@RestController
@RequestMapping("/eshoppingzone")
public class ProductsController {

	@Autowired
	private ProductsService prodService;

	@Autowired
	private CartClient cartClient;

	@Autowired
	private UserProfileClient userProfileClient;

	// Add new Products
	@PostMapping("/addProducts")
	public ResponseEntity<ProductsDto> addNewProducts(@RequestBody Products products) {
		Products prods = prodService.addProducts(products);
		ProductsDto productDto = convertToDto(prods);
		return new ResponseEntity<ProductsDto>(productDto, HttpStatus.CREATED);
	}

	// View all Products
	@GetMapping("/products")
	public ResponseEntity<List<Products>> getAllProducts() {
		List<Products> products = prodService.viewProducts();
		return new ResponseEntity<List<Products>>(products, HttpStatus.OK);
	}

	// View Products by Id
	@GetMapping("/{id}")
	public ResponseEntity<Products> getProductsById(@PathVariable int id) {
		Products product = prodService.viewProductsById(id);
		return new ResponseEntity<Products>(product, HttpStatus.OK);
	}

	// Update Products by Id
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Products> updateProducts(@RequestBody Products products, @PathVariable int id) {
		Products updatedProducts = prodService.updateProducts(products, id);
		return new ResponseEntity<Products>(updatedProducts, HttpStatus.OK);
	}

	// Delete Products by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
		prodService.deleteProducts(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Add rating to Product based on Product Id
	@PostMapping("/{productId}/rating")
	public ResponseEntity<Rating> addRating(@PathVariable int productId, @RequestBody Rating rating) {
		Rating newRating = ((ProductsServiceImpl) prodService).addRatingToProduct(productId, rating);
		return new ResponseEntity<Rating>(newRating, HttpStatus.CREATED);
	}

	// Add Products to the Cart
	@PostMapping("/cart")
	public ResponseEntity<Void> addProductToCart(@RequestParam String email, @RequestParam int productId,
			@RequestParam int quantity) {
		int userId = userProfileClient.getUserId(email);
		Products product = prodService.viewProductsById(productId);
		cartClient.addProductsToCart(userId, productId, product.getTitle(), product.getImage(), product.getPrice() ,quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// delete products from cart
	@DeleteMapping("/cart")
	public ResponseEntity<Void> deleteProductFromCart(@RequestParam String email, @RequestParam int productId) {
		int userId = userProfileClient.getUserId(email);
		cartClient.deleteProductFromCart(userId, productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private ProductsDto convertToDto(Products product) {
		ProductsDto dto = new ProductsDto();
		dto.setId(product.getProductId());
		dto.setTitle(product.getTitle());
		dto.setPrice(product.getPrice());
		dto.setDescription(product.getDescription());
		dto.setCategory(product.getCategory());
		dto.setImage(product.getImage());

		// Convert Rating (if available)
		if (product.getRating() != null) {
			Rating rating = product.getRating(); // Get the Rating object
			dto.setRating(new RatingDto(rating.getRate(), rating.getCount())); // Map the Rating to RatingDto
		}

		return dto;
	}

}
