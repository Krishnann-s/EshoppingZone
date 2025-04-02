package com.eshopingzone.cartservice.client;

import com.eshopingzone.cartservice.payload.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "profile-service")
public interface UserClient {
	
    @GetMapping("/api/users/profile/{email}")
    Long getProfileIdByEmail(@PathVariable String email);

    @GetMapping("/api/address/{addressId}")
    AddressDTO getAddressById(@PathVariable Long addressId, @RequestHeader("Authorization") String authHeader);
}
