package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.RouteStopDTO;

public interface RouteStopService {
    RouteStopDTO getById(Long id);
    List<RouteStopDTO> getByRouteId(Long routeId);
    RouteStopDTO create(RouteStopDTO stopDTO);
    RouteStopDTO update(RouteStopDTO stopDTO);
    void delete(Long id);
	List<RouteStopDTO> searchEnabledStopsByNameOrCity(String q, int limit);
}
