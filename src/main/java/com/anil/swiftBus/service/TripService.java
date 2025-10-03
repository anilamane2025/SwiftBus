package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.TripDTO;
import java.time.LocalDate;
import java.util.List;

public interface TripService {

    TripDTO createTrip(TripDTO tripDTO);
    TripDTO updateTrip(TripDTO tripDTO);
    TripDTO getTripById(Long tripId);
    List<TripDTO> getAllTrips();
    List<TripDTO> getTripsByBusId(Long busId);
    List<TripDTO> getTripsByRouteId(Long routeId);
    List<TripDTO> getTripsByServiceDate(LocalDate date);
    void deleteTrip(Long tripId);
}
