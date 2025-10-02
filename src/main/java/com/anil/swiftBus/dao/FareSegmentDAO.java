package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Route;

import java.util.List;

public interface FareSegmentDAO {
    FareSegment save(FareSegment fareSegment);
    FareSegment findById(Long id);
    List<FareSegment> findByRouteId(Long routeId);
    void delete(Long id);
    FareSegment findByRouteAndStops(Long routeId, Long fromStopId, Long toStopId);
    List<Route> getAllRoutesWithStopsAndFare();
}
