package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class RoleNameMissingException extends Exception {
    private String message;
    public RoleNameMissingException(String message) {
        this.message = message;
    }
}
