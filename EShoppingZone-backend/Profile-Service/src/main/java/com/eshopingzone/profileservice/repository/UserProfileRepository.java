package com.eshopingzone.profileservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshopingzone.profileservice.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

	Optional<UserProfile> findByEmail(String email);
	Optional<UserProfile> findByUserName(String userName);
}
