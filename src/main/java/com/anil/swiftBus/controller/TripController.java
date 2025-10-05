package com.anil.swiftBus.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.enums.TripStatus;
import com.anil.swiftBus.service.BusService;
import com.anil.swiftBus.service.RouteService;
import com.anil.swiftBus.service.TripService;

@Controller
@RequestMapping("/admin/trips")
public class TripController {

    private final TripService tripService;
    private final BusService busService;
    private final RouteService routeService;

    public TripController(TripService tripService, BusService busService, RouteService routeService) {
        this.tripService = tripService;
        this.busService = busService;
        this.routeService = routeService;
    }

    @GetMapping("/list")
    public String listTrips(Model model) {
        model.addAttribute("trips", tripService.getAllTrips());
        return "trips-list";
    }

    @GetMapping("/add")
    public String addTripForm(Model model) {
        model.addAttribute("trip", new TripDTO());
        model.addAttribute("statuses", TripStatus.values());
        model.addAttribute("buses", busService.getAllBuses());
        model.addAttribute("routes", routeService.getAll());
        model.addAttribute("editable", true);
        return "trips-add";
    }

    @GetMapping("/edit/{id}")
    public String editTripForm(@PathVariable("id") Long id, Model model) {
        TripDTO trip = tripService.getTripById(id);

        if (trip == null) {
            return "redirect:/admin/trips/list";
        }

        // If trip already booked (service will check bookings), prevent editing bus/route/departure
        boolean editable = tripService.isEditable(trip.getTripId());

        model.addAttribute("trip", trip);
        model.addAttribute("statuses", TripStatus.values());
        model.addAttribute("buses", busService.getAllBuses());
        model.addAttribute("routes", routeService.getAll());
        LocalDateTime departureDateTime = trip.getDepartureDatetime();
        LocalDateTime now = LocalDateTime.now();

        // Check if the departure time is after now AND if it's more than 1 hour away
        if(editable) {
        if (departureDateTime.isAfter(now) && Duration.between(now, departureDateTime).toHours() > 2) {
            editable = true;
        } else {
            editable = false;
        }
        }
        model.addAttribute("editable", editable);
        return "trips-add";
    }

    @PostMapping("/save")
    public String saveTrip(
            @Valid @ModelAttribute("trip") TripDTO tripDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
    	if (tripDTO.getTripId() == null) {
    		model.addAttribute("editable", true);
    	}else {
    		boolean editable = tripService.isEditable(tripDTO.getTripId());
    		model.addAttribute("editable", editable);
    	}

        if (result.hasErrors()) {
            model.addAttribute("buses", busService.getAllBuses());
            model.addAttribute("routes", routeService.getAll());
            System.out.println("===========------"+result);
            return "trips-add"; 
        }
        if (!tripDTO.getDepartureDatetime().toLocalDate().equals(tripDTO.getServiceDate())) {
        	result.rejectValue("departureDatetime", "error.TripDTO", "Departure datetime must be on the service date.");
        	return "trips-add";
          //  throw new IllegalArgumentException("Departure datetime must be on the service date.");
        }


        try {
        	if (tripDTO.getTripId() == null) {
                tripService.createTrip(tripDTO);
                redirectAttributes.addFlashAttribute("success", "Trip created successfully.");
            } else {
                tripService.updateTrip(tripDTO);
                redirectAttributes.addFlashAttribute("success", "Trip updated successfully.");
            }
        } catch (IllegalArgumentException e) {
        	model.addAttribute("error", e.getMessage());
            model.addAttribute("buses", busService.getAllBuses());
            model.addAttribute("routes", routeService.getAll());
             return "trips-add";
        }catch (Exception e) {
        	model.addAttribute("error", e.getMessage());
            model.addAttribute("buses", busService.getAllBuses());
            model.addAttribute("routes", routeService.getAll());
             return "trips-add";
        }
        return "redirect:/admin/trips/list";
    }

    @GetMapping("/cancel/{id}")
    public String cancelTrip(@PathVariable("id") Long id) {
        tripService.deleteTrip(id); // Soft delete (set status = CANCELLED)
        return "redirect:/admin/trips/list";
    }
}
