package com.ecommerce.userservice.services;

import com.ecommerce.userservice.exceptions.NoRolesFoundException;
import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role getRoleById(Long id) throws RoleNotFoundException {
        Optional<Role> optionalRole = roleRepository.findById(id);
        optionalRole.orElseThrow(() -> new RoleNotFoundException("Role with id: "+id+" not found"));
        return optionalRole.get();
    }

    public List<Role> getAllRoles() throws NoRolesFoundException {
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()) {
            throw new NoRolesFoundException("There are no roles defined");
        }
        return roles;
     }

    public Role findRoleByName(String roleName) throws RoleNotFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        optionalRole.orElseThrow(() -> new RoleNotFoundException("Role with name: "+roleName+" not found"));
        return optionalRole.get();
    }

    public List<Role> findRolesByNameIn(List<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }

    public Role createRole(Role role) {
        role.setName(role.getName().toLowerCase());
        return roleRepository.findByName(role.getName())
                .orElseGet(()-> roleRepository.save(role));
    }
}
