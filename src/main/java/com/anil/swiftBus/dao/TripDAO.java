package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Trip;
import java.time.LocalDate;
import java.util.List;

public interface TripDAO {
    Trip save(Trip trip);
    Trip update(Trip trip);
    Trip findById(Long tripId);
    List<Trip> findAll();
    List<Trip> findByBusId(Long busId);
    List<Trip> findByRouteId(Long routeId);
    List<Trip> findByServiceDate(LocalDate date);
 
}
