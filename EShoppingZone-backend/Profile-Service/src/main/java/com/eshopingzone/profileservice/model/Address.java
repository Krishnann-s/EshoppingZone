package com.eshopingzone.profileservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserProfile userId;
}
