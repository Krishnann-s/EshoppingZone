package com.eshoppingzone.address_service.service;

import java.util.List;

import com.eshoppingzone.address_service.payload.AddressDTO;

public interface AddressService {

	AddressDTO createAddress(AddressDTO addressDto, Long userId);
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAddressesByUserId(Long userId);
    AddressDTO updateAddressById(Long addressId, AddressDTO addressDto);
    String deleteAddress(Long addressId);
}
