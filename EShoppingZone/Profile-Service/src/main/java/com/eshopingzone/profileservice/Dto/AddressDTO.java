package com.eshopingzone.profileservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
