package com.ecommerce.userservice.wrappers;

import com.ecommerce.userservice.dtos.GrantRolesDto;
import com.ecommerce.userservice.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleWrapper {
    public static List<Role> entitiesFrom(List<String> dtoRoles) {
        return dtoRoles.stream()
                .map(r -> Role.builder().name(r).build())
                .collect(Collectors.toList());
    }
}
