package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAOImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAOImpl roleRepository;

    public RoleServiceImpl(RoleDAOImpl roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void updateRoleForUser(User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            Role rRole;
            if (role.getRole().isEmpty()) {
                rRole = roleRepository.findByRole("ROLE_USER");
            } else {
                rRole = roleRepository.findByRole(role.getRole());
            }
            roles.add(rRole);
        }
        user.setRoleList(roles);
    }
}
