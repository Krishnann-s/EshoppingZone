package com.eshoppingzone.address_service.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;


@Component
public class AuthUtil {

    public String loggedInEmail() {
        Jwt jwt = getJwtToken();
        return jwt.getClaimAsString("email");
    }

    public Long loggedInUserId() {
        Jwt jwt = getJwtToken();
        
        // Get userId from the claim
        Object userIdObj = jwt.getClaim("userId");
        String userId = null;
        
        if (userIdObj != null) {
            userId = userIdObj.toString();
        } else if (jwt.getClaimAsString("user_id") != null) {
            userId = jwt.getClaimAsString("user_id");
        } else if (jwt.getClaimAsString("id") != null) {
            userId = jwt.getClaimAsString("id");
        } else if (jwt.getClaimAsString("sub") != null) {
            userId = jwt.getClaimAsString("sub");
        } else {
            userId = jwt.getSubject();
        }
        
        return Long.parseLong(userId);
    }
    
    private Jwt getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        
        if (authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        
        throw new IllegalStateException("No JWT token found in the security context");
    }
}