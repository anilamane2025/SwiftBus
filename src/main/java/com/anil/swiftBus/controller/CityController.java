package com.anil.swiftBus.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    // add a list of cities
    @PostMapping("/add")
    public ResponseEntity<String> addCities(@RequestBody List<City> cities) {
        
        cityService.addCities(cities);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cities added successfully.");
    }

    // get all cities
    @GetMapping("/list")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }
    
    @GetMapping("/cities-by-state")
    @ResponseBody
    public ResponseEntity<List<City>> getCitiesByState(@RequestParam String state) {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCitiesByState(state));
    }
}