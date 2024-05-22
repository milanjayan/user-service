package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class TokenExpiredException extends Throwable {

    private String message;
    public TokenExpiredException(String message) {
        this.message = message;
    }
}
