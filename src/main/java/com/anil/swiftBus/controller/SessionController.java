package com.anil.swiftBus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @GetMapping("/sessionExpired")
    public String sessionExpired() {
        return "session-expired"; 
    }
}