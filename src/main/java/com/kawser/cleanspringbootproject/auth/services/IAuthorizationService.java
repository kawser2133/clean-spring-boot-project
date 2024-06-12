package com.kawser.cleanspringbootproject.auth.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthorizationService {

    UserDetails loadUserByUsername(String username);
    long getCurrentUserId();
}
