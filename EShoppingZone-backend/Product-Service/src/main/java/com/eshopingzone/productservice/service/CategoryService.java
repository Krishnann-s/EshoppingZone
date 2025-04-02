package com.eshopingzone.productservice.service;

import com.eshopingzone.productservice.payload.CategoryDTO;
import com.eshopingzone.productservice.payload.CategoryResponse;

public interface CategoryService {

	CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	CategoryDTO createCategory(CategoryDTO categoryDto);
	
	CategoryDTO deleteCategory(Long categoryId);
	
	CategoryDTO updateCategory(CategoryDTO category, Long categoryId);
}
