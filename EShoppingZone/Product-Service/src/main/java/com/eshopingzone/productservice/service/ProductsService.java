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
	ProductResponse viewAllProducts();
	Products viewProductsById(Long id);
	ProductDTO updateProducts(ProductDTO productDto, Long id);
	ProductDTO deleteProducts(Long id);
	ProductResponse searchByCategory(Long categoryId);
	ProductResponse searchProductsByKeyword(String keyword);
	ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
