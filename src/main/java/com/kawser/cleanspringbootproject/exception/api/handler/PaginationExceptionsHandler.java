package com.kawser.cleanspringbootproject.exception.api.handler;

import com.kawser.cleanspringbootproject.exception.api.domain.pagination.InvalidArgumentsToPaginationException;
import com.kawser.cleanspringbootproject.exception.api.domain.pagination.InvalidSortDirectionException;
import com.kawser.cleanspringbootproject.exception.message.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is responsible for handling exceptions related to pagination.
 */
@ControllerAdvice
public class PaginationExceptionsHandler extends ResponseEntityExceptionHandler {
    
    /**
     * This method handles InvalidArgumentsToPaginationException. It returns a response with status 400.
     * @param ex InvalidArgumentsToPaginationException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(InvalidArgumentsToPaginationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleInvalidArgumentsToPaginationException(InvalidArgumentsToPaginationException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles InvalidSortDirectionException. It returns a response with status 400.
     * @param ex InvalidSortDirectionException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(InvalidSortDirectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleInvalidSortDirectionException(InvalidSortDirectionException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

}
