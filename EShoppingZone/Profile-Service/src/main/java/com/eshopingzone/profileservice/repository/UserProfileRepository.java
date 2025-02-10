package com.eshopingzone.profileservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshopingzone.profileservice.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{

	Optional<UserProfile> findByEmail(String email);
	UserProfile findByFullName(String fullName);
}
