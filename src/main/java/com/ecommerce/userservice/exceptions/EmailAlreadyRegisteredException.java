package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class EmailAlreadyRegisteredException extends Exception {
    private String email;
    public EmailAlreadyRegisteredException(String email) {
        this.email = email;
    }
}
