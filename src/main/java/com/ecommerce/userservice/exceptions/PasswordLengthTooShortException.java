package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class PasswordLengthTooShortException extends Exception {
    private String message;
    public PasswordLengthTooShortException(String message) {
        this.message = message;
    }
}
