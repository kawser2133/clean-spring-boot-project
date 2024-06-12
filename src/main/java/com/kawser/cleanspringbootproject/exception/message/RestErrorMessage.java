package com.kawser.cleanspringbootproject.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * RestErrorMessage class.
 * This class is responsible for creating a message to be returned in the response body when an exception is thrown.
 * It contains the status and the message.
 */
@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {

    /**
     * Status to be returned in the response.
     */
    private HttpStatus status;

    /**
     * Message to be returned in the response body.
     */
    private String message;

}
