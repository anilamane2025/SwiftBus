package com.anil.swiftBus.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.service.BookingService;
import com.anil.swiftBus.service.UserService;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping({"/home","/home.html","","/"})
    public String passengerHome(Model model,Principal principal) {
   	 	String username = principal.getName(); 
	    UserDTO user = userService.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found!")); 
	    
	    Long totalActiveBooking = bookingService.getBookingTicketsByUser(Long.parseLong(user.getId()))
	    	    .stream()
	    	    .filter(s -> 
	    	        BookingStatus.COMPLETED.name().equals(s.getBookingStatus()) ||
	    	        BookingStatus.CONFIRMED.name().equals(s.getBookingStatus())
	    	    )
	    	    .count();

    	model.addAttribute("totalActiveBooking", totalActiveBooking);
    	
        return "home"; 
    }
}
