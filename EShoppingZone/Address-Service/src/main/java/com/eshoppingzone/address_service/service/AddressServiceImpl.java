package com.eshoppingzone.address_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshoppingzone.address_service.execption.ResourceNotFoundException;
import com.eshoppingzone.address_service.model.Address;
import com.eshoppingzone.address_service.payload.AddressDTO;
import com.eshoppingzone.address_service.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public AddressDTO createAddress(AddressDTO addressDto, Long userId) {
		// Map AddressDTO to Address entity
		Address address = modelMapper.map(addressDto, Address.class);

		// Save the address entity
		Address savedAddress = addressRepo.save(address);

		// Map the saved Address entity back to AddressDTO
		return modelMapper.map(savedAddress, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAllAddresses() {
		List<Address> addresses = addressRepo.findAll();

		return addresses.stream().map(a -> modelMapper.map(a, AddressDTO.class)).collect(Collectors.toList());
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		return modelMapper.map(address, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAddressesByUserId(Long userId) {
		List<Address> addresses = addressRepo.findByProfileId(userId);

		return addresses.stream().map(a -> modelMapper.map(a, AddressDTO.class)).collect(Collectors.toList());
	}

	@Override
	public AddressDTO updateAddressById(Long addressId, AddressDTO addressDto) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		addressFromDB.setCity(addressDto.getCity());
		addressFromDB.setCountry(addressDto.getCountry());
		addressFromDB.setPincode(addressDto.getPincode());
		addressFromDB.setState(addressDto.getState());
		addressFromDB.setStreet(addressDto.getStreet());

		Address updatedAddress = addressRepo.save(addressFromDB);

		return modelMapper.map(updatedAddress, AddressDTO.class);
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		addressRepo.delete(addressFromDB);

		return "Address deleted successfully with AddressId: " + addressId;
	}

}
