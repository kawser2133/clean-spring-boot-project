package com.kawser.cleanspringbootproject.exception.api.domain.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class ModelValidationException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when there is a validation error for a model.
     *
     * @param messageKey the key of the message in the resource bundle.
     * @param placeholders the placeholders to be replaced in the message.
     */
    public ModelValidationException(String messageKey, Object... placeholders) {
        super(formatMessage(messageKey, placeholders));
        log.error("Invalid model state: {}", formatMessage(messageKey, placeholders));
    }

    private static String formatMessage(String messageKey, Object... placeholders) {
        String message = bundle.getString(messageKey);
        for (int i = 0; i < placeholders.length; i++) {
            message = message.replace("{" + i + "}", placeholders[i].toString());
        }
        return message;
    }
}