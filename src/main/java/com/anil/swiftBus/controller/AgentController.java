package com.anil.swiftBus.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.service.AgentCommissionLedgerService;
import com.anil.swiftBus.service.BookingService;
import com.anil.swiftBus.service.UserService;

@Controller
@RequestMapping("/agent")
public class AgentController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private AgentCommissionLedgerService agentCommissionLedgerService;
    
    @GetMapping({"/home","/home.html","","/"})
    public String adminHome(Model model,Principal principal) {
   	 	String username = principal.getName(); 
	    UserDTO user = userService.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found!")); 
	    
	    Long totalActiveBookingAgent = bookingService.getBookingTicketsByUser(Long.parseLong(user.getId()))
	    	    .stream()
	    	    .filter(s -> 
	    	        BookingStatus.COMPLETED.name().equals(s.getBookingStatus()) ||
	    	        BookingStatus.CONFIRMED.name().equals(s.getBookingStatus())
	    	    )
	    	    .count();

    	Long totalCommission = agentCommissionLedgerService.findByCommissionAgentId(Long.parseLong(user.getId()));
    	model.addAttribute("totalActiveBookingAgent", totalActiveBookingAgent);
    	model.addAttribute("totalCommission", totalCommission);
        return "home"; 
    }
    
    @GetMapping("/my-commissions")
    public String listAgentCommission(Model model,Principal principal) {
    	 String username = principal.getName(); 
 	    UserDTO user = userService.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found!")); 
 	    
		model.addAttribute("commissions", agentCommissionLedgerService.findByAgentId(Long.parseLong(user.getId())));
        return "my-commission-list";
    }
}
