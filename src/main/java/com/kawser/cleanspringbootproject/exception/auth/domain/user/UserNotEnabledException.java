package com.kawser.cleanspringbootproject.exception.auth.domain.user;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class UserNotEnabledException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public UserNotEnabledException() {
        super(bundle.getString("user.not_enabled"));
        log.error(bundle.getString("user.not_enabled"));
    }
    
    public UserNotEnabledException(Throwable cause) {
        super(bundle.getString("user.not_enabled"), cause);
        log.error(bundle.getString("user.not_enabled"), cause);
    }
}
