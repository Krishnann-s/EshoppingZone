package com.eshopingzone.cartservice.repository;

import java.util.List;

import com.eshopingzone.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	List<CartItem> findByProfileId(Long profileId);
	CartItem findByUserIdAndProductId(Long profileId, int productId);
	void deleteByUserId(int userId);
	Cart findByEmail(String email);
}
