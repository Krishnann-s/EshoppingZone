package com.eshopingzone.profileservice.config;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.Dto.UserProfileDTO;
import com.eshopingzone.profileservice.model.Address;
import com.eshopingzone.profileservice.model.UserProfile;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

		// Address to AddressDTO mapping with proper handling of userId
		modelMapper.createTypeMap(Address.class, AddressDTO.class)
				.addMappings(mapper -> {
					mapper.map(Address::getAddressId, AddressDTO::setAddressId);
					mapper.map(Address::getStreet, AddressDTO::setStreet);
					mapper.map(Address::getCity, AddressDTO::setCity);
					mapper.map(Address::getState, AddressDTO::setState);
					mapper.map(Address::getCountry, AddressDTO::setCountry);
					mapper.map(Address::getPincode, AddressDTO::setPincode);
					// Custom mapping for userId
					mapper.<Long>map(src -> {
						if (src.getUserId() != null) {
							return src.getUserId().getUserId();
						}
						return null;
					}, AddressDTO::setUserId);
				});

		// AddressDTO to Address mapping
		modelMapper.createTypeMap(AddressDTO.class, Address.class)
				.addMappings(mapper -> {
					mapper.map(AddressDTO::getAddressId, Address::setAddressId);
					mapper.map(AddressDTO::getStreet, Address::setStreet);
					mapper.map(AddressDTO::getCity, Address::setCity);
					mapper.map(AddressDTO::getState, Address::setState);
					mapper.map(AddressDTO::getCountry, Address::setCountry);
					mapper.map(AddressDTO::getPincode, Address::setPincode);
					// Skip userId as it needs special handling
				});

		return modelMapper;
	}
}