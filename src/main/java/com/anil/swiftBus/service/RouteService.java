package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.RouteDTO;

public interface RouteService {
    RouteDTO getById(Long id);
    List<RouteDTO> getAll();
    RouteDTO create(RouteDTO routeDTO);
    RouteDTO update(RouteDTO routeDTO);
    void delete(Long id); // soft delete
    boolean existsByRouteName(String routeName, Long excludeId);
}

