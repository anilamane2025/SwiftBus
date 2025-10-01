package com.anil.swiftBus.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.RouteService;

@Controller
@RequestMapping("/admin/bus-routes")
public class RoutesController {

	private CityService cityService;

	private RouteService routeService;

	public RoutesController(CityService cityService, RouteService routeService) {
		this.cityService = cityService;
		this.routeService = routeService;
	}

	@GetMapping("/routes-list")
	public String showRoutesList(Model model) {

		model.addAttribute("routes", routeService.getAll());
		return "routes-list";
	}

	@GetMapping("/add")
	@PreAuthorize("hasAuthority('ROUTES_ADD')")
	public String routesAdd(Model model) {
		model.addAttribute("states", cityService.getAllStates());
		model.addAttribute("routeDTO", new RouteDTO());
		model.addAttribute("formfor", "Add");
		return "routes-add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ROUTES_EDIT')")
	public String routesEdit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("states", cityService.getAllStates());
		RouteDTO dto = routeService.getById(id);
		if (dto != null) {
			model.addAttribute("routeDTO", dto);
			model.addAttribute("formfor", "Edit");
			return "routes-add";
		}
		return "routes-list";
	}

	@PostMapping("/save")
	public String saveRoute(@Valid @ModelAttribute("routeDTO") RouteDTO routeDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		model.addAttribute("states", cityService.getAllStates());
		model.addAttribute("formfor", (routeDTO.getRouteId() != null ? "Edit" : "Add"));

		Set<String> seen = new HashSet<>();
		Set<Integer> seenOrders = new HashSet<>();
		for (RouteStopDTO stop : routeDTO.getStops()) {
			if (!seen.add(stop.getStopName())) {
				bindingResult.rejectValue("stops", "duplicate.stopName",
						"Duplicate stop name found: " + stop.getStopName());
				break;
			}
			if (!seenOrders.add(stop.getStopOrder())) {
				bindingResult.rejectValue("stops", "duplicate.stopOrder",
						"Duplicate stop order found: " + stop.getStopOrder());
			}

		}

		if (routeService.existsByRouteName(routeDTO.getRouteName(), routeDTO.getRouteId())) {
			bindingResult.rejectValue("routeName", "duplicate.routeName",
					"Route name already exists. Please choose a different one.");
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult);
			return "routes-add";
		}

		try {
			for (RouteStopDTO stop : routeDTO.getStops()) {
				stop.setRouteId(routeDTO.getRouteId());
			}

			if (routeDTO.getRouteId() == null) {
				routeService.create(routeDTO);
			} else {
				routeService.update(routeDTO);
			}

			redirectAttributes.addFlashAttribute("success",
					(routeDTO.getRouteId() == null ? "Route saved successfully!" : "Route updated successfully!"));

		} catch (Exception e) {
			model.addAttribute("error", "Error saving route: " + e.getMessage());
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/admin/bus-routes/routes-list";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ROUTES_DELETE')")
	public String deleteBus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			routeService.delete(id);
			redirectAttributes.addFlashAttribute("success", "Route deleted successfully.");
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/bus-routes/routes-list";
	}

}
