package com.kawser.cleanspringbootproject.exception.api.domain.product;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents the exception that is thrown when the products list is empty.
 * Reference for the error message in the messages.properties file: product.empty_list
 * 
 */
@Slf4j
public class ProductsEmptyException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the products list is empty.
     * 
     */
    public ProductsEmptyException() {
        super(bundle.getString("product.empty_list"));
        log.error("Products list is empty.");
    }
}
