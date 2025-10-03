package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.TripDTO;
import com.anil.swiftBus.entity.Bus;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.Trip;
import com.anil.swiftBus.enums.TripStatus;

public class TripMapper {

    public static TripDTO toDTO(Trip entity) {
        if (entity == null) return null;

        TripDTO dto = new TripDTO();
        dto.setTripId(entity.getTripId());
        dto.setBusId(entity.getBus() != null ? entity.getBus().getBusId() : null);
        dto.setRouteId(entity.getRoute() != null ? entity.getRoute().getRouteId() : null);
        dto.setBusName(entity.getBus() != null ? entity.getBus().getBusName() : null);
        dto.setRouteName(entity.getRoute() != null ? entity.getRoute().getRouteName() : null);
        dto.setServiceDate(entity.getServiceDate());
        dto.setDepartureDatetime(entity.getDepartureDatetime());
        dto.setArrivalDatetime(entity.getArrivalDatetime());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Trip toEntity(TripDTO dto) {
        if (dto == null) return null;

        Trip entity = new Trip();
        entity.setTripId(dto.getTripId());

        if (dto.getBusId() != null) {
            Bus bus = new Bus();
            bus.setBusId(dto.getBusId());
            entity.setBus(bus);
        }

        if (dto.getRouteId() != null) {
            Route route = new Route();
            route.setRouteId(dto.getRouteId());
            entity.setRoute(route);
        }

        entity.setServiceDate(dto.getServiceDate());
        entity.setDepartureDatetime(dto.getDepartureDatetime());
        entity.setArrivalDatetime(dto.getArrivalDatetime());
        if (dto.getStatus() != null) {
            entity.setStatus(TripStatus.valueOf(dto.getStatus()));
        } else {
            entity.setStatus(TripStatus.SCHEDULED);
        }

        return entity;
    }
}
