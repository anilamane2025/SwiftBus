package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.entity.City;

public interface CityService {
    void addCities(List<City> cities);
    List<City> getAllCities();
	List<String> getAllStates();
	List<City> getCitiesByState(String state);
	List<City> getAllCitiesByName(String q, int i);
}