package com.anil.swiftBus.controller;

import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.entity.RouteStopPoint;
import com.anil.swiftBus.enums.StopPointType;
import com.anil.swiftBus.service.RouteStopPointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/route-stop-point")
public class RouteStopPointController {

    @Autowired
    private RouteStopPointService routeStopPointService;
    
    @GetMapping("/list")
    public String listAllStopPoints(Model model) {
        List<RouteDTO> routes = routeStopPointService.getAllRoutesWithStopsAndPoints();
        model.addAttribute("routes", routes);
        return "routes-stop-point-list"; 
    }

    @GetMapping("/list/{routeStopId}")
    public String listStopPoints(@PathVariable("routeStopId") Long routeStopId, Model model) {
        List<RouteStopPointDTO> stopPoints = routeStopPointService.getPointsByStop(routeStopId);
        model.addAttribute("stopPoints", stopPoints);
        model.addAttribute("routeStopId", routeStopId);
        return "routes-stop-point-list"; 
    }

    @PreAuthorize("hasAuthority('ROUTES_STOP_POINT_ADD')")
    @GetMapping("/add/{stopId}")
    public String showAddForm(@PathVariable("stopId") Long routeStopId, Model model) {
        RouteStopPointDTO dto = new RouteStopPointDTO();
        dto.setRouteStopId(routeStopId);
        dto.setEnabled(true);
        model.addAttribute("routeStopPointDTO", dto);
        model.addAttribute("routeStopId", routeStopId);
        model.addAttribute("pointTypes", StopPointType.values());
        return "routes-stop-point-form";
    }
    
    @PostMapping("/save")
    public String saveStopPoint(@Valid @ModelAttribute("routeStopPointDTO") RouteStopPointDTO dto,
                                BindingResult result,RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("pointTypes", StopPointType.values());
        	return "routes-stop-point-form";
        }
        routeStopPointService.saveOrUpdate(dto);
        if(dto.getRouteStopPointId()!=null) {
        	redirectAttributes.addFlashAttribute("success", "Stop point updated successfully!");	
        }else {
        redirectAttributes.addFlashAttribute("success", "Stop point added successfully!");
        }
        return "redirect:/admin/route-stop-point/list";
    }

    @PreAuthorize("hasAuthority('ROUTES_STOP_POINT_EDIT')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,Model model) {
        RouteStopPointDTO dto = routeStopPointService.getPointById(id);
        if (dto == null) {
        	redirectAttributes.addFlashAttribute("success", "Route not found!");
        	return "redirect:/admin/route-stop-point/list";
        }
        model.addAttribute("routeStopPointDTO", dto);
        model.addAttribute("routeStopId", id);
        model.addAttribute("pointTypes", StopPointType.values());
        return "routes-stop-point-form";
    }

    

    @PreAuthorize("hasAuthority('ROUTES_STOP_POINT_DELETE')")
    @GetMapping("/delete/{id}")
    public String deleteStopPoint(@PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes) {
    	try {
        routeStopPointService.deletePoint(id);
        redirectAttributes.addFlashAttribute("success", "Stop point deleted successfully!");
    	}catch(Exception e) {
    		redirectAttributes.addFlashAttribute("error", "Stop point is not deleted!");	
    	}
        return "redirect:/admin/route-stop-point/list/" ;
    }
}
