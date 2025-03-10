package com.eshopingzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
