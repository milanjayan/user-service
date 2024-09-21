package com.ecommerce.userservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegisterClientDto {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String postLogoutRedirectUri;
    private String scopes;
}
