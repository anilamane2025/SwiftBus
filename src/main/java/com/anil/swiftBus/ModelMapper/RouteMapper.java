package com.anil.swiftBus.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;

public class RouteMapper {

    public static RouteDTO toDTO(Route entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getStops() != null) {
            entity.getStops().forEach(stop -> {
                if (stop.getStopPoints() != null) {
                    stop.getStopPoints().size(); 
                }
            });
            entity.getStops().size(); 
        }
        RouteDTO dto = new RouteDTO();
        dto.setRouteId(entity.getRouteId());
        dto.setRouteName(entity.getRouteName());
        dto.setDistanceKm(entity.getDistanceKm());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setEnabled(entity.isEnabled());

        if (entity.getStops() != null) {
            List<RouteStopDTO> stopDTOs = entity.getStops().stream()
            		.filter(RouteStop::isEnabled)
                    .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))  
                    .map(RouteStopMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setStops(stopDTOs);
        }
        
        return dto;
    }
    
    public static RouteDTO toDTOWithFare(Route entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getStops() != null) {
            entity.getStops().forEach(stop -> {
                if (stop.getStopPoints() != null) {
                    stop.getStopPoints().size(); 
                }
            });
            entity.getStops().size(); 
        }
        RouteDTO dto = new RouteDTO();
        dto.setRouteId(entity.getRouteId());
        dto.setRouteName(entity.getRouteName());
        dto.setDistanceKm(entity.getDistanceKm());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setEnabled(entity.isEnabled());

        if (entity.getStops() != null) {
        	List<RouteStopDTO> stopDTOs = entity.getStops().stream()
        			.filter(RouteStop::isEnabled)
                    .sorted((a, b) -> Integer.compare(a.getStopOrder(), b.getStopOrder()))  
                    .map(RouteStopMapper::toDTOWithFare)
                    .collect(Collectors.toList());
            dto.setStops(stopDTOs);
        }
        
        return dto;
    }

    public static Route toEntity(RouteDTO dto) {
        if (dto == null) {
            return null;
        }
        Route entity = new Route();
        entity.setRouteId(dto.getRouteId());
        entity.setRouteName(dto.getRouteName());
        entity.setDistanceKm(dto.getDistanceKm());
        entity.setEnabled(true);

        if (dto.getStops() != null) {
            List<RouteStop> stops = dto.getStops().stream()
                    .map(RouteStopMapper::toEntity)
                    .collect(Collectors.toList());

            stops.forEach(stop -> stop.setRoute(entity));
            entity.setStops(stops);
        }
        return entity;
    }
}
