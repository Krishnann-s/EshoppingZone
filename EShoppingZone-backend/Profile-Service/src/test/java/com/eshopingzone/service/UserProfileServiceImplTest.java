package com.eshopingzone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.Dto.UserProfileDTO;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.AddressRepository;
import com.eshopingzone.profileservice.repository.UserProfileRepository;
import com.eshopingzone.profileservice.service.JwtService;
import com.eshopingzone.profileservice.service.UserProfileServiceImpl;

public class UserProfileServiceImplTest {
	@Mock
    private UserProfileRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AddressRepository addressRepo;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewCustomerProfile_Positive() {
        UserProfileDTO userProfileDto = new UserProfileDTO();
        userProfileDto.setUserName("testUser");
        userProfileDto.setEmail("test@example.com");
        userProfileDto.setMobileNumber(1234567890L);
        userProfileDto.setDateOfBirth(LocalDate.of(2000,1,1));
        userProfileDto.setGender("Male");
        userProfileDto.setRole("USER");
        userProfileDto.setPassword("password");

        AddressDTO addressDto = new AddressDTO();
        addressDto.setStreet("123 Street");
        addressDto.setCity("City");
        addressDto.setState("State");
        addressDto.setCountry("Country");
        addressDto.setPincode("123456");
        List<AddressDTO> addressList = new ArrayList<>();
        addressList.add(addressDto);
        userProfileDto.setAddress(addressList);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(1L);
        userProfile.setUserName("testUser");
        userProfile.setEmail("test@example.com");
        userProfile.setMobileNumber(1234567890L);
        userProfile.setDateOfBirth(LocalDate.of(2000,1,1));
        userProfile.setGender("Male");
        userProfile.setRole("USER");
        userProfile.setPassword("encodedPassword");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepo.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfileDTO result = userProfileService.addNewCustomerProfile(userProfileDto);

        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(1234567890L, result.getMobileNumber());
        assertEquals(LocalDate.of(2000,1,1), result.getDateOfBirth());
        assertEquals("Male", result.getGender());
        assertEquals("USER", result.getRole());
        assertEquals(1L, result.getUserId());
    }
    
    @Test
    public void testAddNewCustomerProfile_Negative() {
        UserProfileDTO userProfileDto = new UserProfileDTO();
        userProfileDto.setUserName("testUser");
        userProfileDto.setEmail("test@example.com");
        userProfileDto.setMobileNumber(1234567890L);
        userProfileDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userProfileDto.setGender("Male");
        userProfileDto.setRole("USER");
        userProfileDto.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepo.save(any(UserProfile.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.addNewCustomerProfile(userProfileDto);
        });

        assertEquals("Database error", exception.getMessage());
    }
}