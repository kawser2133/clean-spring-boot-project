package com.kawser.cleanspringbootproject.exception.global.handler;

import com.kawser.cleanspringbootproject.exception.api.domain.common.ModelValidationException;
import com.kawser.cleanspringbootproject.exception.message.RestErrorMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling global exceptions.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionsHandler {

    /**
     * This method handles MethodArgumentNotValidException. It returns a response with status 400.
     * The error message contains the fields and their respective error messages.
     * The error message format is: {field1=message1, field2=message2, ...}
     * @param ex MethodArgumentNotValidException
     * @param request WebRequest
     * @return ResponseEntity<RestErrorMessage> with status 400 and the error message with the fields and their respective error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            WebRequest request) {

        // Get the field errors and put them in a map
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // Error message format: {field1=message1, field2=message2, ...}
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("{");
        errors.forEach((key, value) -> errorMessage.append(key).append("=").append(value).append(", "));
        if (errorMessage.length() > 1) { // Verify if there is any error message
            errorMessage.setLength(errorMessage.length() - 2); // Remove the last comma and space ", "
        }
        errorMessage.append("}");

        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles ConstraintViolationException. It returns a response with status 400.
     * The error message contains the fields and their respective error messages.
     * The error message format is: {field1=message1, field2=message2, ...}
     * @param ex ConstraintViolationException
     * @param request WebRequest
     * @return ResponseEntity<RestErrorMessage> with status 400 and the error message with the fields and their respective error messages
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleConstraintViolationException(ConstraintViolationException ex,
            WebRequest request) {

        // Get the constraint violations and put them in a map
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        // Error message format: {field1=message1, field2=message2, ...}
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("{");
        errors.forEach((key, value) -> errorMessage.append(key).append("=").append(value).append(", "));
        if (errorMessage.length() > 1) { // Verify if there is any error message
            errorMessage.setLength(errorMessage.length() - 2); // Remove the last comma and space ", "
        }
        errorMessage.append("}");

        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles ResponseStatusException. It returns a response with the status and the reason.
     * @param ex ResponseStatusException
     * @return ResponseEntity<RestErrorMessage> with the status and the reason
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RestErrorMessage> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatusCode httpStatusCode = ex.getStatusCode();
        HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode.value());

        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(httpStatus, ex.getReason());
        return ResponseEntity.status(httpStatus).body(threatResponse);
    }

    @ExceptionHandler(ModelValidationException.class)
    public ResponseEntity<String> handleModelValidationException(ModelValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}