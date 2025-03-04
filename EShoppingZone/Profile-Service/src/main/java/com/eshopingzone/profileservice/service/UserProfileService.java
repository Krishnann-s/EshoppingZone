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
	UserProfile updateProfile(ProfileUpdate userProfile, int id);
	void deleteProfile(int id);
	String generateToken(String userId, String email, String role);
	void validateToken(String token);
	int getUserIdByEmail(String email);
}
