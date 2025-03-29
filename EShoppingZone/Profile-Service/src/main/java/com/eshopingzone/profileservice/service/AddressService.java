package com.eshopingzone.profileservice.service;

import java.util.List;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.model.UserProfile;


public interface AddressService {

	AddressDTO createAddress(AddressDTO addressDto, UserProfile userId);
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAddressesByUserId(UserProfile userId);
    AddressDTO updateAddressById(Long addressId, AddressDTO addressDto);
    String deleteAddress(Long addressId);
}
