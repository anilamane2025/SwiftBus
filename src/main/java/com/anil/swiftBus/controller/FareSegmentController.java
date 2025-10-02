package com.anil.swiftBus.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.service.FareSegmentService;

@Controller
@RequestMapping("/admin/fare-segment")
public class FareSegmentController {

    private final FareSegmentService fareSegmentService;
   
    public FareSegmentController(FareSegmentService fareSegmentService) {
        this.fareSegmentService = fareSegmentService;
    }

    @GetMapping("/list")
    public String listFares(Model model) {
        List<RouteDTO> routes = fareSegmentService.getAllRoutesWithStopsAndFare();
        model.addAttribute("routes", routes);
        return "fare-segment-list"; // JSP page
    }

    @PostMapping("/saveAll")
    @PreAuthorize("hasAuthority('MANAGE_FARE_SEGMENT')")
    public String saveFares(
            @RequestParam Long routeId,
            @RequestParam("fareSegmentIds") List<Long> fareSegmentIds,
            @RequestParam("fareAmounts") List<Double> fareAmounts,
            RedirectAttributes redirectAttributes) {

        if(fareSegmentIds.size() != fareAmounts.size()) {
            redirectAttributes.addFlashAttribute("error", "Mismatch in fare IDs and amounts.");
            return "redirect:/admin/fare-segment/list";
        }

        List<FareSegmentDTO> updatedFares = new ArrayList<>();
        for (int i = 0; i < fareSegmentIds.size(); i++) {
            FareSegmentDTO dto = new FareSegmentDTO();
            dto.setFareSegmentId(fareSegmentIds.get(i));
            dto.setFareAmount(BigDecimal.valueOf(fareAmounts.get(i)));
            fareSegmentService.updateFare(dto);
            updatedFares.add(dto);
        }

        redirectAttributes.addFlashAttribute("success", "Fares updated successfully!");
        return "redirect:/admin/fare-segment/list";
    }

    @GetMapping("/generate/{routeId}")
    @PreAuthorize("hasAuthority('MANAGE_FARE_SEGMENT')")
    public String generateMissingFares(@PathVariable Long routeId, RedirectAttributes redirectAttributes) {
        try {
            fareSegmentService.generateMissingFaresForRoute(routeId);
            redirectAttributes.addFlashAttribute("success", "Missing fares generated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error generating fares: " + e.getMessage());
        }
        return "redirect:/admin/fare-segment/list";
    }
}
