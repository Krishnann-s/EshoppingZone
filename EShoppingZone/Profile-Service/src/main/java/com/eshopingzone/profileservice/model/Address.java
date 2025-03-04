package com.eshopingzone.profileservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	private int address_id;
	
	@NotBlank
	@Size(min = 12, message = "street name must be 12 characters")
	private String street;
	
	@NotBlank
	@Size(min = 12, message = "City name must be 12 characters")
	private String city;
	
	@NotBlank
	@Size(min = 12, message = "State name must be 12 characters")
	private String state;
	
	@NotBlank
	@Size(min = 12, message = "Country name must be 12 characters")
	private String country;
	
	@NotBlank
	@Digits(integer = 6, fraction = 0, message = "Pincode must be a 6-Digit number")
	private int pincode;
	
	@ManyToOne
	@JoinColumn(name = "profile_id")
	private UserProfile userProfile;
}
