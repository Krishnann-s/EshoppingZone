package com.eshopingzone.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.productservice.model.Category;
import com.eshopingzone.productservice.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{

	Page<Products> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

	Page<Products> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

}
