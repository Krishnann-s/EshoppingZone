package com.eshopingzone.productservice.service;


import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshopingzone.productservice.model.Products;
import com.eshopingzone.productservice.payload.ProductDTO;
import com.eshopingzone.productservice.payload.ProductResponse;

@Service
public interface ProductsService {

	ProductDTO addProducts(Long categoryId, ProductDTO productDto);
	ProductResponse viewAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	Products viewProductsById(Long id);
	ProductDTO updateProducts(ProductDTO productDto, Long id);
	ProductDTO deleteProducts(Long id);
	ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
	
	ProductDTO getProductById(Long productId);
}
