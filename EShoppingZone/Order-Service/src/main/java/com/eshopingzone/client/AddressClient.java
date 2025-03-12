package com.eshopingzone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eshopingzone.payload.AddressDTO;

@FeignClient(name = "profile-service")
public interface AddressClient {

	@GetMapping("/api/address/{addressId}")
	AddressDTO getAddressById(@PathVariable Long addressId);
}
