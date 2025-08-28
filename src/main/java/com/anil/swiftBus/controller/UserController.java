package com.anil.swiftBus.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.enums.UserType;
import com.anil.swiftBus.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("registrationError", "User with this email already exists.");
            return "register";
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        // Assign default role USER
        Role role = new Role(); // Will be set automatically due to cascade persist
        role.setUserType(UserType.PASSENGER);
        user.setRole(role);

        userService.saveUser(user);

        return "redirect:/login?registered";
    }
}