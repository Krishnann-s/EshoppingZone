package com.eshopingzone.profileservice.service;

import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshopingzone.profileservice.Dto.LoginDto;
import com.eshopingzone.profileservice.Dto.ProfileUpdate;
import com.eshopingzone.profileservice.exception.ResourceNotFoundException;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public UserProfileServiceImpl(UserProfileRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public UserProfile addNewCustomerProfile(UserProfile userProfile) {
		userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
		return userRepo.save(userProfile);
	}

	@Override
	public UserProfile loginProfile(LoginDto loginDto) {
        System.out.println("Before findByEmail: userRepo = " + userRepo);
        if (userRepo == null) {
            throw new IllegalStateException("userRepo is null");
        }
		UserProfile user = userRepo.findByEmail(loginDto.getEmail()).orElseThrow(
				() -> new ResourceNotFoundException("Email id: " + loginDto.getEmail() + " is not found."));
	     System.out.println("After findByEmail: user = " + user);
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
	public UserProfile getByProfileId(int id) {
		if (id <= 0) {
			throw new ResourceNotFoundException("User Id with Id: " + id + " not found!.");
		}
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id: " + id + " Not Found."));
	}

	@Override
	public UserProfile updateProfile(ProfileUpdate profileUpdate, int id) {
		UserProfile existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (existingUser != null) {
			existingUser.setUserName(profileUpdate.getUserName());
			existingUser.setMobileNumber(profileUpdate.getMobileNumber());
			return userRepo.save(existingUser);
		} else {
			throw new ResourceNotFoundException("User not found to update");
		}

	}

	@Override
	public void deleteProfile(int id) {
		if (!userRepo.existsById(id)) {
			throw new ResourceNotFoundException("Id: " + id + "Not Found.");
		}
		userRepo.deleteById(id);
	}
	
	public String generateToken(String userId, String email, String role) {
		return jwtService.generateToken(userId,email, role);
	}
	
	public void validateToken(String token) {
		if(token == null || token.trim().isEmpty()) {
			throw new ResourceNotFoundException("Token cannot be null or empty");
		}
		jwtService.validateToken(token);
	}

	@Override
	public int getUserIdByEmail(String email) {
		return userRepo.findByEmail(email).get().getProfileId();
	}
}
