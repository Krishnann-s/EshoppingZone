package com.eshopingzone.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.eshopingzone.productservice.payload.ImageResponse;
import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;
import com.eshopingzone.productservice.proxy.CartClient;
import com.eshopingzone.productservice.proxy.ImageClient;
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
	
	@Autowired
	private ImageClient imageClient;


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
		cartClient.addProductsToCart(userId, productId, product.getProductName(), product.getImageId(),
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

	// Upload Product image
	@PostMapping(value = "/admin/product/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProductDTO> uploadProductImage(
	        @PathVariable Long productId,
	        @RequestParam("image") MultipartFile image) {
	    
	    // First, upload image to image-service
	    ResponseEntity<ImageResponse> imageResponse = imageClient.uploadImage(image, "product");
	    
	    // Get the image ID from the response
	    String imageId = imageResponse.getBody().getId();
	    
	    // If you decide to store image dimensions, you could update your Products entity
	    // to include width and height fields, and set them here
	    
	    // Update product with image reference
	    ProductDTO updatedProduct = prodService.updateProductImageReference(productId, imageId);
	    
	    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
    
    // Method to get product image
    @GetMapping(value = "/public/product/{productId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        // Get image ID from product
        String imageId = prodService.getProductImageId(productId);
        
        // Get image data from image-service
        return imageClient.getImage(imageId);
    }
    
    // Method to delete product image
    @DeleteMapping("/admin/product/{productId}/image")
    public ResponseEntity<ProductDTO> deleteProductImage(@PathVariable Long productId) {
        // Get image ID from product
        String imageId = prodService.getProductImageId(productId);
        
        // Delete image from image-service
        imageClient.deleteImage(imageId);
        
        // Update product to remove image reference
        ProductDTO updatedProduct = prodService.updateProductImageReference(productId, null);
        
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
