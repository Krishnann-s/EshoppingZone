package com.eshopingzone.profileservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.eshopingzone.profileservice.Dto.AddressDTO;

@FeignClient(name = "address-service")
public interface AddressClient {

	@GetMapping("/api/user/address")
	List<AddressDTO> getAddressesByUser();
}
