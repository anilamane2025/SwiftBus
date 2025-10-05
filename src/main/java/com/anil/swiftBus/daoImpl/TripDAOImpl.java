package com.anil.swiftBus.daoImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;

@Repository
@Transactional
public class TripDAOImpl implements TripDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Trip save(Trip trip) {
        em.persist(trip);
        return trip;
    }

    @Override
    public Trip update(Trip trip) {
        return em.merge(trip);
    }

    @Override
    public Trip findById(Long tripId) {
        return em.find(Trip.class, tripId);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
    }

    @Override
    public List<Trip> findByBusId(Long busId) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.bus.busId = :busId", Trip.class)
                 .setParameter("busId", busId)
                 .getResultList();
    }

    @Override
    public List<Trip> findByRouteId(Long routeId) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.route.routeId = :routeId", Trip.class)
                 .setParameter("routeId", routeId)
                 .getResultList();
    }

    @Override
    public List<Trip> findByServiceDate(LocalDate date) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.serviceDate = :date", Trip.class)
                 .setParameter("date", date)
                 .getResultList();
    }

	@Override
	public List<Trip> findByStatusAndArrivalDatetimeBefore(TripStatus scheduled, LocalDateTime now) {
		return em.createQuery(
	            "SELECT t FROM Trip t " +
	            "WHERE t.status = :status " +
	            "AND t.arrivalDatetime < :now", Trip.class)
	        .setParameter("status", scheduled)
	        .setParameter("now", now)
	        .getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Trip> findTripsForJourney(String fromStopName, String toStopName, LocalDate date) {
		List<RouteStop> fromStops = em.createQuery(
                "SELECT rs FROM RouteStop rs WHERE rs.enabled = true AND LOWER(rs.stopName) = :name", RouteStop.class)
                .setParameter("name", fromStopName.toLowerCase())
                .getResultList();

        List<RouteStop> toStops = em.createQuery(
                "SELECT rs FROM RouteStop rs WHERE rs.enabled = true AND LOWER(rs.stopName) = :name", RouteStop.class)
                .setParameter("name", toStopName.toLowerCase())
                .getResultList();

        if (fromStops.isEmpty() || toStops.isEmpty()) return Collections.emptyList();

        Set<Long> allStopIds = Stream.concat(
                fromStops.stream().map(RouteStop::getRouteStopId),
                toStops.stream().map(RouteStop::getRouteStopId))
            .collect(Collectors.toSet());

        // 2️⃣ Query all candidate trips
        List<Trip> trips = em.createQuery(
                "SELECT DISTINCT t FROM Trip t " +
                "JOIN FETCH t.route r " +
                "JOIN FETCH r.stops rs " +
                "WHERE t.serviceDate = :date " +
                "AND t.status = :status " +
                "AND r.enabled = true " +
                "AND rs.enabled = true " +
                "AND rs.routeStopId IN :stopIds", Trip.class)
                .setParameter("date", date)
                .setParameter("status", TripStatus.SCHEDULED)
                .setParameter("stopIds", allStopIds)
                .getResultList();

        return trips;
        
        

	}

	@Override
	public Trip findByIdRouteIdBusId(Long tripId, Long busId) {
		return em.createQuery("SELECT t FROM Trip t WHERE t.bus.busId = :busId AND t.tripId = :tripId", Trip.class)
                .setParameter("busId", busId)
                .setParameter("tripId", tripId)
                .getSingleResult();
	}

}
