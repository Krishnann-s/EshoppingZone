package com.eshopingzone.profileservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.Dto.UserProfileDTO;
import com.eshopingzone.profileservice.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshopingzone.profileservice.Dto.LoginDto;
import com.eshopingzone.profileservice.Dto.ProfileUpdate;
import com.eshopingzone.profileservice.exception.ResourceNotFoundException;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.UserProfileRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Autowired
	private ModelMapper modelMapper;

	public UserProfileServiceImpl(UserProfileRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public UserProfileDTO addNewCustomerProfile(UserProfileDTO userProfileDto) {
		// Create a new UserProfile entity and map from DTO
		UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);

		// Encode the password
		userProfile.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));

		// Initialize empty address list if null
		if (userProfile.getAddress() == null) {
			userProfile.setAddress(new ArrayList<>());
		}

		// Save user to get the ID
		UserProfile savedUser = userRepo.save(userProfile);

		// If addresses exist in the DTO, process them
		if (userProfileDto.getAddress() != null && !userProfileDto.getAddress().isEmpty()) {
			// Create a list to hold the address entities
			List<Address> addresses = new ArrayList<>();

			// For each address DTO, create an address entity
			for (AddressDTO addressDto : userProfileDto.getAddress()) {
				// Map the DTO to an entity
				Address address = modelMapper.map(addressDto, Address.class);
				// Set the user reference
				address.setUserId(savedUser);
				// Add to the list
				addresses.add(address);
			}

			// Set the addresses on the user and save again
			savedUser.setAddress(addresses);
			savedUser = userRepo.save(savedUser);
		}

		// Map the saved entity back to a DTO for the response
		UserProfileDTO responseDto = modelMapper.map(savedUser, UserProfileDTO.class);

		return responseDto;
	}
	@Override
	public UserProfile loginProfile(LoginDto loginDto) {
		UserProfile user = userRepo.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Email id: " + loginDto.getEmail() + " is not found."));

		if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			return user;
		} else {
			throw new BadCredentialsException("Invalid Password!");
		}
	}

	@Override
	public List<UserProfile> getAllProfiles() {
		List<UserProfile> allUsers = userRepo.findAll();
		if (allUsers.isEmpty()) {
			throw new ResourceNotFoundException("No Users Found");
		}
		return allUsers;
	}

	@Override
	@Transactional
	public UserProfile getByProfileId(Long id) {
		if (id <= 0) {
			throw new ResourceNotFoundException("User Id with Id: " + id + " not found!.");
		}
		return userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id: " + id + " Not Found."));
	}

	@Override
	public UserProfile updateProfile(ProfileUpdate profileUpdate, Long id) {
		UserProfile existingUser = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		existingUser.setUserName(profileUpdate.getUserName());
		existingUser.setMobileNumber(profileUpdate.getMobileNumber());
		return userRepo.save(existingUser);
	}

	@Override
	public void deleteProfile(Long id) {
		if (!userRepo.existsById(id)) {
			throw new ResourceNotFoundException("Id: " + id + " Not Found.");
		}
		userRepo.deleteById(id);
	}

	public String generateToken(String userId, String email, String role) {
		return jwtService.generateToken(userId, email, role);
	}

	public void validateToken(String token) {
		if(token == null || token.trim().isEmpty()) {
			throw new ResourceNotFoundException("Token cannot be null or empty");
		}
		jwtService.validateToken(token);
	}

	@Override
	public Long getProfileIdByEmail(String email) {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Email: " + email + " Not Found."))
				.getUserId();
	}

	public UserProfile updateProfilePicture(Long userId, String imageId) {
		UserProfile userProfile = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Profile not found"));

		userProfile.setProfilePictureId(imageId);
		return userRepo.save(userProfile);
	}

	public String getProfilePictureId(Long userId) {
		UserProfile userProfile = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Profile not found"));

		return userProfile.getProfilePictureId();
	}
}