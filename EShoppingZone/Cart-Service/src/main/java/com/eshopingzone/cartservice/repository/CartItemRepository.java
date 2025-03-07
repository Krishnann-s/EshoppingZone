package com.eshopingzone.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByProductIdAndCart_CartId(Long productId, Long cartId);
}
