package com.eshopingzone.profileservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.profileservice.model.Address;
import com.eshopingzone.profileservice.model.UserProfile;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findByUserId(UserProfile userId);
}
