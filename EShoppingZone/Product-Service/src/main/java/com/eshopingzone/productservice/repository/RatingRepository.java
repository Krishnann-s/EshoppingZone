package com.eshopingzone.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshopingzone.productservice.model.Rating;

public interface RatingRepository extends  JpaRepository<Rating, Integer>{

}
