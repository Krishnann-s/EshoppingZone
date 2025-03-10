package com.eshopingzone.profileservice.service;

import java.util.List;

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
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserProfileRepository userRepo;

	@Override
	public AddressDTO createAddress(AddressDTO addressDto, UserProfile user) {

		// Map AddressDTO to Address entity
        Address address = modelMapper.map(addressDto, Address.class);
        
        // Associate the address with the user profile
        address.setUserProfile(user);
        
        // Add the new address to the user's address list
        user.getAddress().add(address);
        
        // Save the address entity
        Address savedAddress = addressRepo.save(address);
        
        // Map the saved Address entity back to AddressDTO
        return modelMapper.map(savedAddress, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAllAddressess() {
		List<Address> address = addressRepo.findAll();
		
		return address.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepo.findById(addressId).orElseThrow(
				() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));
		
		return modelMapper.map(address, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAddressesByUser(UserProfile user) {
		List<Address> addresses = user.getAddress();
		
		return addresses.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();
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
		UserProfile user = addressFromDB.getUserProfile();
		user.getAddress().removeIf(address -> address.getAddressId().equals(addressId));
		user.getAddress().add(updatedAddress);
		userRepo.save(user);
		
		return modelMapper.map(updatedAddress, AddressDTO.class);
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found."));
		
		UserProfile user = addressFromDB.getUserProfile();
		user.getAddress().removeIf(address -> address.getAddressId().equals(addressId));
		userRepo.save(user);
		
		addressRepo.delete(addressFromDB);
		
		return "Address deleted successfully with AddressId: " + addressId;
	}
}
