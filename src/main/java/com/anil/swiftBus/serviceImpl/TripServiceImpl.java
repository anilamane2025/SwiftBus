package com.anil.swiftBus.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.ModelMapper.BusMapper;
import com.anil.swiftBus.ModelMapper.RouteMapper;
import com.anil.swiftBus.ModelMapper.RouteStopMapper;
import com.anil.swiftBus.ModelMapper.RouteStopPointMapper;
import com.anil.swiftBus.ModelMapper.TripMapper;
import com.anil.swiftBus.dao.BookingDAO;
import com.anil.swiftBus.dao.BusDAO;
import com.anil.swiftBus.dao.FareSegmentDAO;
import com.anil.swiftBus.dao.RouteDAO;
import com.anil.swiftBus.dao.RouteStopDAO;
import com.anil.swiftBus.dao.RouteStopPointDAO;
import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.dto.TripSearchDTO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.RouteStopPoint;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.StopPointType;
import com.anil.swiftBus.enums.TripStatus;
import com.anil.swiftBus.service.TripService;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripDAO tripDAO;
    private final RouteDAO routeDAO;
    private final BusDAO busDAO;
    private final RouteStopDAO routeStopDAO;
    private final FareSegmentDAO fareSegmentDao;
    private final BookingDAO bookingDAO;

    public TripServiceImpl(TripDAO tripDAO,RouteDAO routeDAO,BusDAO busDAO,RouteStopDAO routeStopDAO, FareSegmentDAO fareSegmentDao,BookingDAO bookingDAO) {
        this.tripDAO = tripDAO;
        this.routeDAO = routeDAO;
        this.busDAO = busDAO;
        this.routeStopDAO = routeStopDAO;
        this.fareSegmentDao = fareSegmentDao;
        this.bookingDAO = bookingDAO;
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
		 long count = bookingDAO.countBookedSeatsForTrip(tripId);
		 System.out.println("count --- bookedseat"+count);
		 return count == 0;
	}

	

	@Override
	public List<TripSearchDTO> findTripsForJourney(String fromStopName, String toStopName, LocalDate date) {
		List<Trip> trips = tripDAO.findTripsForJourney(fromStopName, toStopName, date);
		
        List<TripSearchDTO> results = new ArrayList<>();
        
        for (Trip t : trips) {
            Route route = t.getRoute();
            List<RouteStop> stops = route.getStops().stream()
                    .filter(RouteStop::isEnabled)
                    .distinct()
                    .sorted(Comparator.comparingInt(RouteStop::getStopOrder))
                    .collect(Collectors.toList());

            
            // find from/to stop
            RouteStop fromStop = stops.stream()
            		.filter(RouteStop::isEnabled)
            		.distinct()
                    .filter(s -> s.getStopName().equalsIgnoreCase(fromStopName))
                    .findFirst().orElse(null);
            RouteStop toStop = stops.stream()
            		.filter(RouteStop::isEnabled)
            		.distinct()
                    .filter(s -> s.getStopName().equalsIgnoreCase(toStopName))
                    .findFirst().orElse(null);

            if (fromStop == null || toStop == null || fromStop.getStopOrder() >= toStop.getStopOrder())
                continue;

            
            // Convert to TripSearchDTO
            TripSearchDTO dto = new TripSearchDTO();
            dto.setId(t.getTripId());
            dto.setRouteName(route.getRouteName());
            dto.setFromRouteStopId(fromStop.getRouteStopId());
            dto.setToRouteStopId(toStop.getRouteStopId());
            dto.setFromName(fromStop.getStopName());
            dto.setToName(toStop.getStopName());
            
            //dto.setRouteStops(stops.stream().filter(RouteStop::isEnabled).distinct().map(RouteStopMapper::toDTO).collect(Collectors.toList()));
            dto.setRouteStops(routeStopDAO.findByRouteId(route.getRouteId()).stream().filter(RouteStop::isEnabled).sorted(Comparator.comparingInt(RouteStop::getStopOrder)).map(RouteStopMapper::toDTO).collect(Collectors.toList()));
            // bus details
            BusDTO bus = busDAO.findById(t.getBus().getBusId()).map(BusMapper::toDTO).orElse(null);
            if (bus != null) {
                dto.setBusName(bus.getBusName());
                dto.setBusType(bus.getBusType());
                dto.setBusId(bus.getBusId());
                int booked = bookingDAO.countBookedSeatsForTripWithRouteStop(t.getTripId(),fromStop.getRouteStopId(),toStop.getRouteStopId());
                dto.setAvailableSeats(Math.max(0, bus.getTotalSeats() - booked));
            }

            // time formatting
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
            LocalDateTime routeDep = t.getDepartureDatetime();
            LocalDateTime routeArr = t.getArrivalDatetime();
            LocalDateTime depTime=null , arrTime=null;
            if (routeDep != null) {
                 depTime = routeDep;
                if (fromStop.getMinutesFromStart() != null) {
                    depTime = depTime.plusMinutes(fromStop.getMinutesFromStart());
                }
                dto.setDepartureTime(depTime.format(fmt));
            }

            if (routeArr != null) {
                 arrTime = routeArr;
                if (toStop.getMinutesFromStart() != null) {
                    arrTime = routeDep.plusMinutes(toStop.getMinutesFromStart());
                }
                dto.setArrivalTime(arrTime.format(fmt));
            }

           
            if (t.getDepartureDatetime() != null && t.getArrivalDatetime() != null &&
                    fromStop.getMinutesFromStart() != null && toStop.getMinutesFromStart() != null) {

            	Duration d = Duration.between(depTime, arrTime);
                dto.setDuration(String.format("%dh %02dm", d.toHours(), d.minusHours(d.toHours()).toMinutes()));

            } else if (t.getDepartureDatetime() != null && t.getArrivalDatetime() != null) {
            	Duration d = Duration.between(depTime, arrTime);
                dto.setDuration(String.format("%dh %02dm", d.toHours(), d.minusHours(d.toHours()).toMinutes()));
            }

            // price from fare
            FareSegment fare = fromStop.getFaresFrom().stream()
            		.filter(FareSegment::isEnabled)
            		.distinct()
            	    .filter(f -> f.getFromRouteStop().equals(fromStop) && f.getToRouteStop().equals(toStop))
            	    .findFirst()
            	    .orElse(null);

            	if (fare != null) {
            	    dto.setPrice(fare.getFareAmount().doubleValue());
            	    dto.setFareSegmentId(fare.getFareSegmentId());
            	}


            // pickup/drop points
            List<RouteStopPointDTO> pickups = new ArrayList<>();
            List<RouteStopPointDTO> drops = new ArrayList<>();
//            for (RouteStop s : stops) {
//            	
//                if (s.getStopOrder() < fromStop.getStopOrder() || s.getStopOrder() > toStop.getStopOrder()) continue;
//
//                List<RouteStopPointDTO> points = s.getStopPoints().stream()
//                        .filter(RouteStopPoint::isEnabled)
//                        .distinct()
//                        .map(RouteStopPointMapper::toDto)
//                        .collect(Collectors.toList());
//                
//                System.out.println(s+"--------"+t.getTripId());
//                System.out.println(points.toString());
//
//                pickups.addAll(points.stream().filter(p -> p.getPointType().equals("PICKUP")).collect(Collectors.toList()));
//                drops.addAll(points.stream().filter(p -> p.getPointType().equals("DROP")).collect(Collectors.toList()));
//            }
            List<RouteStopPointDTO> pickupPoints = fromStop.getStopPoints().stream()
                    .filter(RouteStopPoint::isEnabled)
                    .distinct()
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            
            List<RouteStopPointDTO> dropPoint = toStop.getStopPoints().stream()
                    .filter(RouteStopPoint::isEnabled)
                    .distinct()
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            

            pickups.addAll(pickupPoints.stream().filter(p -> p.getPointType().equals("PICKUP")).collect(Collectors.toList()));
            drops.addAll(dropPoint.stream().filter(p -> p.getPointType().equals("DROP")).collect(Collectors.toList()));

            dto.setPickupPoints(pickups);
            dto.setDropPoints(drops);

            results.add(dto);
            
        }


        results.sort(Comparator.comparing(TripSearchDTO::getDepartureTime));
		return results;
	}

	@Override
	public TripSearchDTO getTripByIdFareIdBusId(Long tripId, Long fareSegmentId, Long busId) {
		TripSearchDTO dto = new TripSearchDTO();
		
		// bus details
        BusDTO bus = busDAO.findById(busId).map(BusMapper::toDTO).orElseThrow(() -> new IllegalArgumentException("No Bus found!"));
        int totalSeats = 0;
        if (bus != null) {
            dto.setBusName(bus.getBusName());
            dto.setBusType(bus.getBusType());
            dto.setBusId(bus.getBusId());
            
        }
        
		FareSegment fareSegment = fareSegmentDao.findById(fareSegmentId);
		if(fareSegment == null) {
			throw new IllegalArgumentException("No fareSegment found!");
		}
		Trip trip = tripDAO.findByIdRouteIdBusId(tripId,busId);
		if(trip == null) {
			throw new IllegalArgumentException("No Trip found!");
		}
		
        dto.setId(trip.getTripId());
        dto.setRouteName(fareSegment.getRoute().getRouteName());
        dto.setRouteStops(fareSegment.getRoute().getStops().stream().filter(RouteStop::isEnabled).sorted(Comparator.comparingInt(RouteStop::getStopOrder)).map(RouteStopMapper::toDTO).collect(Collectors.toList()));
        

        // time formatting
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime routeDep = trip.getDepartureDatetime();
        LocalDateTime routeArr = trip.getArrivalDatetime();
        LocalDateTime depTime=null , arrTime=null;
        RouteStop fromStop = fareSegment.getFromRouteStop();
        RouteStop toStop = fareSegment.getToRouteStop();
        dto.setFromName(fromStop.getStopName());
        dto.setToName(toStop.getStopName());
        dto.setFromRouteStopId(fromStop.getRouteStopId());
        dto.setToRouteStopId(toStop.getRouteStopId());
        
        int booked = bookingDAO.countBookedSeatsForTripWithRouteStop(trip.getTripId(),fromStop.getRouteStopId(),toStop.getRouteStopId());
        dto.setAvailableSeats(Math.max(0, bus.getTotalSeats() - booked));
        
        if (routeDep != null) {
             depTime = routeDep;
            if (fromStop.getMinutesFromStart() != null) {
                depTime = depTime.plusMinutes(fromStop.getMinutesFromStart());
            }
            dto.setDepartureTime(depTime.format(fmt));
        }

        if (routeArr != null) {
             arrTime = routeArr;
            if (toStop.getMinutesFromStart() != null) {
                arrTime = routeDep.plusMinutes(toStop.getMinutesFromStart());
            }
            dto.setArrivalTime(arrTime.format(fmt));
        }

       
        if (trip.getDepartureDatetime() != null && trip.getArrivalDatetime() != null &&
                fromStop.getMinutesFromStart() != null && toStop.getMinutesFromStart() != null) {

        	Duration d = Duration.between(depTime, arrTime);
            dto.setDuration(String.format("%dh %02dm", d.toHours(), d.minusHours(d.toHours()).toMinutes()));

        } else if (trip.getDepartureDatetime() != null && trip.getArrivalDatetime() != null) {
        	Duration d = Duration.between(depTime, arrTime);
            dto.setDuration(String.format("%dh %02dm", d.toHours(), d.minusHours(d.toHours()).toMinutes()));
        }

        	dto.setPrice(fareSegment.getFareAmount().doubleValue());
    	    dto.setFareSegmentId(fareSegment.getFareSegmentId());

        // pickup/drop points
        List<RouteStopPointDTO> pickups = new ArrayList<>();
        List<RouteStopPointDTO> drops = new ArrayList<>();
       
            List<RouteStopPointDTO> pickupPoints = fromStop.getStopPoints().stream()
                    .filter(RouteStopPoint::isEnabled)
                    .distinct()
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            
            List<RouteStopPointDTO> dropPoint = toStop.getStopPoints().stream()
                    .filter(RouteStopPoint::isEnabled)
                    .distinct()
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            

            pickups.addAll(pickupPoints.stream().filter(p -> p.getPointType().equals("PICKUP")).collect(Collectors.toList()));
            drops.addAll(dropPoint.stream().filter(p -> p.getPointType().equals("DROP")).collect(Collectors.toList()));
        

        dto.setPickupPoints(pickups);
        dto.setDropPoints(drops);
        return dto;
	}
	
	
}
