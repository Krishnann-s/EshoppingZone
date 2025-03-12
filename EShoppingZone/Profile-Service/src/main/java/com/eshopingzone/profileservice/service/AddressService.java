package com.eshopingzone.profileservice.service;

import java.util.List;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.model.UserProfile;

public interface AddressService {

	AddressDTO createAddress(AddressDTO addressDto, UserProfile user);

	List<AddressDTO> getAllAddressess();

	AddressDTO getAddressById(Long addressId);

	List<AddressDTO> getAddressesByUser(UserProfile user);

	AddressDTO updateAddressById(Long addressId, AddressDTO addressDto);

	String deleteAddress(Long addressId);

}
