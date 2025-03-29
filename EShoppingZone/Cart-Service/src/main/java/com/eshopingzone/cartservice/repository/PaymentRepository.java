package com.eshopingzone.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.cartservice.model.Payment;



@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
