package com.ecommerce.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public BCryptPasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
