package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;

public class FareSegmentMapper {

    public static FareSegmentDTO toDto(FareSegment entity) {
        if (entity == null) return null;

        FareSegmentDTO dto = new FareSegmentDTO();
        dto.setFareSegmentId(entity.getFareSegmentId());
        dto.setRouteId(entity.getRoute() != null ? entity.getRoute().getRouteId() : null);
        dto.setFromRouteStopId(entity.getFromRouteStop() != null ? entity.getFromRouteStop().getRouteStopId() : null);
        dto.setToRouteStopId(entity.getToRouteStop() != null ? entity.getToRouteStop().getRouteStopId() : null);
        dto.setFareAmount(entity.getFareAmount());
        dto.setEnabled(entity.isEnabled());
        dto.setToStopName(entity.getToRouteStop() != null ? entity.getToRouteStop().getStopName() : null);
        dto.setToStopOrder(entity.getToRouteStop() != null ? entity.getToRouteStop().getStopOrder() : null);
        return dto;
    }

    public static FareSegment toEntity(FareSegmentDTO dto, Route route, RouteStop fromStop, RouteStop toStop) {
        if (dto == null) return null;

        FareSegment entity = new FareSegment();
        entity.setFareSegmentId(dto.getFareSegmentId());
        entity.setRoute(route);
        entity.setFromRouteStop(fromStop);
        entity.setToRouteStop(toStop);
        entity.setFareAmount(dto.getFareAmount());
        entity.setEnabled(dto.isEnabled());
        return entity;
    }
    
}
