package com.eshopingzone.profileservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.exception.ResourceNotFoundException;
import com.eshopingzone.profileservice.model.Address;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.AddressRepository;
import com.eshopingzone.profileservice.repository.UserProfileRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private UserProfileRepository userRepo;

	@Override
	public AddressDTO createAddress(AddressDTO addressDto, Long userId) {
		// Get the UserProfile entity
		UserProfile user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		// Map AddressDTO to Address entity
		Address address = modelMapper.map(addressDto, Address.class);
		address.setUserId(user);

		// Save the address entity
		Address savedAddress = addressRepo.save(address);

		// Map the saved Address entity back to AddressDTO
		AddressDTO savedDto = modelMapper.map(savedAddress, AddressDTO.class);
		savedDto.setUserId(userId); // Set the user ID in the DTO

		return savedDto;
	}

	@Override
	public List<AddressDTO> getAllAddresses() {
		List<Address> addresses = addressRepo.findAll();

		return addresses.stream()
				.map(address -> {
					AddressDTO dto = modelMapper.map(address, AddressDTO.class);
					if (address.getUserId() != null) {
						dto.setUserId(address.getUserId().getUserId());
					}
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		AddressDTO dto = modelMapper.map(address, AddressDTO.class);
		if (address.getUserId() != null) {
			dto.setUserId(address.getUserId().getUserId());
		}

		return dto;
	}

	@Override
	public List<AddressDTO> getAddressesByUserId(Long userId) {
		// Get the UserProfile entity
		UserProfile user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		List<Address> addresses = addressRepo.findByUserId(user);

		return addresses.stream()
				.map(address -> {
					AddressDTO dto = modelMapper.map(address, AddressDTO.class);
					dto.setUserId(userId);
					return dto;
				})
				.collect(Collectors.toList());
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

		AddressDTO dto = modelMapper.map(updatedAddress, AddressDTO.class);
		if (updatedAddress.getUserId() != null) {
			dto.setUserId(updatedAddress.getUserId().getUserId());
		}

		return dto;
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		addressRepo.delete(addressFromDB);

		return "Address deleted successfully with AddressId: " + addressId;
	}
}