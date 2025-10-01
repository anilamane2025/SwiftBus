package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import java.util.List;

public interface RouteStopPointService {
    void deletePoint(Long id);
    RouteStopPointDTO getPointById(Long id);
    List<RouteStopPointDTO> getPointsByStop(Long stopId);
    List<RouteDTO> getAllRoutesWithStopsAndPoints();
    RouteStopPointDTO saveOrUpdate(RouteStopPointDTO dto);

}
