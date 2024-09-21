package com.ecommerce.userservice.exceptions;


import com.ecommerce.userservice.dtos.ExceptionDto;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(SignupCredentialMissingException.class)
    public ResponseEntity<ExceptionDto> handleSignupCredentialMissingException(SignupCredentialMissingException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Signup credential "+exception.getCredential()+" missing")
                .resolution("Try again with all credentials")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionDto> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Email address "+exception.getEmail()+" already registered")
                .resolution("Try again with another email address")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginCredentialMissingException.class)
    public ResponseEntity<ExceptionDto> handleLoginCredentialMissingException(LoginCredentialMissingException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Login credential "+exception.getCredential()+" missing")
                .resolution("Try again with all login credentials")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<ExceptionDto> handlePasswordDoesNotMatchException(PasswordDoesNotMatchException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Password incorrect")
                .resolution("Try again with different password")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordLengthTooShortException.class)
    public ResponseEntity<ExceptionDto> handlePasswordLengthTooShortException(PasswordLengthTooShortException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Try again with different password")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Try different user")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRolesFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoRolesFoundException(NoRolesFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Create at least one role")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleRoleNotFoundException(RoleNotFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Give another id")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequiredRoleMissingException.class)
    public ResponseEntity<ExceptionDto> handleRequiredRoleMissingException(RequiredRoleMissingException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("The required role "+exception.getRequiredRole()+" for accessing this api is missing")
                .resolution("Try again after acquiring required role")
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleClientNotFoundException(ClientNotFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Try again with different client id")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoClientsFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoClientsFoundException(NoClientsFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Try again after registering a client")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientRegistrationCredentialMissingException.class)
    public ResponseEntity<ExceptionDto> handleClientRegistrationCredentialMissingException(ClientRegistrationCredentialMissingException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution(exception.getResolution())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
