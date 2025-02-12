package com.eshopingzone.cartservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.CartItem;

@Repository
public interface CartDtoRepository extends JpaRepository<CartItem, Integer>{

	List<CartItem> findByUserId(int userId);
	CartItem findByUserIdAndProductId(int userId, int productId);
	void deleteByUserId(int userId);
}
