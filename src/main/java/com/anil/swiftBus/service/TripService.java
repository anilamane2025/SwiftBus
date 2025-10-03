package com.anil.swiftBus.service;

import java.time.LocalDate;
import java.util.List;

import com.anil.swiftBus.dto.TripDTO;

public interface TripService {

    TripDTO createTrip(TripDTO tripDTO);
    TripDTO updateTrip(TripDTO tripDTO);
    TripDTO getTripById(Long tripId);
    List<TripDTO> getAllTrips();
    List<TripDTO> getTripsByBusId(Long busId);
    List<TripDTO> getTripsByRouteId(Long routeId);
    List<TripDTO> getTripsByServiceDate(LocalDate date);
    void deleteTrip(Long tripId);
	boolean isEditable(Long tripId);
	List<TripDTO> findTripsForJourney(Long fromStopId, Long toStopId, LocalDate date);
}
