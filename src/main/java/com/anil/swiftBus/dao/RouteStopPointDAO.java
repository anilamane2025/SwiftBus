package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStopPoint;
import java.util.List;

public interface RouteStopPointDAO {
    RouteStopPoint save(RouteStopPoint point);
    RouteStopPoint update(RouteStopPoint point);
    void delete(Long id);
    RouteStopPoint findById(Long id);
    List<RouteStopPoint> findByStopId(Long stopId);
    List<Route> getAllRoutesWithStopsAndPoints();
}
