package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.entity.RouteStop;

public class RouteStopMapper {

    public static RouteStopDTO toDTO(RouteStop entity) {
        if (entity == null) {
            return null;
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
        return entity;
    }
}
