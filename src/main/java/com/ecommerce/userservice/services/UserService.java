package com.ecommerce.userservice.services;

import com.ecommerce.userservice.configs.KafkaService;
import com.ecommerce.userservice.dtos.SendEmailMessageDto;
import com.ecommerce.userservice.exceptions.*;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.network.Send;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private KafkaService kafkaService;
    private ObjectMapper objectMapper;

    public User signup(User user) throws PasswordLengthTooShortException, EmailAlreadyRegisteredException, JsonProcessingException {
        final String KAFKA_TOPIC_SEND_EMAIL = "sendEmail";
        validate(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser =  userRepository.save(user);
        SendEmailMessageDto sendEmailMessageDto = SendEmailMessageDto.builder()
                .to(savedUser.getEmail())
                .subject("Welcome Note")
                .body("Welcome "+savedUser.getName()+"."+" Thank you for joining our ecom platform! Explore amazing products and enjoy a seamless shopping experience.")
                .build();
        kafkaService.sendMessage(KAFKA_TOPIC_SEND_EMAIL, objectMapper.writeValueAsString(sendEmailMessageDto));
        return savedUser;
    }

    private void validate(User user) throws PasswordLengthTooShortException, EmailAlreadyRegisteredException {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
            throw new EmailAlreadyRegisteredException(user.getEmail());
        }
        int MINIMUM_PASSWORD_LENGTH = 8;
        if(user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new PasswordLengthTooShortException("Password should be at least "+MINIMUM_PASSWORD_LENGTH+" characters long");
        }
    }

    public User grantRoles(String email, List<Role> roles) throws UserNotFoundException, RolesNotDefinedException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with email "+email+" not found"));
        User user = optionalUser.get();
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .toList();
        List<Role> definedRoles = roleService.findRolesByNameIn(roleNames);
        List<String> definedRoleNames = definedRoles.stream()
                .map(Role::getName)
                .toList();
        validate(roleNames, definedRoleNames);
        user.setRoles(definedRoles);
        return userRepository.save(user);
    }

    public void validate(List<String> roleNames, List<String> definedRoleNames) throws RolesNotDefinedException {
        List<String> invalidRoleNames = roleNames.stream().map(String::toLowerCase)
                .filter(roleName -> !definedRoleNames.contains(roleName))
                .toList();
        if(!invalidRoleNames.isEmpty()) {
            throw new RolesNotDefinedException("These role(s) are not defined "+invalidRoleNames);
        }
    }


//    public Token login(String email, String password) throws UserNotFoundException, PasswordDoesNotMatchException, TokenLimitExceededException {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        optionalUser.orElseThrow(() -> new UserNotFoundException("User with email "+email+" not found"));
//        User user = optionalUser.get();
//        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//            throw new PasswordDoesNotMatchException(password);
//        }
//        int MAX_TOKEN_COUNT = 2;
//        if(user.getTokenCount() >= MAX_TOKEN_COUNT) {
//            throw new TokenLimitExceededException(MAX_TOKEN_COUNT);
//        }
//        Token token = generateToken(user);
//        int tokenCount = user.getTokenCount() + 1;
//        user.setTokenCount(tokenCount);
//        userRepository.save(user);
//        return tokenRepository.save(token);
//    }
//
//
//    private Token generateToken(User user) {
//        long NUMBER_OF_DAYS = 30L;
//        LocalDate today = LocalDate.now();
//        LocalDate dateAfter30days = today.plusDays(NUMBER_OF_DAYS);
//        Date expiryDate = Date.from(dateAfter30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        return Token.builder()
//                .value(RandomStringUtils.randomAlphanumeric(128))
//                .expiryDate(expiryDate)
//                .type(TokenType.ACCESS)
//                .user(user)
//                .build();
//    }
//    public void logout(String tokenValue) throws InvalidTokenException {
//        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeleted(tokenValue, false);
//        optionalToken.orElseThrow(() -> new InvalidTokenException("Token is invalid"));
//        Token token = optionalToken.get();
//        token.setDeleted(true);
//        tokenRepository.save(token);
//        User user = token.getUser();
//        int tokenCount = user.getTokenCount();
//        user.setTokenCount(tokenCount-1);
//        userRepository.save(user);
//    }
//
//    public User verifyToken(String tokenValue) throws InvalidTokenException, TokenExpiredException {
//        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeleted(tokenValue, false);
//        optionalToken.orElseThrow(() -> new InvalidTokenException("Token is invalid"));
//        Token token = optionalToken.get();
//        Date currentDate = new Date();
//        if(currentDate.after(token.getExpiryDate())) {
//            token.setDeleted(true);
//            tokenRepository.save(token);
//            throw new TokenExpiredException("Token has expired");
//        }
//        return token.getUser();
//    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with id "+id+" not found"));
        return optionalUser.get();
    }


}
