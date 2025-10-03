package com.anil.swiftBus.dao;
import java.util.List;

import com.anil.swiftBus.entity.RouteStop;
public interface RouteStopDAO {
    RouteStop findById(Long id);
    List<RouteStop> findByRouteId(Long routeId);
    RouteStop save(RouteStop stop);
    RouteStop update(RouteStop stop);
    void delete(Long id);
	List<RouteStop> searchEnabledStopsByNameOrCity(String q, int limit);
}
