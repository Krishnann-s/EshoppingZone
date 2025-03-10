package com.eshopingzone.profileservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	private Long addressId;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pincode;
}
