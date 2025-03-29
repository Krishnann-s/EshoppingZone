package com.eshopingzone.profileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.profileservice.Dto.AddressDTO;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.service.AddressService;
import com.eshopingzone.profileservice.util.AuthUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private AuthUtil authUtil;

	@PostMapping("/address")
	public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDto) {
		Long userId = authUtil.loggedInUser();
		addressDto.setUserId(userId);
		AddressDTO savedAddressDto = addressService.createAddress(addressDto, userId);

		return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
	}

	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAllAddress() {
		List<AddressDTO> addressDtoList = addressService.getAllAddresses();

		return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
	}

	@GetMapping("/address/{addressId}")
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
		AddressDTO addressDto = addressService.getAddressById(addressId);

		return new ResponseEntity<>(addressDto, HttpStatus.OK);
	}

	@GetMapping("/user/address")
	public ResponseEntity<List<AddressDTO>> getAddressesByUser() {
		Long userId = authUtil.loggedInUser();
		List<AddressDTO> addressDtoList = addressService.getAddressesByUserId(userId);

		return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
	}

	@PutMapping("/address/{addressId}")
	public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId,
			@RequestBody AddressDTO addressDto) {
		AddressDTO updatedAddressDto = addressService.updateAddressById(addressId, addressDto);

		return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
	}

	@DeleteMapping("/address/{addressId}")
	public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId) {
		String status = addressService.deleteAddress(addressId);

		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}