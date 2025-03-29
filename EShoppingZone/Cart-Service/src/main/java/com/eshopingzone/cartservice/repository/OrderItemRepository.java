package com.eshopingzone.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.OrderItem;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
