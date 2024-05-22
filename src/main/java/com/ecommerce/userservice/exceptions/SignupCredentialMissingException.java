package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class SignupCredentialMissingException extends Exception {
    private String credential;
    public SignupCredentialMissingException(String credential) {
        this.credential = credential;
    }
}
