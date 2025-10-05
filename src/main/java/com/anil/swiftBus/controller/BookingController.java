package com.anil.swiftBus.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anil.swiftBus.dto.BookingRequestDTO;
import com.anil.swiftBus.dto.BookingResponseDTO;
import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.BusSeatDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.dto.TripSearchDTO;
import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.service.BookingService;
import com.anil.swiftBus.service.BusService;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.RouteStopService;
import com.anil.swiftBus.service.TripService;
import com.anil.swiftBus.service.UserService;

@Controller
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private CityService cityService;
		
	@Autowired
	private RouteStopService routeStopService;

    private final TripService tripService;
    private final BusService busService;
    private final UserService userService;
    private final BookingService bookingService;

    public BookingController(TripService tripService, BusService busService, UserService userService,BookingService bookingService) {
        this.tripService = tripService;
        this.busService = busService;
        this.userService = userService;
        this.bookingService = bookingService;
    }
    
    @GetMapping({"/",""})
	public String bookingPage(Model model) {
	    model.addAttribute("stops", cityService.getAllCities());
	    return "booking";
	}
    
    @GetMapping("/search")
    public String searchBuses(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            Model model) {

		if (from == null || to == null || date == null) {
            model.addAttribute("error", "Invalid search parameters");
            return "booking";
        }

        // 1. fetch TripDTOs from tripService (should return trips for that date & route)
        List<TripSearchDTO> tripDtos = tripService.findTripsForJourney(from, to, date);

       
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("date", date);
        model.addAttribute("trips", tripDtos);
        model.addAttribute("resultCount", tripDtos.size());
        List<String> policyPoints = Arrays.asList(
        	    "Pets are not allowed.",
        	    "Carrying or consuming liquor inside the bus is prohibited. Bus operator reserves the right to deboard drunk passengers.",
        	    "Bus operator is not obligated to wait beyond the scheduled departure time of the bus. No refund request will be entertained for late arriving passengers.",
        	    "Ticket cannot be cancelled after scheduled bus departure time from the first boarding point.",
        	    "Any other company policy applicable."
        	);
        	model.addAttribute("policyList", policyPoints);


        return "booking-result";
    }

    // Seat select page
    @GetMapping("/seat-select/{tripId}/{fareSegmentId}/{busId}")
    public String seatSelect(
            @PathVariable Long tripId,
            @PathVariable Long fareSegmentId,
            @PathVariable Long busId,
            Model model,
            Authentication authentication) {
    	   try {
    	if (tripId == null || fareSegmentId == null || busId == null) {
            model.addAttribute("error", "Invalid search parameters");
            return "booking";
        }
    	UserDTO userDTO = null;
    	if (authentication != null && authentication.isAuthenticated()) {
        	userDTO = userService.findUserByUsername(authentication.getName()).orElseThrow(()-> new IllegalArgumentException("Not Authenticated!"));
        	
        }
    	
        //Get trip details
        TripSearchDTO trip = tripService.getTripByIdFareIdBusId(tripId,fareSegmentId,busId);

        //Get bus seats
        BusDTO bus = busService.getBus(busId);
        
        BusDTO busBooked = busService.getBusWithBookedStatus(bus,tripId);

        
        model.addAttribute("trip", trip);
        model.addAttribute("bus", busBooked);
        model.addAttribute("passenger", userDTO);
     
        } catch (IllegalArgumentException e) {
        	model.addAttribute("error", e.getMessage());
            return "redirect:/booking";
        }catch (Exception e) {
        	model.addAttribute("error", e.getMessage());
        	return "redirect:/booking";
        }
        return "seat-select"; 
    }
    
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<BookingResponseDTO> confirmBooking(@RequestBody BookingRequestDTO request) {
        try {
            BookingResponseDTO response = bookingService.confirmBooking(request);
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BookingResponseDTO error = new BookingResponseDTO();
            error.setStatus("FAILED");
            error.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Redirect page after successful booking
    @GetMapping("/success")
    public String bookingSuccess(@RequestParam("bookingId") Long bookingId,
                                 org.springframework.ui.Model model) {
        model.addAttribute("bookingId", bookingId);
        return "booking"; 
    }

    
}
