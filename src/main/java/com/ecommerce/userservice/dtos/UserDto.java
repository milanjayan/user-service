package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder()
public class UserDto {
    private String name;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
}
