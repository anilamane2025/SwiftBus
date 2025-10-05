package com.anil.swiftBus.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.service.BookingService;
import com.anil.swiftBus.service.BusService;
import com.anil.swiftBus.service.RouteService;
import com.anil.swiftBus.service.TripService;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

	private final TripService tripService;
	private final BusService busService;
	private final RouteService routeService;
	private final BookingService bookingService;
	public AdminBookingController(TripService tripService, BusService busService, RouteService routeService,
			BookingService bookingService) {
		this.tripService = tripService;
		this.busService = busService;
		this.routeService = routeService;
		this.bookingService = bookingService;
	}
	
	@GetMapping("/booking-list")
    public String listTrips(Model model) {
        model.addAttribute("trips", tripService.getAllTrips());
        
        return "booking-trips-list";
    }
	
	





}
