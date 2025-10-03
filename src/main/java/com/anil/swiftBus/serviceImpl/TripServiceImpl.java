package com.anil.swiftBus.serviceImpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.ModelMapper.TripMapper;
import com.anil.swiftBus.dao.BusDAO;
import com.anil.swiftBus.dao.RouteDAO;
import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;
import com.anil.swiftBus.service.TripService;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripDAO tripDAO;
    private final RouteDAO routeDAO;
    private final BusDAO busDAO;

    public TripServiceImpl(TripDAO tripDAO,RouteDAO routeDAO,BusDAO busDAO) {
        this.tripDAO = tripDAO;
        this.routeDAO = routeDAO;
        this.busDAO = busDAO;
    }

    @Override
    public TripDTO createTrip(TripDTO tripDTO) {

        Route route = routeDAO.findById(tripDTO.getRouteId());
            if(route==null) {
            	throw new IllegalArgumentException("Route not found!");
            }
            
            RouteStop lastStop = route.getStops().stream()
                    .filter(RouteStop::isEnabled) // if you soft delete disabled stops
                    .max(Comparator.comparingInt(RouteStop::getStopOrder))
                    .orElseThrow(() -> new IllegalArgumentException("No stops found for route"));

            int totalMinutes = lastStop.getMinutesFromStart();

        tripDTO.setArrivalDatetime(tripDTO.getDepartureDatetime().plusMinutes(totalMinutes));

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
        Route route = routeDAO.findById(tripDTO.getRouteId());
        if(route==null) {
        	throw new IllegalArgumentException("Route not found!");
        }
        
        RouteStop lastStop = route.getStops().stream()
                .filter(RouteStop::isEnabled) // if you soft delete disabled stops
                .max(Comparator.comparingInt(RouteStop::getStopOrder))
                .orElseThrow(() -> new IllegalArgumentException("No stops found for route"));

        int totalMinutes = lastStop.getMinutesFromStart();

    tripDTO.setArrivalDatetime(tripDTO.getDepartureDatetime().plusMinutes(totalMinutes));

    Bus bus= busDAO.findById(tripDTO.getBusId()).orElseThrow(() -> new IllegalArgumentException("No Bus found with id "+tripDTO.getBusId()));
    
        if (tripDTO.getTripId() != null && !isEditable(tripDTO.getTripId())) {
            throw new RuntimeException("Trip already booked. Cannot modify trip details.");
        }
        existingTrip.setRoute(route);
        existingTrip.setBus(bus);
        existingTrip.setServiceDate(tripDTO.getServiceDate());
        existingTrip.setDepartureDatetime(tripDTO.getDepartureDatetime());
        existingTrip.setArrivalDatetime(tripDTO.getDepartureDatetime().plusMinutes(totalMinutes));
        
        existingTrip.setTripId(existingTrip.getTripId());
        tripDAO.update(existingTrip);
        return TripMapper.toDTO(existingTrip);
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

	@Override
	public boolean isEditable(Long tripId) {
		 //long count = bookingDao.countByTripId(tripId);
	       // return count == 0;
		return true;
	}
}
