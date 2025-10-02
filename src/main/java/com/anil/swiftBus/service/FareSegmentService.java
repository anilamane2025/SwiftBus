package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.dto.RouteDTO;

import java.util.List;

public interface FareSegmentService {
    FareSegmentDTO save(FareSegmentDTO dto);
    FareSegmentDTO findById(Long id);
    List<FareSegmentDTO> findByRoute(Long routeId);
    void delete(Long id);
    void generateMissingFaresForRoute(Long routeId);
    void updateFare(FareSegmentDTO dto);
    List<RouteDTO> getAllRoutesWithStopsAndFare();
}
