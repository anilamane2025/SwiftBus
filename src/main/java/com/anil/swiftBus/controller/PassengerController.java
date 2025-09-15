package com.anil.swiftBus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
	
	@GetMapping({"/home","/home.html","/",""})
    public String passengerHome() {
        return "home"; 
    }
}
