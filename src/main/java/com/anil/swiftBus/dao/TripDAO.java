package com.anil.swiftBus.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;

public interface TripDAO {
    Trip save(Trip trip);
    Trip update(Trip trip);
    Trip findById(Long tripId);
    List<Trip> findAll();
    List<Trip> findByBusId(Long busId);
    List<Trip> findByRouteId(Long routeId);
    List<Trip> findByServiceDate(LocalDate date);
	List<Trip> findByStatusAndArrivalDatetimeBefore(TripStatus scheduled, LocalDateTime now);
 
}
