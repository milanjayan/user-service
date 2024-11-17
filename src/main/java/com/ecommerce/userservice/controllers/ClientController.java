package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.ClientDto;
import com.ecommerce.userservice.dtos.RegisterClientDto;
import com.ecommerce.userservice.dtos.ResponseDto;
import com.ecommerce.userservice.exceptions.ClientNotFoundException;
import com.ecommerce.userservice.exceptions.ClientRegistrationCredentialMissingException;
import com.ecommerce.userservice.exceptions.NoClientsFoundException;
import com.ecommerce.userservice.security.models.Client;
import com.ecommerce.userservice.security.services.ClientService;
import com.ecommerce.userservice.wrappers.ClientWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> getClientByClientId(@PathVariable String clientId) throws ClientNotFoundException {
        Client client = clientService.getClientByClientId(clientId);
        ClientDto responseDto = ClientWrapper.dtoFrom(client);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getAllClients() throws NoClientsFoundException {
        List<Client> clients = clientService.getAllClients();
        List<ClientDto> clientDtos = clients.stream()
                .map(ClientWrapper::dtoFrom)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }

    @PostMapping("/register-client")
    public ResponseEntity<ResponseDto> registerClient(@RequestBody RegisterClientDto registerClientDto) throws ClientRegistrationCredentialMissingException {
        validate(registerClientDto);
        Client client = ClientWrapper.entityFrom(registerClientDto);
        clientService.registerClient(client);
        ResponseDto responseDto = ResponseDto.builder().message("Client Registered").build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{clientId}")
    public ResponseEntity<ResponseDto> deleteClient(@PathVariable String clientId) throws ClientNotFoundException {
        clientService.deleteClient(clientId);
        ResponseDto responseDto = ResponseDto.builder().message("Client Deleted").build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private void validate(RegisterClientDto registerClientDto) throws ClientRegistrationCredentialMissingException {
        if(registerClientDto.getClientId().isBlank()) {
            throw new ClientRegistrationCredentialMissingException("Client Id is missing", "Please provide a client id");
        }
        if(registerClientDto.getClientSecret().isBlank()) {
            throw new ClientRegistrationCredentialMissingException("Client Secret is missing", "Please provide a client secret");
        }
        if(registerClientDto.getRedirectUri().isBlank()) {
            throw new ClientRegistrationCredentialMissingException("Redirect Uri is missing", "Please provide a redirect uri");
        }
        if(registerClientDto.getPostLogoutRedirectUri().isBlank()) {
            throw new ClientRegistrationCredentialMissingException("Post Logout Uri is missing", "Please provide a post logout uri");
        }
    }

}
