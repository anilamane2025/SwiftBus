package com.anil.swiftBus.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.BusSeatDTO;
import com.anil.swiftBus.exception.SeatValidationException;
import com.anil.swiftBus.service.BusService;

@ControllerAdvice
@RequestMapping("/admin/bus")
public class BusController {

    @Autowired
    private BusService busService;
    
    @GetMapping("/bus-list")
    public String showBusList(Model model) {
    	List<BusDTO> buses = busService.getAllBuses();
    	buses.forEach(bus -> bus.getSeats().sort(Comparator.comparingInt(BusSeatDTO::getSeatRow)
                .thenComparingInt(BusSeatDTO::getSeatCol)));
    	  model.addAttribute("buses", buses);
        return "bus-list";
    }
   

    @GetMapping("/add")
    public String showAddForm(Model model) {
    	BusDTO busDTO = new BusDTO();
    	
        busDTO.setSeats(new ArrayList<>());
     
        model.addAttribute("busDTO", busDTO);
        
        return "bus-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('BUS_ADD')")
    public String saveBus(
            @Valid @ModelAttribute("busDTO") BusDTO busDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
    	 if(busService.existsByRegistrationNo(busDTO.getRegistrationNo())) {
             result.rejectValue("registrationNo", "error.busDTO", "Registration number already exists");
             return "bus-add";
         }

        if (result.hasErrors()) {
            return "bus-add";
        }
       
        try {
            busService.saveBusWithSeats(busDTO, result);
            model.addAttribute("success", "Bus saved successfully!");
        } catch (SeatValidationException e) {

            if (result.hasErrors()) {
            	List<String> seatErrors = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList()); 
            	model.addAttribute("seatErrors", seatErrors);
                return "bus-add";
            }
        }catch (Exception e) {
            result.reject("seatError", e.getMessage());
            
            if (result.hasErrors()) {
                return "bus-add";
            }
        }
        redirectAttributes.addFlashAttribute("success", "Bus saved successfully!");
        return "redirect:/admin/bus/bus-list";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BusDTO busDTO = busService.getBus(id); // fetch bus + seats
        System.out.println("updated given"+busDTO);
        model.addAttribute("busDTO", busDTO);
        return "bus-edit";
    }
    
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('BUS_EDIT')")
    public String updateBus(@PathVariable Long id,
            @Valid @ModelAttribute("busDTO") BusDTO busDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
    	System.out.println("updated"+busDTO);
    	
    	if (busService.existsByRegistrationNoAndNotId(busDTO.getRegistrationNo(), id)) {
            result.rejectValue("registrationNo", "error.busDTO", "Registration number already exists");
            return "bus-edit";
        }
    	

        if (result.hasErrors()) {
        	System.out.println("result--"+result);
        	return "bus-edit";
        }
       
        
        try {
            busService.updateBusWithSeats(busDTO, result);
            model.addAttribute("success", "Bus updated successfully!");
        } catch (SeatValidationException e) {
            result.reject("seatError", e.getMessage());
            if (result.hasErrors()) {
            	System.out.println("result--1"+result);
            model.addAttribute("seatErrors", result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "bus-edit";
            }
        } catch (Exception e) {
            result.reject("seatError", "Unexpected error occurred: " + e.getMessage());
            if (result.hasErrors()) {
            return "bus-edit";
            }
        }
        
        return "redirect:/admin/bus/bus-list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('BUS_DELETE')")
    public String deleteBus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
    		busService.deleteBus(id);
            redirectAttributes.addFlashAttribute("success", "Bus deleted successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/bus/bus-list";
    }
}
