package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.ModelMapper.RouteMapper;
import com.anil.swiftBus.ModelMapper.RouteStopMapper;
import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.dao.RouteDAO;
import com.anil.swiftBus.service.RouteService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final RouteDAO routeDAO;
    
    @PersistenceContext
    private EntityManager em;


    public RouteServiceImpl(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    @Override
    public RouteDTO getById(Long id) {
        Route route = routeDAO.findById(id);
        return RouteMapper.toDTO(route);
    }

    @Override
    public List<RouteDTO> getAll() {
        return routeDAO.findAll().stream()
                .map(RouteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteDTO create(RouteDTO routeDTO) {
        Route route = RouteMapper.toEntity(routeDTO);
        routeDAO.save(route);
        return RouteMapper.toDTO(route);
    }

    @Override
    @Transactional
    public RouteDTO update(RouteDTO routeDTO) {
        Route existingRoute = routeDAO.findById(routeDTO.getRouteId());

        if (existingRoute == null) {
            throw new IllegalArgumentException("Route not found with id " + routeDTO.getRouteId());
        }

        List<RouteStop> existingStops = existingRoute.getStops();

        // Map existing stops by ID for quick lookup
        Map<Long, RouteStop> existingStopsById = existingStops.stream()
            .collect(Collectors.toMap(RouteStop::getRouteStopId, Function.identity()));

        // Validate and normalize incoming stops
        List<RouteStopDTO> incomingStops = routeDTO.getStops();
        validateStopOrderUniqueness(incomingStops);         // Check for duplicate stopOrder
        normalizeStopOrders(incomingStops);                 // reassign stop orders to be sequential (1,2,3...)

        // Build a set of incoming stop IDs (null = new stop)
        Set<Long> incomingStopIds = incomingStops.stream()
            .map(RouteStopDTO::getRouteStopId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        // Soft-delete stops that are no longer in the updated list
        for (RouteStop existingStop : existingStops) {
            if (!incomingStopIds.contains(existingStop.getRouteStopId())) {
                existingStop.setEnabled(false);
            }
        }

        // Update or add stops
        for (RouteStopDTO stopDTO : incomingStops) {
            if (stopDTO.getRouteStopId() != null && existingStopsById.containsKey(stopDTO.getRouteStopId())) {
                // Update existing stop
                RouteStop existingStop = existingStopsById.get(stopDTO.getRouteStopId());
                updateStopFromDTO(existingStop, stopDTO);
                existingStop.setEnabled(true); // Re-enable if previously soft-deleted
            } else {
                // Add new stop
                RouteStop newStop = RouteStopMapper.toEntity(stopDTO);
                newStop.setRoute(existingRoute);
                newStop.setEnabled(true);
                existingStops.add(newStop);
            }
        }

        existingRoute.setRouteName(routeDTO.getRouteName());
        existingRoute.setDistanceKm(routeDTO.getDistanceKm());

        Route updatedRoute = routeDAO.update(existingRoute);
        return RouteMapper.toDTO(updatedRoute);
    }

    private void validateStopOrderUniqueness(List<RouteStopDTO> stops) {
        Set<Integer> stopOrders = new HashSet<>();
        for (RouteStopDTO stop : stops) {
            if (!stopOrders.add(stop.getStopOrder())) {
                throw new IllegalArgumentException("Duplicate stop_order detected: " + stop.getStopOrder());
            }
        }
    }

    private void normalizeStopOrders(List<RouteStopDTO> stops) {
        stops.sort(Comparator.comparingInt(RouteStopDTO::getStopOrder));
        int order = 1;
        for (RouteStopDTO stop : stops) {
            stop.setStopOrder(order++);
        }
    }



    private void updateStopFromDTO(RouteStop entity, RouteStopDTO dto) {
        entity.setStopName(dto.getStopName());
        entity.setStopOrder(dto.getStopOrder());
        entity.setDistanceFromOriginKm(dto.getDistanceFromOriginKm());
        entity.setMinutesFromStart(dto.getMinutesFromStart());

        if (dto.getCityId() != null) {
            City city = new City();
            city.setCityId(dto.getCityId());
            entity.setCity(city);
        }
    }

    @Override
    public void delete(Long id) {
        routeDAO.delete(id);
    }

	@Override
	public boolean existsByRouteName(String routeName, Long excludeId) {
		if (excludeId == null) {
            return routeDAO.existsByRouteNameIgnoreCaseAndDeletedFalse(routeName);
        }
        return routeDAO.existsByRouteNameIgnoreCaseAndDeletedFalseAndRouteIdNot(routeName, excludeId);
	}
}
