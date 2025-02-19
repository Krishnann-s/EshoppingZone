package com.eshopingzone.categoryservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshopingzone.categoryservice.exception.APIException;
import com.eshopingzone.categoryservice.exception.CategoryNotFoundException;
import com.eshopingzone.categoryservice.model.Category;
import com.eshopingzone.categoryservice.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public List<Category> getAllCategories() {
		
		if(categoryRepo.findAll().isEmpty()) {
			throw new APIException("No Categories Created.");
		}
		return categoryRepo.findAll();
	}

	@Override
	public void createCategory(Category category) {
		Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());
		if(savedCategory != null) {
			throw new APIException("Category with the name: " + category.getCategoryName() + " already exsits");
		}
		categoryRepo.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(
				() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found!"));
		categoryRepo.delete(cat);
		return "Category with id: " + categoryId + " is deleted.";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(
				() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found!"));
		cat.setCategoryName(category.getCategoryName());
		return cat;
	}

}
