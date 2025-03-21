package com.eshopingzone.profileservice.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_profile", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "mobile_number")
})
@JsonPropertyOrder({ "profileId", "userName", "email", "mobileNumber", "dob", "gender", "role", "password", "address" })
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int profileId;

	@NotBlank
	@Size(min = 5 , max = 25)
	private String userName;

	@Column(name = "email", unique = true, nullable = false)
	@Email
	@Size(min = 10 , max = 50)
	private String email;

	@Column(name = "mobile_number" , unique = true, nullable = false)
	private Long mobileNumber;

	@JsonProperty("dob")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	private String gender;

	private String role;

	@Column(name = "password",nullable = false)
//	@Size(min = 8, max = 50, message = "Password must contain atleast 8 characters")
	private String password;

	@OneToMany(mappedBy = "userProfile" , cascade = {CascadeType.PERSIST, CascadeType.MERGE, }, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Address> address;

}
