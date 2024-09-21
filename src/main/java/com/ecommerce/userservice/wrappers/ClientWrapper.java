package com.ecommerce.userservice.wrappers;

import com.ecommerce.userservice.dtos.ClientDto;
import com.ecommerce.userservice.dtos.RegisterClientDto;
import com.ecommerce.userservice.security.models.Client;

import java.time.Instant;

public class ClientWrapper {

    public static ClientDto dtoFrom(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .clientId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(client.getClientAuthenticationMethods())
                .authorizationGrantTypes(client.getAuthorizationGrantTypes())
                .redirectUris(client.getRedirectUris())
                .postLogoutRedirectUris(client.getPostLogoutRedirectUris())
                .scopes(client.getScopes())
                .build();
    }

    public static Client entityFrom(RegisterClientDto registerClientDto) {
        return Client.builder()
                .clientId(registerClientDto.getClientId())
                .clientSecret(registerClientDto.getClientSecret())
                .redirectUris(registerClientDto.getRedirectUri())
                .postLogoutRedirectUris(registerClientDto.getPostLogoutRedirectUri())
                .scopes(registerClientDto.getScopes())
                .build();
    }
}
