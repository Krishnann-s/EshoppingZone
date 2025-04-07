package com.eshopingzone.profileservice.config;

import com.eshopingzone.profileservice.Dto.UserProfileDTO;
import com.eshopingzone.profileservice.model.UserProfile;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Configure ModelMapper
		modelMapper.getConfiguration()
				.setPropertyCondition(Conditions.isNotNull())
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setCollectionsMergeEnabled(false);

		// UserProfile to UserProfileDTO mapping
		modelMapper.createTypeMap(UserProfile.class, UserProfileDTO.class)
				.addMappings(mapper -> {
					mapper.map(UserProfile::getUserId, UserProfileDTO::setUserId);
					mapper.map(UserProfile::getUserName, UserProfileDTO::setUserName);
					mapper.map(UserProfile::getEmail, UserProfileDTO::setEmail);
					mapper.map(UserProfile::getMobileNumber, UserProfileDTO::setMobileNumber);
					mapper.map(UserProfile::getDateOfBirth, UserProfileDTO::setDateOfBirth);
					mapper.map(UserProfile::getGender, UserProfileDTO::setGender);
					mapper.map(UserProfile::getRole, UserProfileDTO::setRole);
					mapper.map(UserProfile::getProfilePictureId, UserProfileDTO::setProfilePictureId);
					// Skip password for security
				});

		// UserProfileDTO to UserProfile mapping
		modelMapper.createTypeMap(UserProfileDTO.class, UserProfile.class)
				.addMappings(mapper -> {
					mapper.map(UserProfileDTO::getUserId, UserProfile::setUserId);
					mapper.map(UserProfileDTO::getUserName, UserProfile::setUserName);
					mapper.map(UserProfileDTO::getEmail, UserProfile::setEmail);
					mapper.map(UserProfileDTO::getMobileNumber, UserProfile::setMobileNumber);
					mapper.map(UserProfileDTO::getDateOfBirth, UserProfile::setDateOfBirth);
					mapper.map(UserProfileDTO::getGender, UserProfile::setGender);
					mapper.map(UserProfileDTO::getRole, UserProfile::setRole);
					mapper.map(UserProfileDTO::getProfilePictureId, UserProfile::setProfilePictureId);
					mapper.map(UserProfileDTO::getPassword, UserProfile::setPassword);
				});

		// We don't need to create explicit mappings for Address and AddressDTO anymore
		// since the field types now match and ModelMapper can handle it automatically

		return modelMapper;
	}
}