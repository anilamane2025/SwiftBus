package com.anil.swiftBus.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anil.swiftBus.dao.CityDAO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDAO cityDao;

    @Override
    public void addCities(List<City> cities) {
        cityDao.addCities(cities);
    }

    @Override
    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }

	@Override
	public List<String> getAllStates() {
		System.out.println("in the CityServiceImpl class...");
		return cityDao.getAllStates();
	}

	@Override
	public List<City> getCitiesByState(String state) {
		return cityDao.getCitiesByState(state);
	}

	@Override
	public List<City> getAllCitiesByName(String q, int limit) {
		List<City> list = cityDao.getAllCitiesByName( q,  limit);
		  return list;
	}
}