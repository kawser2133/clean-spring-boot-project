package com.kawser.cleanspringbootproject.exception.auth.domain.user;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class EmailAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public EmailAlreadyExistsException(String email) {
        super(bundle.getString("user.email_already_exists").replace("{email}", email));
        log.error("User with email {} already exists", email);
    }
}
