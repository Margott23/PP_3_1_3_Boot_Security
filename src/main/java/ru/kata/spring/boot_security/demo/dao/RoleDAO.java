package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleDAO {
    Role findByRole(String roleName);

    void save(Role role);

    Role findById(int id);
}
