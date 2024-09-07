package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAOImpl;
import ru.kata.spring.boot_security.demo.dao.UserDAOImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleDAOImpl roleRepository;
    private final UserDAOImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleDAOImpl roleRepository, UserDAOImpl userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            User user = new User(
                    "Admin",
                    "",
                    0,
                    "admin",
                    passwordEncoder.encode("admin"),
                    List.of(adminRole));
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            userRepository.save(user);
        }
    }
}
