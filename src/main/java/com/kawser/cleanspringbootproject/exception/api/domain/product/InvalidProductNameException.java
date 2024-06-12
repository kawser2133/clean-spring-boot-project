package com.kawser.cleanspringbootproject.exception.api.domain.product;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents the exception that is thrown when a product with an invalid name is created.
 * Reference for the error message in the messages.properties file: product.invalid_name 
 * 
 */
@Slf4j
public class InvalidProductNameException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when a product with an invalid name is created.
     * 
     * @param name the name of the product that is invalid.
     * 
     */
    public InvalidProductNameException(String name) {
        super(bundle.getString("product.invalid_name").replace("{name}", name));
        log.error("Invalid product name: {}", name);
    }
}