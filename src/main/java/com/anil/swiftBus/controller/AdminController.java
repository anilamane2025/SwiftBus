package com.anil.swiftBus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anil.swiftBus.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private UserService userService;
    
    @GetMapping({"/home","/home.html","","/"})
    public String adminHome() {
        return "home"; 
    }
}
