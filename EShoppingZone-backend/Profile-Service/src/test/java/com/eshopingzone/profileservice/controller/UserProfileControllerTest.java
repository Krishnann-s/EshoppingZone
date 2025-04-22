//package com.eshopingzone.profileservice.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import com.eshopingzone.profileservice.Dto.UserProfileDTO;
//import com.eshopingzone.profileservice.client.ImageClient;
//import com.eshopingzone.profileservice.config.UserProfileDetailsService;
//import com.eshopingzone.profileservice.repository.UserProfileRepository;
//import com.eshopingzone.profileservice.service.JwtService;
//import com.eshopingzone.profileservice.service.UserProfileService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserProfileControllerTest {
//    
//    @Autowired
//    private MockMvc mockMvc;
//    
//    @MockBean
//    private UserProfileService userService;
//    
//    @MockBean
//    private JwtService jwtService;
//    
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//    
//    @MockBean
//    private UserProfileRepository userRepo;
//    
//    @MockBean
//    private AuthenticationManager authManager;
//    
//    @MockBean
//    private UserProfileDetailsService userDetailsService;
//    
//    @MockBean
//    private ImageClient imgClient;
//    
//    @Autowired
//    private ObjectMapper objectMapper;
//    
//    @Test
//    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {
//        // Arrange
//        UserProfileDTO userDetails = new UserProfileDTO();
//        userDetails.setUserName("Sergio");
//        userDetails.setEmail("sergio@example.com");
//        userDetails.setPassword("sergio123");
//        userDetails.setMobileNumber(9089786756L);
//        userDetails.setRole("user");
//        userDetails.setGender("Male");
//        userDetails.setProfilePictureId("1");
//        userDetails.setDateOfBirth(LocalDate.of(2000, 3, 21));
//        
//        when(userService.addNewCustomerProfile(any(UserProfileDTO.class))).thenReturn(userDetails);
//        
//        // Act & Assert
//        MvcResult result = mockMvc.perform(post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDetails)))
//                .andExpect(status().isCreated())
//                .andReturn();
//        
//        // Additional assertions
//        UserProfileDTO createdUser = objectMapper.readValue(
//                result.getResponse().getContentAsString(), UserProfileDTO.class);
//        
//        Assertions.assertEquals(userDetails.getUserName(), createdUser.getUserName());
//        Assertions.assertEquals(userDetails.getEmail(), createdUser.getEmail());
//		Assertions.assertEquals(userDetails.getPassword(), createdUser.getPassword());
//		Assertions.assertEquals(userDetails.getMobileNumber(), createdUser.getMobileNumber());
//		Assertions.assertEquals(userDetails.getRole(), createdUser.getRole());
//		Assertions.assertEquals(userDetails.getGender(), createdUser.getGender());
//		Assertions.assertEquals(userDetails.getProfilePictureId(), createdUser.getProfilePictureId());
//		Assertions.assertEquals(userDetails.getDateOfBirth(), createdUser.getDateOfBirth());
//		Assertions.assertNotNull(createdUser.getUserId(),"User Id cannot be empty");
//	}
//}
