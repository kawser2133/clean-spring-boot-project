package com.kawser.cleanspringbootproject.auth.services.impl;

import com.kawser.cleanspringbootproject.auth.models.User;
import com.kawser.cleanspringbootproject.auth.repositories.UserRepository;
import com.kawser.cleanspringbootproject.auth.services.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for handling the authorization operations.
 */
@Service
public class AuthorizationService implements IAuthorizationService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method will load the user by its username.
     * @param username Username to load the user
     * @throws UsernameNotFoundException if the user is not found
     * @return UserDetails object with the user information
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User userDetails = (User) authentication.getPrincipal();
            return userDetails.getId();
        }
        return 0;
    }
    
}