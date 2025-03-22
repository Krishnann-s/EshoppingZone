package com.eshopingzone.cartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service")
public interface UserClient {
	
    @GetMapping("/api/users/profile/{email}")
    Long getProfileIdByEmail(@PathVariable String email);
}
