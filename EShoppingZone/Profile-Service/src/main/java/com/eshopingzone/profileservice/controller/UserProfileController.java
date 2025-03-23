package com.eshopingzone.profileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.profileservice.Dto.LoginDto;
import com.eshopingzone.profileservice.Dto.ProfileUpdate;
import com.eshopingzone.profileservice.Dto.ResponseDto;
import com.eshopingzone.profileservice.exception.ResourceNotFoundException;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.UserProfileRepository;
import com.eshopingzone.profileservice.service.JwtService;
import com.eshopingzone.profileservice.service.UserProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserProfileController {

	@Autowired
	private UserProfileService userService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserProfileRepository userRepo;

	@Autowired
	private AuthenticationManager authManager;
	

	// Register User
	@PostMapping("/user/register")
	public ResponseEntity<UserProfile> register(@Valid @RequestBody UserProfile userProfile) {
		try {
			if (userProfile.getEmail() == null || userProfile.getEmail().isEmpty()) {
				throw new ResourceNotFoundException("Email cannot be nul or empty");
			}

			UserProfile newUser = userService.addNewCustomerProfile(userProfile);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Login User
	@PostMapping("/user/login")
	public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {
	    try {
	        Authentication authenticate = authManager
	                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

	        if (authenticate.isAuthenticated()) {
	            // Fetch user directly from the repository
	            UserProfile user = userRepo.findByEmail(loginDto.getEmail())
	                    .orElseThrow(() -> new ResourceNotFoundException("Email id: " + loginDto.getEmail() + " is not found."));

	            // Validate password
	            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
	                throw new BadCredentialsException("Invalid Password!");
	            }

	            // Generate token
	            String token = jwtService.generateToken(String.valueOf(user.getProfileId()), user.getEmail(), user.getRole());

	            // Prepare response
	            ResponseDto resDto = new ResponseDto();
	            resDto.setToken(token);
	            resDto.setRole(user.getRole());

	            return ResponseEntity.ok(resDto);
	        } else {
	            throw new BadCredentialsException("Invalid Login Credentials");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto("Login failed: "+ e.getMessage()));
	    }
	}

	// Get All User
	@GetMapping("/users")
	public ResponseEntity<List<UserProfile>> getAllProfiles() {
		List<UserProfile> profiles = userService.getAllProfiles();
		return ResponseEntity.ok(profiles);
	}

	// Get User by ID
	@GetMapping("/user/{id}")
	public ResponseEntity<UserProfile> getProfileById(@PathVariable int id) {
		UserProfile profiles = userService.getByProfileId(id);
		return ResponseEntity.ok(profiles);
	}
	
	// Get Profile Id by Email
	@GetMapping("/users/profile/{email}")
	public ResponseEntity<Long> getProfileIdByEmail(@PathVariable String email) {
	    Long profileId = userService.getProfileIdByEmail(email);
	    return new ResponseEntity<>(profileId, HttpStatus.OK);
	}

	// Update User
	@PutMapping("/update/user/{id}")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdate userProfile, @PathVariable int id) {
		userService.updateProfile(userProfile, id);
		return ResponseEntity.ok("Profile updated successfully");
	}

	// Delete user by ID
	@DeleteMapping("/delete/user/{id}")
	public ResponseEntity<String> deleteProfile(@PathVariable int id) {
		userService.deleteProfile(id);
		return ResponseEntity.ok("Profile deleted successfully");
	}
}
