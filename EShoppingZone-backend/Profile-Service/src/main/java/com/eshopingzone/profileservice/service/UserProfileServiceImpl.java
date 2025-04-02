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
import com.eshopingzone.profileservice.repository.AddressRepository;
import com.eshopingzone.profileservice.repository.UserProfileRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AddressRepository addressRepo;

	public UserProfileServiceImpl(UserProfileRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public UserProfileDTO addNewCustomerProfile(UserProfileDTO userProfileDto) {
		// Create a new UserProfile entity but DON'T map addresses yet
		UserProfile userProfile = new UserProfile();
		userProfile.setUserName(userProfileDto.getUserName());
		userProfile.setEmail(userProfileDto.getEmail());
		userProfile.setMobileNumber(userProfileDto.getMobileNumber());
		userProfile.setDateOfBirth(userProfileDto.getDateOfBirth());
		userProfile.setGender(userProfileDto.getGender());
		userProfile.setRole(userProfileDto.getRole());
		userProfile.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));

		// Save user to get the ID
		UserProfile savedUser = userRepo.save(userProfile);

		// If addresses exist in the DTO, process them
		if (userProfileDto.getAddress() != null && !userProfileDto.getAddress().isEmpty()) {
			List<Address> addresses = new ArrayList<>();
			for (AddressDTO addressDto : userProfileDto.getAddress()) {
				// Create address entity manually
				Address address = new Address();
				address.setStreet(addressDto.getStreet());
				address.setCity(addressDto.getCity());
				address.setState(addressDto.getState());
				address.setCountry(addressDto.getCountry());
				address.setPincode(addressDto.getPincode());
				address.setUserId(savedUser);
				addresses.add(address);
			}
			addressRepo.saveAll(addresses);
		}

		// Explicitly fetch addresses for the user
		List<Address> addresses = addressRepo.findByUserId(savedUser);
		savedUser.setAddress(addresses);

		// Create response DTO manually without using ModelMapper
		UserProfileDTO responseDto = new UserProfileDTO();
		responseDto.setUserId(savedUser.getUserId());
		responseDto.setUserName(savedUser.getUserName());
		responseDto.setEmail(savedUser.getEmail());
		responseDto.setMobileNumber(savedUser.getMobileNumber());
		responseDto.setDateOfBirth(savedUser.getDateOfBirth());
		responseDto.setGender(savedUser.getGender());
		responseDto.setRole(savedUser.getRole());
		responseDto.setProfilePictureId(savedUser.getProfilePictureId());

		// Map addresses manually
		if (addresses != null && !addresses.isEmpty()) {
			List<AddressDTO> addressDTOs = new ArrayList<>();
			for (Address address : addresses) {
				AddressDTO dto = new AddressDTO();
				dto.setAddressId(address.getAddressId());
				dto.setStreet(address.getStreet());
				dto.setCity(address.getCity());
				dto.setState(address.getState());
				dto.setCountry(address.getCountry());
				dto.setPincode(address.getPincode());
				dto.setUserId(savedUser.getUserId());
				addressDTOs.add(dto);
			}
			responseDto.setAddress(addressDTOs);
		} else {
			responseDto.setAddress(new ArrayList<>());
		}

		return responseDto;
	}
	
	// Login user
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