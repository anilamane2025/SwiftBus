package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.dto.RouteDTO;

public interface FareSegmentService {
    FareSegmentDTO save(FareSegmentDTO dto);
    FareSegmentDTO findById(Long id);
    List<FareSegmentDTO> findByRoute(Long routeId);
    void delete(Long id);
    void generateMissingFaresForRoute(Long routeId);
    void updateFare(FareSegmentDTO dto);
    List<RouteDTO> getAllRoutesWithStopsAndFare();
}
