package com.ecommerce.userservice.exceptions;


import com.ecommerce.userservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(TokenLimitExceededException.class)
    public ResponseEntity<ExceptionDto> handleTokenLimitExceededException(TokenLimitExceededException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Token limit of "+exception.getTokenLimit()+" exceeded")
                .resolution("Logout a session to login again")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("Try different user")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenException(InvalidTokenException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("Authentication token is invalid")
                .resolution("")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequiredRoleMissingException.class)
    public ResponseEntity<ExceptionDto> handleRequiredRoleMissingException(RequiredRoleMissingException exception) {
        ExceptionDto response = ExceptionDto.builder()
                .message("The required role "+exception.getRequiredRole()+" for accessing this api is missing")
                .resolution("Try again after acquiring required role")
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
