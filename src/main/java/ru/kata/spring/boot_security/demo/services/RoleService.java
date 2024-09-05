package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

public interface RoleService {

    void updateRoleForUser(User user);
}
