package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.City;

public interface CityDAO {
    void addCities(List<City> cities);
    List<City> getAllCities();
	List<String> getAllStates();
	List<City> getCitiesByState(String state);
	City getCityByCityId(Long cityId);
}