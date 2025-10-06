package com.anil.swiftBus.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anil.swiftBus.dto.AgentSummaryDTO;
import com.anil.swiftBus.service.AgentCommissionLedgerService;
import com.anil.swiftBus.service.BookingService;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

	private final AgentCommissionLedgerService agentCommissionLedgerService;
	private final BookingService bookingService;
	public AdminBookingController( AgentCommissionLedgerService agentCommissionLedgerService,
			BookingService bookingService) {
		
		this.agentCommissionLedgerService = agentCommissionLedgerService;
		
		this.bookingService = bookingService;
	}
	
	@GetMapping("/booking-list")
    public String listTrips(Model model) {
		model.addAttribute("bookings", bookingService.getBookingTicketsForAdmin());
        return "booking-list";
    }
	
	@GetMapping("/agent-commissions")
    public String listAgentCommission(Model model) {
		model.addAttribute("commissions", agentCommissionLedgerService.findAll());
        return "commission-list";
    }
	
	@GetMapping("/agent-commissions-summary")
	public String agentSummary(
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        @RequestParam(required = false) Boolean  settled,
	        Model model) {

	    if (startDate == null) startDate = LocalDate.now().minusMonths(1); // default last 1 month
	    if (endDate == null) endDate = LocalDate.now();

	    List<AgentSummaryDTO> summaries = agentCommissionLedgerService.getAgentWiseSummaryByDate(startDate, endDate,settled);
	    model.addAttribute("agentSummaries", summaries);
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
	    model.addAttribute("settled", settled != null ? settled : false);
	    return "agent-commission-summary";
	}

	
}
