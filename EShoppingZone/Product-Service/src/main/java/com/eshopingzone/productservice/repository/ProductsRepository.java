package com.eshopingzone.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.productservice.model.Category;
import com.eshopingzone.productservice.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{

	List<Products> findByCategoryOrderByPriceAsc(Category category);

	List<Products> findByProductNameLikeIgnoreCase(String keyword);



}
