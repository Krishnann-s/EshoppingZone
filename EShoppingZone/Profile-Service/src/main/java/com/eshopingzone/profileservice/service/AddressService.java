package com.eshopingzone.profileservice.service;

import java.util.List;

import com.eshopingzone.profileservice.Dto.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDto, Long userId);
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAddressesByUserId(Long userId);
    AddressDTO updateAddressById(Long addressId, AddressDTO addressDto);
    String deleteAddress(Long addressId);
}