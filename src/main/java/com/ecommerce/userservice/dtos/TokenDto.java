package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.TokenType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class TokenDto {
    private String value;
    private Date expiryDate;
    private TokenType type;
    private Long userId;

    public static TokenDto from(Token token) {
        return TokenDto.builder()
                .value(token.getValue())
                .expiryDate(token.getExpiryDate())
                .type(token.getType())
                .userId(token.getUser().getId())
                .build();
    }
}
