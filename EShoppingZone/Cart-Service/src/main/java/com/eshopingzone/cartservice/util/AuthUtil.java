package com.eshopingzone.cartservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    
    // Temporary value for testing when authentication is disabled
    private static final Long DEFAULT_USER_ID = 1L;
    
    public String loggedInEmail() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof Jwt)) {
                System.out.println("No authentication found or principal is not a JWT");
                return "test@example.com"; // Default email for testing
            }
            
            Jwt jwt = (Jwt) auth.getPrincipal();
            return jwt.getClaimAsString("email"); // Extract email from JWT
        } catch (Exception e) {
            System.err.println("Error extracting email from JWT: " + e.getMessage());
            return "test@example.com"; // Default email for testing
        }
    }

    public Long loggedInUserId() {
        System.out.println("loggedInUserId() method is called");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof Jwt)) {
                System.out.println("No authentication found or principal is not a JWT, using default user ID: " + DEFAULT_USER_ID);
                return DEFAULT_USER_ID; // Default user ID for testing
            }
            
            Jwt jwt = (Jwt) auth.getPrincipal();
            System.out.println("JWT claims: " + jwt.getClaims());
            
            // Try multiple possible claim names for userId
            String userId = jwt.getClaimAsString("userId");
            if (userId == null) {
                userId = jwt.getClaimAsString("user_id");
            }
            if (userId == null) {
                userId = jwt.getClaimAsString("sub");
            }
            if (userId == null) {
                userId = jwt.getSubject();
            }
            
            if (userId == null) {
                System.out.println("User ID not found in JWT claims, using default user ID: " + DEFAULT_USER_ID);
                return DEFAULT_USER_ID; // Default user ID for testing
            }
            
            return Long.parseLong(userId);
        } catch (Exception e) {
            System.err.println("Error extracting userId from JWT: " + e.getMessage());
            System.out.println("Using default user ID for testing: " + DEFAULT_USER_ID);
            return DEFAULT_USER_ID; // Default user ID for testing
        }
    }
}