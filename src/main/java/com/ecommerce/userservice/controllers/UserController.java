package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.LoginRequestDto;
import com.ecommerce.userservice.dtos.SignupRequestDto;
import com.ecommerce.userservice.dtos.TokenDto;
import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.exceptions.*;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.services.UserService;
import com.ecommerce.userservice.wrappers.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto dto) throws SignupCredentialMissingException, PasswordLengthTooShortException, EmailAlreadyRegisteredException {
        validate(dto);
        User user = UserWrapper.entityFrom(dto);
        User responseUser = userService.signup(user);
        UserDto responseDto = UserWrapper.dtoFrom(responseUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private void validate(SignupRequestDto dto) throws SignupCredentialMissingException {
        if(dto.getName() == null) {
            throw new SignupCredentialMissingException("name");
        }
        if(dto.getEmail() == null) {
            throw new SignupCredentialMissingException("email");
        }
        if(dto.getPhoneNumber() == null) {
            throw new SignupCredentialMissingException("phone number");
        }
        if(dto.getPassword() == null) {
            throw new SignupCredentialMissingException("password");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto dto) throws LoginCredentialMissingException, UserNotFoundException, PasswordDoesNotMatchException, TokenLimitExceededException {
        validate(dto);
        Token token = userService.login(dto.getEmail(), dto.getPassword());
        TokenDto tokenDto = TokenDto.from(token);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
    private void validate(LoginRequestDto dto) throws LoginCredentialMissingException {
        if(dto.getEmail() == null) {
            throw new LoginCredentialMissingException("email");
        }
        if(dto.getPassword() == null) {
            throw new LoginCredentialMissingException("password");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(name = "Authentication") String tokenValue) throws InvalidTokenException {
        userService.logout(tokenValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDto> verifyToken(@RequestHeader(name = "Authentication") String tokenValue) throws InvalidTokenException, TokenExpiredException {
        User user = userService.verifyToken(tokenValue);
        UserDto dto = UserWrapper.dtoFrom(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestHeader(name = "Authentication") String tokenValue, @PathVariable Long id) throws InvalidTokenException, TokenExpiredException, RequiredRoleMissingException, UserNotFoundException {
        String REQUIRED_ROLE = "Admin";
        User requestUser = userService.verifyToken(tokenValue);
        List<Role> roles = requestUser.getRoles();
        for(Role role : roles) {
            if(REQUIRED_ROLE.equalsIgnoreCase(role.getName())) {
               User responseUser = userService.getUserById(id);
               UserDto dto = UserWrapper.dtoFrom(responseUser);
               return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        }
        throw new RequiredRoleMissingException(REQUIRED_ROLE);
    }

    @GetMapping("/random")
    public String getRandom() {
        return "hello random";
    }
}
