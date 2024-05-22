package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class RequiredRoleMissingException extends Throwable {

    private String requiredRole;
    public RequiredRoleMissingException(String requiredRole) {
        this.requiredRole = requiredRole;
    }
}
