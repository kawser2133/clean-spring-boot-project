package com.kawser.cleanspringbootproject.exception.auth.domain.authentication;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class InvalidCredentialsException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public InvalidCredentialsException() {
        super(bundle.getString("auth.invalid_credentials"));
        log.error(bundle.getString("auth.invalid_credentials"));
    }
    
    public InvalidCredentialsException(Throwable cause) {
        super(bundle.getString("auth.invalid_credentials"), cause);
        log.error(bundle.getString("auth.invalid_credentials"), cause);
    }
}
