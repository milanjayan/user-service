package com.ecommerce.userservice.exceptions;

import lombok.Getter;

@Getter
public class TokenLimitExceededException extends Exception {
    private int tokenLimit;
    public TokenLimitExceededException(int tokenLimit) {
        this.tokenLimit = tokenLimit;
    }
}
