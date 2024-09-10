package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        roleService.updateRoleForUser(user);
        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User updateUser) {
        String encryptedPassword = passwordEncoder.encode(updateUser.getPassword());
        updateUser.setPassword(encryptedPassword);
        roleService.updateRoleForUser(updateUser);
        userRepository.updateUser(updateUser);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Login not found"));
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveDefaultUser(User admin) {
        roleService.addDefaultRoles();
        roleService.updateDefaultRolesToDefaultUser(admin);
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        userRepository.save(admin);
    }
}
