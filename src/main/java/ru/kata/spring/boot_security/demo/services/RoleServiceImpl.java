package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void updateRoleForUser(User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            Role rRole = roleRepository.findByRole(role.getRole());
            if (rRole == null) {
                rRole = roleRepository.save(role);
            }
            roles.add(rRole);
        }
        user.setRoleList(roles);
    }
}
