package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.Route;

public interface RouteDAO {
    Route findById(Long id);
    List<Route> findAll();
    Route save(Route route);
    Route update(Route route);
    void delete(Long id); // soft delete
	boolean existsByRouteNameIgnoreCaseAndDeletedFalse(String routeName);
	boolean existsByRouteNameIgnoreCaseAndDeletedFalseAndRouteIdNot(String routeName, Long excludeId);
}

