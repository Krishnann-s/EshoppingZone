package com.eshopingzone.productservice.service;

import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;

public interface ProductsService {

	ProductDTO addProducts(Long categoryId, ProductDTO productDto);

	ProductResponse viewAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductDTO viewProductsById(Long id);

	ProductDTO updateProducts(ProductDTO productDto, Long id);

	ProductDTO deleteProducts(Long id);

	ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder);

	ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder);

	ProductDTO updateProductImageReference(Long productId, String imageId);

	String getProductImageId(Long productId);

}
