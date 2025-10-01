package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Route;

import java.util.List;

public interface RouteDAO {
    Route findById(Long id);
    List<Route> findAll();
    Route save(Route route);
    Route update(Route route);
    void delete(Long id); // soft delete
	boolean existsByRouteNameIgnoreCaseAndDeletedFalse(String routeName);
	boolean existsByRouteNameIgnoreCaseAndDeletedFalseAndRouteIdNot(String routeName, Long excludeId);
}

