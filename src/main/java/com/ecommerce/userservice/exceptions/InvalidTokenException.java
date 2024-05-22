package com.ecommerce.userservice.exceptions;

public class InvalidTokenException extends Exception {
    private String message;
    public InvalidTokenException(String message) {
        this.message = message;
    }
}
