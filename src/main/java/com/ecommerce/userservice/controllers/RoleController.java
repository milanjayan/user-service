package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.exceptions.NoRolesFoundException;
import com.ecommerce.userservice.exceptions.RoleNameMissingException;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.repositories.RoleRepository;
import com.ecommerce.userservice.services.RoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) throws RoleNotFoundException {
        Role role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("roles")
    public ResponseEntity<List<Role>> getAllRoles() throws NoRolesFoundException {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Role> createRole(@RequestParam String roleName) throws RoleNameMissingException {
        if(roleName == null || roleName.isBlank()) {
            throw new RoleNameMissingException("Role name is not provided");
        }
        Role role = Role.builder().name(roleName).build();
        Role responseRole = roleService.createRole(role);
        return new ResponseEntity<>(responseRole, HttpStatus.CREATED);
    }
}
