package com.eshopingzone.profileservice.service;

import java.util.List;

import com.eshopingzone.profileservice.Dto.LoginDto;
import com.eshopingzone.profileservice.Dto.ProfileUpdate;
import com.eshopingzone.profileservice.model.UserProfile;

public interface UserProfileService {
	
	UserProfile addNewCustomerProfile(UserProfile userProfile);
	UserProfile loginProfile(LoginDto loginDto);
	List<UserProfile> getAllProfiles();
	UserProfile getByProfileId(int id);
	void updateProfile(ProfileUpdate userProfile);
	void deleteProfile(int id);
	String generateToken(String email, String role);
	void validateToken(String token);
}
