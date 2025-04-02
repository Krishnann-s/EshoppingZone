package com.eshopingzone.productservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profile-service")
public interface UserProfileClient {

	@GetMapping("/api/user")
	int getUserId(@RequestParam("email") String email);

}
