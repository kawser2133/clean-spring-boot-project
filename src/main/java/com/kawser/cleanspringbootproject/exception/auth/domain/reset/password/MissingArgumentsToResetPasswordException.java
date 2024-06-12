package com.kawser.cleanspringbootproject.exception.auth.domain.reset.password;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class MissingArgumentsToResetPasswordException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public MissingArgumentsToResetPasswordException() {
        super(bundle.getString("password_reset.arguments_missing"));
        log.error(bundle.getString("password_reset.arguments_missing"));
    }
    
    public MissingArgumentsToResetPasswordException(Throwable cause) {
        super(bundle.getString("password_reset.arguments_missing"), cause);
        log.error(bundle.getString("password_reset.arguments_missing"), cause);
    }
}