package com.epam.exception;

public class CryptoServiceException extends RuntimeException{

    public CryptoServiceException(String message) {
        super(message);
    }
}
