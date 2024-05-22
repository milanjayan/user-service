package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class LoginCredentialMissingException extends Exception {
    private String credential;
    public LoginCredentialMissingException(String credential) {
        this.credential = credential;
    }
}
