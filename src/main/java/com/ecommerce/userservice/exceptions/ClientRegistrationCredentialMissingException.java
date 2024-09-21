package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class ClientRegistrationCredentialMissingException extends Exception {
    private String resolution;
    public ClientRegistrationCredentialMissingException(String message, String resolution) {
        super(message);
        this.resolution = resolution;
    }
}
