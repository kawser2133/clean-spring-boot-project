package com.kawser.cleanspringbootproject.exception.auth.domain.user;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class UsernameAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());    

    public UsernameAlreadyExistsException(String username) {
        super(bundle.getString("user.username_already_exists").replace("{username}", username));
        log.error("User with username {} already exists", username);
    }
    
}
