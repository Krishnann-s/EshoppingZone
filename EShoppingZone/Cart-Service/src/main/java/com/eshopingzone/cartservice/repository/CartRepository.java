package com.eshopingzone.cartservice.repository;

import java.util.List;

import com.eshopingzone.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	@Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
	Cart findCartByEmail(String email);
}
