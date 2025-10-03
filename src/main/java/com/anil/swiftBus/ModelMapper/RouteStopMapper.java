package com.anil.swiftBus.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.RouteStopPoint;

public class RouteStopMapper {

    public static RouteStopDTO toDTO(RouteStop entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getStopPoints() != null) {
            entity.getStopPoints().size();
        }
        RouteStopDTO dto = new RouteStopDTO();
        dto.setRouteStopId(entity.getRouteStopId());
        dto.setRouteId(entity.getRoute() != null ? entity.getRoute().getRouteId() : null);
        dto.setCityId(entity.getCity() != null ? entity.getCity().getCityId() : null);
        if (entity.getCity() != null) {
        	dto.setCityName(entity.getCity().getCityName());
            dto.setCityState(entity.getCity().getCityState());
        }
        dto.setStopName(entity.getStopName());
        dto.setStopOrder(entity.getStopOrder());
        dto.setDistanceFromOriginKm(entity.getDistanceFromOriginKm());
        dto.setMinutesFromStart(entity.getMinutesFromStart());
        dto.setEnabled(entity.isEnabled());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        if (entity.getStopPoints() != null) {
            List<RouteStopPointDTO> pointDTOs = entity.getStopPoints()
                    .stream()
                    .filter(RouteStopPoint::isEnabled)
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            dto.setStopPoints(pointDTOs);
        } 
        return dto;
    }
    
    public static RouteStopDTO toDTOWithFare(RouteStop entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getStopPoints() != null) {
            entity.getStopPoints().size();
        }
        RouteStopDTO dto = new RouteStopDTO();
        dto.setRouteStopId(entity.getRouteStopId());
        dto.setRouteId(entity.getRoute() != null ? entity.getRoute().getRouteId() : null);
        dto.setCityId(entity.getCity() != null ? entity.getCity().getCityId() : null);
        if (entity.getCity() != null) {
        	dto.setCityName(entity.getCity().getCityName());
            dto.setCityState(entity.getCity().getCityState());
        }
        dto.setStopName(entity.getStopName());
        dto.setStopOrder(entity.getStopOrder());
        dto.setDistanceFromOriginKm(entity.getDistanceFromOriginKm());
        dto.setMinutesFromStart(entity.getMinutesFromStart());
        dto.setEnabled(entity.isEnabled());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        if (entity.getFaresFrom() != null) {
            dto.setFares(
                entity.getFaresFrom().stream()
                	  .filter(FareSegment::isEnabled)
                      .map(FareSegmentMapper::toDto)
                      .collect(Collectors.toList())
            );
        }

        if (entity.getStopPoints() != null) {
            List<RouteStopPointDTO> pointDTOs = entity.getStopPoints()
                    .stream()
                    .filter(RouteStopPoint::isEnabled)
                    .map(RouteStopPointMapper::toDto)
                    .collect(Collectors.toList());
            dto.setStopPoints(pointDTOs);
        } 
        return dto;
    }

    public static RouteStop toEntity(RouteStopDTO dto) {
        if (dto == null) {
            return null;
        }
        RouteStop entity = new RouteStop();
        entity.setRouteStopId(dto.getRouteStopId());

        if (dto.getCityId() != null) {
            City city = new City();
            city.setCityId(dto.getCityId());
            entity.setCity(city);
        }
        entity.setStopName(dto.getStopName());
        entity.setStopOrder(dto.getStopOrder());
        entity.setDistanceFromOriginKm(dto.getDistanceFromOriginKm());
        entity.setMinutesFromStart(dto.getMinutesFromStart());
        entity.setEnabled(dto.isEnabled());
        if (dto.getStopPoints() != null) {
        	 List<RouteStopPoint> points = dto.getStopPoints().stream()
        		        .map(p -> RouteStopPointMapper.toEntity(p, entity)) 
        		        .collect(Collectors.toList());

        		    points.forEach(p -> p.setRouteStop(entity));
        		    entity.setStopPoints(points);
        }
        return entity;
    }
}
