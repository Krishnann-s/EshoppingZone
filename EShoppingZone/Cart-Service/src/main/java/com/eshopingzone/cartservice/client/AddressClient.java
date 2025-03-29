package com.eshopingzone.cartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.eshopingzone.cartservice.payload.AddressDTO;


@FeignClient(name = "address-service")
public interface AddressClient {

	@GetMapping("/api/address/{addressId}")
	AddressDTO getAddressById(@PathVariable Long addressId, @RequestHeader("Authorization") String authHeader);
}
