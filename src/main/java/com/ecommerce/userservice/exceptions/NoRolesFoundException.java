package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class NoRolesFoundException extends Exception {
    private String message;
    public NoRolesFoundException(String message) {
        this.message = message;
    }
}
