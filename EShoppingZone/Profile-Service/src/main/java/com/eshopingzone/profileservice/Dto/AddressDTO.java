package com.eshopingzone.profileservice.Dto;

import com.eshopingzone.profileservice.model.UserProfile;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    
    private Long addressId;
    
    @NotBlank
    @Size(min = 4, message = "street name must be 4 characters")
    private String street;
    
    @NotBlank
    @Size(min = 4, message = "City name must be 4 characters")
    private String city;
    
    @NotBlank
    @Size(min = 4, message = "State name must be 4 characters")
    private String state;
    
    @NotBlank
    @Size(min = 4, message = "Country name must be 4 characters")
    private String country;
    
    @NotBlank
    @Digits(integer = 6, fraction = 0, message = "Pincode must be a 6-Digit number")
    private String pincode;
    
    private UserProfile userId;
}
