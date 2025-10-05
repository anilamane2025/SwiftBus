package com.anil.swiftBus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.RouteStopService;

@Controller
public class HomeController {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RouteStopService routeStopService;

	@GetMapping("/")
    public String Home() {
        return "index"; // index.jsp
    }
	
	@GetMapping("/api/stops/search")
    @ResponseBody
    public List<Map<String,Object>> searchStops(@RequestParam("q") String q) {
        // routeStopService.searchEnabledStopsByNameOrCity(q) -> List<RouteStop>
        List<RouteStopDTO> results = routeStopService.searchEnabledStopsByNameOrCity(q, 10);
        List<City> allCities = cityService.getAllCitiesByName(q,10);
        Set<String> seenDisplayNames = new HashSet<>();
        List<Map<String,Object>> response = new ArrayList<>();

        for (RouteStopDTO s : results) {
            // Always add city
            String cityName = s.getCityName();
            if (!seenDisplayNames.contains(cityName.toLowerCase())) {
                seenDisplayNames.add(cityName.toLowerCase());
                Map<String,Object> m = new HashMap<>();
                m.put("displayName", cityName);
                response.add(m);
            }

            // Add stop name if different from city
            if (!s.getStopName().equalsIgnoreCase(s.getCityName())) {
                String stopName = s.getStopName();
                if (!seenDisplayNames.contains(stopName.toLowerCase())) {
                    seenDisplayNames.add(stopName.toLowerCase());
                    Map<String,Object> m = new HashMap<>();
                    m.put("displayName", stopName);
                    response.add(m);
                }
            }
        }
        
        for (City city : allCities) {
            String cityName = city.getCityName();
            if (!seenDisplayNames.contains(cityName.toLowerCase())) {
                seenDisplayNames.add(cityName.toLowerCase());
                Map<String,Object> m = new HashMap<>();
                m.put("displayName", cityName);
                response.add(m);
            }
        }

        return response;
    }
}
