package com.eshopingzone.cartservice.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
	
    public String loggedInEmail() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaim("email"); // Extract email from JWT
    }

    public Long loggedInUserId() {
    	System.out.println("loggedInUserId() method is called");
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("JWT claims: " + jwt.getClaims());
        return Long.parseLong(jwt.getClaim("userId")); // Extract userId from JWT
    }
}
