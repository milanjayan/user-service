package com.ecommerce.userservice.wrappers;

import com.ecommerce.userservice.dtos.SignupRequestDto;
import com.ecommerce.userservice.dtos.UserDto;
import com.ecommerce.userservice.models.User;

public class UserWrapper {

    public static User entityFrom(SignupRequestDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(dto.getPassword())
                .build();
    }
    public static UserDto dtoFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .build();
    }
}
