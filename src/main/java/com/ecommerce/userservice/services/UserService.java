package com.ecommerce.userservice.services;

import com.ecommerce.userservice.exceptions.*;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.TokenType;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.TokenRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public User signup(User user) throws PasswordLengthTooShortException, EmailAlreadyRegisteredException {
        validate(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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

    public Token login(String email, String password) throws UserNotFoundException, PasswordDoesNotMatchException, TokenLimitExceededException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with email "+email+" not found"));
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new PasswordDoesNotMatchException(password);
        }
        int MAX_TOKEN_COUNT = 2;
        if(user.getTokenCount() >= MAX_TOKEN_COUNT) {
            throw new TokenLimitExceededException(MAX_TOKEN_COUNT);
        }
        Token token = generateToken(user);
        int tokenCount = user.getTokenCount() + 1;
        user.setTokenCount(tokenCount);
        userRepository.save(user);
        return tokenRepository.save(token);
    }


    private Token generateToken(User user) {
        long NUMBER_OF_DAYS = 30L;
        LocalDate today = LocalDate.now();
        LocalDate dateAfter30days = today.plusDays(NUMBER_OF_DAYS);
        Date expiryDate = Date.from(dateAfter30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Token.builder()
                .value(RandomStringUtils.randomAlphanumeric(128))
                .expiryDate(expiryDate)
                .type(TokenType.ACCESS)
                .user(user)
                .build();
    }
    public void logout(String tokenValue) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeleted(tokenValue, false);
        optionalToken.orElseThrow(() -> new InvalidTokenException("Token is invalid"));
        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        User user = token.getUser();
        int tokenCount = user.getTokenCount();
        user.setTokenCount(tokenCount-1);
        userRepository.save(user);
    }

    public User verifyToken(String tokenValue) throws InvalidTokenException, TokenExpiredException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeleted(tokenValue, false);
        optionalToken.orElseThrow(() -> new InvalidTokenException("Token is invalid"));
        Token token = optionalToken.get();
        Date currentDate = new Date();
        if(currentDate.after(token.getExpiryDate())) {
            token.setDeleted(true);
            tokenRepository.save(token);
            throw new TokenExpiredException("Token has expired");
        }
        return token.getUser();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new UserNotFoundException("User with id "+id+" not found"));
        return optionalUser.get();
    }

}
