package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.ModelMapper.TripMapper;
import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;
import com.anil.swiftBus.service.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripDAO tripDAO;

    public TripServiceImpl(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Override
    public TripDTO createTrip(TripDTO tripDTO) {
        Trip trip = TripMapper.toEntity(tripDTO);
        tripDAO.save(trip);
        return TripMapper.toDTO(trip);
    }

    @Override
    public TripDTO updateTrip(TripDTO tripDTO) {
        Trip existingTrip = tripDAO.findById(tripDTO.getTripId());
        if (existingTrip == null) {
            throw new IllegalArgumentException("Trip not found with id " + tripDTO.getTripId());
        }
        Trip updatedTrip = TripMapper.toEntity(tripDTO);
        updatedTrip.setTripId(existingTrip.getTripId());
        tripDAO.update(updatedTrip);
        return TripMapper.toDTO(updatedTrip);
    }

    @Override
    public TripDTO getTripById(Long tripId) {
        return TripMapper.toDTO(tripDAO.findById(tripId));
    }

    @Override
    public List<TripDTO> getAllTrips() {
        return tripDAO.findAll().stream()
                      .map(TripMapper::toDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getTripsByBusId(Long busId) {
        return tripDAO.findByBusId(busId).stream()
                      .map(TripMapper::toDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getTripsByRouteId(Long routeId) {
        return tripDAO.findByRouteId(routeId).stream()
                      .map(TripMapper::toDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getTripsByServiceDate(LocalDate date) {
        return tripDAO.findByServiceDate(date).stream()
                      .map(TripMapper::toDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public void deleteTrip(Long tripId) {
        Trip trip = tripDAO.findById(tripId);
        if (trip != null) {
        	trip.setStatus(TripStatus.CANCELLED);
            tripDAO.update(trip);
        }
    }
}
