package com.kawser.cleanspringbootproject.exception.api.handler;

import com.kawser.cleanspringbootproject.exception.api.domain.product.InvalidProductNameException;
import com.kawser.cleanspringbootproject.exception.api.domain.product.ProductNotFoundException;
import com.kawser.cleanspringbootproject.exception.api.domain.product.ProductsEmptyException;
import com.kawser.cleanspringbootproject.exception.message.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is responsible for handling exceptions related to products.
 */
@ControllerAdvice
public class ProductExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles ProductNotFoundException. It returns a response with status 404.
     * @param ex ProductNotFoundException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleProductNotFoundException(ProductNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles ProductsEmptyException. It returns a response with status 404.
     * @param ex ProductsEmptyException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(ProductsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleProductsEmptyException(ProductsEmptyException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles InvalidProductNameException. It returns a response with status 400.
     * @param ex InvalidProductNameException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(InvalidProductNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleInvalidProductNameException(InvalidProductNameException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
