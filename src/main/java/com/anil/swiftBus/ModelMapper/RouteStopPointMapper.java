package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.RouteStopPoint;

public class RouteStopPointMapper {

    public static RouteStopPointDTO toDto(RouteStopPoint entity) {
        RouteStopPointDTO dto = new RouteStopPointDTO();
        dto.setRouteStopPointId(entity.getRouteStopPointId());
        dto.setRouteStopId(entity.getRouteStop().getRouteStopId());
        dto.setPointName(entity.getPointName());
        dto.setPointType(entity.getPointType());
        dto.setLandmark(entity.getLandmark());
        dto.setEnabled(entity.isEnabled());
        return dto;
    }

    public static RouteStopPoint toEntity(RouteStopPointDTO dto, RouteStop parentStop) {
        RouteStopPoint entity = new RouteStopPoint();
        entity.setRouteStopPointId(dto.getRouteStopPointId());
        entity.setRouteStop(parentStop);
        entity.setPointName(dto.getPointName());
        entity.setPointType(dto.getPointType());
        entity.setLandmark(dto.getLandmark());
        entity.setEnabled(dto.isEnabled());
        return entity;
    }
}
