package com.kawser.cleanspringbootproject.exception.auth.handler;

import com.kawser.cleanspringbootproject.exception.auth.domain.authentication.InvalidCredentialsException;
import com.kawser.cleanspringbootproject.exception.auth.domain.authentication.InvalidOtpException;
import com.kawser.cleanspringbootproject.exception.message.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is responsible for handling exceptions related to authentication.
 */
@ControllerAdvice
public class AuthExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles InvalidCredentialsException. It returns a response with status 401.
     * @param ex InvalidCredentialsException
     * @return ResponseEntity<RestErrorMessage> with status 401 and the exception message
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestErrorMessage> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    /**
     * This method handles InvalidOtpException. It returns a response with status 401.
     * @param ex InvalidOtpException
     * @return ResponseEntity<RestErrorMessage> with status 401 and the exception message
     */
    @ExceptionHandler(InvalidOtpException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestErrorMessage> handleInvalidOtpException(InvalidOtpException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }
}
