package com.eshopingzone.profileservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.eshopingzone.profileservice.Dto.UserProfileDTO;
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

	/// Create Address
	@Override
	public AddressDTO createAddress(AddressDTO addressDto, Long userId) {
		// Fetch UserProfile
		UserProfile user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		// Map DTO to Address entity
		Address address = modelMapper.map(addressDto, Address.class);
		address.setUserId(user); // Set UserProfile entity

		// Save address
		Address savedAddress = addressRepo.save(address);

		// Map saved Address to DTO
		AddressDTO savedDto = modelMapper.map(savedAddress, AddressDTO.class);
		savedDto.setUserProfileDTO(modelMapper.map(user, UserProfileDTO.class)); // ✅ Map UserProfile to DTO

		return savedDto;
	}

	@Override
	public List<AddressDTO> getAllAddresses() {
		List<Address> addresses = addressRepo.findAll();

		return addresses.stream()
				.map(address -> {
					AddressDTO dto = modelMapper.map(address, AddressDTO.class);
					// Manually map UserProfile to UserProfileDTO
					dto.setUserProfileDTO(modelMapper.map(address.getUserId(), UserProfileDTO.class));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found."));

		AddressDTO dto = modelMapper.map(address, AddressDTO.class);
		dto.setUserProfileDTO(modelMapper.map(address.getUserId(), UserProfileDTO.class)); // ✅ Map UserProfile to DTO

		return dto;
	}

	@Override
	public List<AddressDTO> getAddressesByUserId(Long userId) {
		// Fetch the UserProfile entity
		UserProfile user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		// Get all addresses for this user
		List<Address> addresses = addressRepo.findByUserId(user);

		return addresses.stream()
				.map(address -> {
					// Map Address entity to DTO
					AddressDTO dto = modelMapper.map(address, AddressDTO.class);
					// Map nested UserProfile to UserProfileDTO
					dto.setUserProfileDTO(modelMapper.map(address.getUserId(), UserProfileDTO.class));
					return dto;
				})
				.collect(Collectors.toList());
	}

	/// Update Address by address id
	@Override
	public AddressDTO updateAddressById(Long addressId, AddressDTO addressDto) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		// Preserve original UserProfile
		UserProfile originalUser = addressFromDB.getUserId();

		// Update fields
		modelMapper.map(addressDto, addressFromDB);
		addressFromDB.setUserId(originalUser); // Ensure UserProfile is not overwritten

		Address updatedAddress = addressRepo.save(addressFromDB);

		AddressDTO updatedDto = modelMapper.map(updatedAddress, AddressDTO.class);
		updatedDto.setUserProfileDTO(modelMapper.map(addressFromDB.getUserId(), UserProfileDTO.class));

		return updatedDto;
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));

		addressRepo.delete(addressFromDB);

		return "Address deleted successfully with AddressId: " + addressId;
	}
}