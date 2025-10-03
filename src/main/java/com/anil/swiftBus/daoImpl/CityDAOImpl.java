package com.anil.swiftBus.daoImpl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.CityDAO;
import com.anil.swiftBus.entity.City;

@Repository
public class CityDAOImpl implements CityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void addCities(List<City> cities) {
        for (City city : cities) {
            entityManager.persist(city);
        }
    }

    @Override
    public List<City> getAllCities() {
    	System.out.println("in the getAllCities() method of CityDAOImpl class...");
        return entityManager.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

	@Override
	public List<String> getAllStates() {
		System.out.println("in the getAllStates() method of CityDAOImpl class...");
		return entityManager.createQuery("SELECT DISTINCT cityState FROM City c", String.class).getResultList();
	}

	@Override
	public List<City> getCitiesByState(String state) {
		return entityManager.createQuery(
	            "SELECT c FROM City c WHERE c.cityState = :state", City.class)
	            .setParameter("state", state)
	            .getResultList();
	}

	@Override
	public City getCityByCityId(Long cityId) {
		return entityManager.createQuery(
	            "SELECT c FROM City c WHERE c.cityId = :cityId", City.class)
	            .setParameter("cityId", cityId)
	            .getSingleResult();
	}
}