package com.ecommerce.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrantRolesDto {
    private String email;
    private List<String> roles;
}
