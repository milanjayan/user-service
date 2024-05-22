package com.ecommerce.userservice.exceptions;

public class PasswordDoesNotMatchException extends Exception {
    private String password;
    public PasswordDoesNotMatchException(String password) {
        this.password = password;
    }
}
