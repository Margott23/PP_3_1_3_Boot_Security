package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String startPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admins/admin";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id == null) {
            return "admins/admin";
        } else {
            model.addAttribute("user", userService.findById(id));
            return "admins/user";
        }
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admins/new_user";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id == null) {
            model.addAttribute("users", userService.findAll());
            return "admins/admin";
        } else {
            model.addAttribute("user", userService.findById(id));
            return "admins/edit_user";
        }
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam(value = "id", required = false) Integer id,
                             @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
