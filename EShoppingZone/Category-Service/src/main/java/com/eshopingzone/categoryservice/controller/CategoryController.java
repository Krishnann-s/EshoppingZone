package com.eshopingzone.categoryservice.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.categoryservice.model.Category;
import com.eshopingzone.categoryservice.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eshoppingzone")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> allCategory = categoryService.getAllCategories();
		return new ResponseEntity<List<Category>>(allCategory, HttpStatus.OK);
	}

	@PostMapping("/public/categories")
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<String>("Category with category id: " + category.getCategoryId() + " is created successfully.",
				HttpStatus.OK);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		String status = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@PutMapping("/public/categories/{categoryId}")
	public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category,
			@PathVariable Long CategoryId) {
		Category updatedCategory = categoryService.updateCategory(category, CategoryId);
		return new ResponseEntity<Category>(updatedCategory, HttpStatus.OK);
	}
}
