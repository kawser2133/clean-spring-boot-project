package com.kawser.cleanspringbootproject.exception.api.domain.product;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents the exception that is thrown when a product with the given id is not found.
 * Reference for the error message in the messages.properties file: product.not_found
 * 
 */
@Slf4j
public class ProductNotFoundException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when a product with the given id is not found.
     * 
     * @param id the id of the product that is not found.
     * 
     */
    public ProductNotFoundException(Long id) {
        super(bundle.getString("product.not_found").replace("{id}", id.toString()));
        log.error("Product with id {} not found.", id);
    }
}
