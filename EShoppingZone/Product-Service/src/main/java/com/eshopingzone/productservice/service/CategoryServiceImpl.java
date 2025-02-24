package com.eshopingzone.productservice.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.eshopingzone.productservice.exception.APIException;
import com.eshopingzone.productservice.exception.CategoryNotFoundException;
import com.eshopingzone.productservice.model.Category;
import com.eshopingzone.productservice.payload.CategoryDTO;
import com.eshopingzone.productservice.payload.CategoryResponse;
import com.eshopingzone.productservice.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
				
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Category> categoryPage = categoryRepo.findAll(pageDetails);
		
		List<Category> categories = categoryPage.getContent();

		if (categories.isEmpty()) {
			throw new APIException("No Categories Created.");
		}

		List<CategoryDTO> categoryDto = categories.stream()
				.map(category -> modelMapper.map(categories, CategoryDTO.class)).toList();

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(categoryDto);
		categoryResponse.setPageNumber(categoryPage.getNumber());
		categoryResponse.setPageSize(categoryPage.getSize());
		categoryResponse.setTotalElements(categoryPage.getTotalElements());
		categoryResponse.setTotalPages(categoryPage.getTotalPages());
		categoryResponse.setLastPage(categoryPage.isLast());

		return categoryResponse;
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {

		Category category = modelMapper.map(categoryDto, Category.class);
		Category categoryName = categoryRepo.findByCategoryName(categoryDto.getCategoryName());

		if (categoryName != null) {
			throw new APIException("Category with the name: " + categoryDto.getCategoryName() + " already exsits");
		}
		Category savedCategory = categoryRepo.save(category);
		CategoryDTO savedCategoryDto = modelMapper.map(savedCategory, CategoryDTO.class);

		return savedCategoryDto;
	}

	@Override
	public CategoryDTO deleteCategory(Long categoryId) {
		Category deletedCategory = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found!"));

		categoryRepo.delete(deletedCategory);

		return modelMapper.map(deletedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId) {

		Category mapCategory = modelMapper.map(categoryDto, Category.class);
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found!"));

		category.setCategoryName(category.getCategoryName());
		category = categoryRepo.save(mapCategory);

		CategoryDTO catDto = modelMapper.map(category, CategoryDTO.class);

		return catDto;
	}

}
