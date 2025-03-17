package com.eshopingzone.productservice.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.eshopingzone.productservice.config.AppConstants;
import com.eshopingzone.productservice.model.Products;
import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;
import com.eshopingzone.productservice.proxy.CartClient;
import com.eshopingzone.productservice.proxy.UserProfileClient;
import com.eshopingzone.productservice.service.ProductsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductsController {

	@Autowired
	private ProductsService prodService;

	@Autowired
	private CartClient cartClient;

	@Autowired
	private UserProfileClient userProfileClient;

	// Add new Products
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addNewProducts(@Valid @RequestBody ProductDTO productDto,
			@PathVariable Long categoryId) {

		ProductDTO savedProductDto = prodService.addProducts(categoryId, productDto);
		return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
	}

	// View all Products
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		
		ProductResponse products = prodService.viewAllProducts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// get all products by Category
	@GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		ProductResponse productResponse = prodService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	// Get All Products using Keyword
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		ProductResponse productResponse = prodService.searchProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	// Get Products by Id
	@GetMapping("/public/products/{productId}")
	public ResponseEntity<ProductDTO> getProductsById(@PathVariable Long productId) {
		ProductDTO productdto = prodService.viewProductsById(productId);
		return new ResponseEntity<>(productdto, HttpStatus.OK);
	}

	// Update Products by Id
	@PutMapping("/admin/product/{productId}")
	public ResponseEntity<ProductDTO> updateProducts(@RequestBody ProductDTO productDto, @PathVariable Long productId) {
		ProductDTO updatedProductsDto = prodService.updateProducts(productDto, productId);
		return new ResponseEntity<>(updatedProductsDto, HttpStatus.OK);
	}

	// Delete Products by Id
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
		prodService.deleteProducts(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Add Products to the Cart
	@PostMapping("/cart")
	public ResponseEntity<Void> addProductToCart(@RequestParam String email, @RequestParam Long productId,
			@RequestParam int quantity) {
		int userId = userProfileClient.getUserId(email);
		ProductDTO product = prodService.viewProductsById(productId);
		cartClient.addProductsToCart(userId, productId, product.getProductName(), product.getImage(),
				product.getPrice(), quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// delete products from cart
	@DeleteMapping("/cart")
	public ResponseEntity<Void> deleteProductFromCart(@RequestParam String email, @RequestParam Long productId) {
		int userId = userProfileClient.getUserId(email);
		cartClient.deleteProductFromCart(userId, productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	@PutMapping("/products/{productId}/image")
//	public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
//			@RequestParam("image") MultipartFile image) throws IOException {
//
//		ProductDTO updatedProduct = prodService.updateProductImage(productId, image);
//
//		return new ResponseEntity<ProductDTO>(updatedProduct, HttpStatus.OK);
//	}
}
