package com.eshopingzone.profileservice.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long userId;
    private String userName;
    private String password;
    private String email;
    private Long mobileNumber;

    @JsonProperty("dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String gender;
    private String role;
    private String profilePictureId;
    private List<AddressDTO> address;

}
