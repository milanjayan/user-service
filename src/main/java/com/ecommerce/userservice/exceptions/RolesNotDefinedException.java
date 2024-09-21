package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class RolesNotDefinedException extends Throwable {
    private String message;
    public RolesNotDefinedException(String message) {
        this.message = message;
    }
}
