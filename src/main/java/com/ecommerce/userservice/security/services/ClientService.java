package com.ecommerce.userservice.security.services;

import com.ecommerce.userservice.exceptions.ClientNotFoundException;
import com.ecommerce.userservice.exceptions.NoClientsFoundException;
import com.ecommerce.userservice.security.models.Client;
import com.ecommerce.userservice.security.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Client getClientByClientId(String clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByClientId(clientId);
        optionalClient.orElseThrow(() -> new ClientNotFoundException("Client with client-id : "+clientId+" not found"));
        return optionalClient.get();
    }

    public List<Client> getAllClients() throws NoClientsFoundException {
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty()) {
            throw new NoClientsFoundException("There are no registered clients");
        }
        return clients;
    }

    public void registerClient(Client client) {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(client.getClientId())
                .clientSecret(bCryptPasswordEncoder.encode(client.getClientSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri(client.getRedirectUris())
                .postLogoutRedirectUri(client.getPostLogoutRedirectUris())
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(client.getScopes())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        jpaRegisteredClientRepository.save(oidcClient);
    }

    public void deleteClient(String clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByClientId(clientId);
        optionalClient.orElseThrow(() -> new ClientNotFoundException("Client with client id: "+clientId+" Not found"));
        clientRepository.delete(optionalClient.get());
    }
}
