package com.eshopingzone.profileservice.service;

import java.util.List;

import com.eshopingzone.profileservice.Dto.LoginDto;
import com.eshopingzone.profileservice.Dto.ProfileUpdate;
import com.eshopingzone.profileservice.Dto.UserProfileDTO;
import com.eshopingzone.profileservice.model.UserProfile;

public interface UserProfileService {

	UserProfileDTO addNewCustomerProfile(UserProfileDTO userProfileDTO);
	UserProfile loginProfile(LoginDto loginDto);
	List<UserProfile> getAllProfiles();
	UserProfile getByProfileId(Long id);
	UserProfile updateProfile(ProfileUpdate profileUpdate, Long id);
	void deleteProfile(Long id);
	Long getProfileIdByEmail(String email);
	UserProfile updateProfilePicture(Long userId, String imageId);
	String getProfilePictureId(Long userId);
}
