package com.eshopingzone.cartservice.repository;

import com.eshopingzone.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	@Query("SELECT c FROM Cart c WHERE c.profileId = ?1")
    Cart findCartByProfileId(Long profileId);

    @Query("SELECT c FROM Cart c WHERE c.profileId = ?1 AND c.cartId = ?2")
    Cart findCartByProfileIdAndCartId(Long profileId, Long cartId);
}
