package com.eshoppingzone.address_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingzone.address_service.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findByProfileId(Long userId);
}
