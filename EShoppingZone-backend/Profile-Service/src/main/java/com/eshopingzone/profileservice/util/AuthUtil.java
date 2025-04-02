package com.eshopingzone.profileservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eshopingzone.profileservice.exception.ResourceNotFoundException;
import com.eshopingzone.profileservice.model.UserProfile;
import com.eshopingzone.profileservice.repository.UserProfileRepository;

@Component
public class AuthUtil {

    @Autowired
    private UserProfileRepository userRepo;

    public Long loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserProfile user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getUserId();
    }
}
