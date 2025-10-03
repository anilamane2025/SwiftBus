package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;

public interface RouteStopPointService {
    void deletePoint(Long id);
    RouteStopPointDTO getPointById(Long id);
    List<RouteStopPointDTO> getPointsByStop(Long stopId);
    List<RouteDTO> getAllRoutesWithStopsAndPoints();
    RouteStopPointDTO saveOrUpdate(RouteStopPointDTO dto);

}
