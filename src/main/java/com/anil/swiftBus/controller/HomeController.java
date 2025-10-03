package com.anil.swiftBus.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.RouteStopService;
import com.anil.swiftBus.service.TripService;

@Controller
public class HomeController {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private RouteStopService routeStopService;

	@GetMapping("/")
    public String Home() {
        return "index"; // index.jsp
    }
	
	@GetMapping("/booking")
	public String bookingPage(Model model) {
	    model.addAttribute("stops", cityService.getAllCities());
	    return "booking";
	}
	
	@GetMapping("/api/stops/search")
    @ResponseBody
    public List<Map<String,Object>> searchStops(@RequestParam("q") String q) {
        // routeStopService.searchEnabledStopsByNameOrCity(q) -> List<RouteStop>
        List<RouteStopDTO> results = routeStopService.searchEnabledStopsByNameOrCity(q, 10);
        return results.stream().map(s -> {
            Map<String,Object> m = new HashMap<>();
            m.put("routeStopId", s.getRouteStopId());
            m.put("displayName", s.getStopName() + " (" + s.getCityName()+")");
            return m;
        }).collect(Collectors.toList());
    }
	
	@GetMapping("/search-trips")
	public String searchTrips(@RequestParam Long fromStopId,
	                          @RequestParam Long toStopId,
	                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	                          Model model) {
	  
	    if (fromStopId == null || toStopId == null || date == null) {
            model.addAttribute("error", "Invalid search parameters");
            return "booking";
        }
        // find trips that run on this route and day, and pass from->to in correct order
        List<TripDTO> trips = tripService.findTripsForJourney(fromStopId, toStopId, date);
        model.addAttribute("trips", trips);
        // add human readable stops
        model.addAttribute("fromStop", routeStopService.getById(fromStopId));
        model.addAttribute("toStop", routeStopService.getById(toStopId));
        model.addAttribute("journeyDate", date);
        return "booking-results"; // JSP below
	}


}
