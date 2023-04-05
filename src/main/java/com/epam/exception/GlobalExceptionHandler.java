package com.epam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CryptoServiceException.class)
    public String handleCryptoServiceException(CryptoServiceException ex) {

        String errorMessage = ex.getMessage();

        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException ex) {

        String errorMessage = ex.getMessage();

        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException ex) {

        String errorMessage = "No data found by requested parameters";

        return errorMessage;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(Exception ex) {

        String errorMessage = ex.getMessage();

        return "Unexpected exception: " + errorMessage;
    }
}
