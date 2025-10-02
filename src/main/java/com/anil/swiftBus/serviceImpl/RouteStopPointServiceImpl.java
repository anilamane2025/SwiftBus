package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.ModelMapper.RouteMapper;
import com.anil.swiftBus.ModelMapper.RouteStopPointMapper;
import com.anil.swiftBus.dao.RouteStopPointDAO;
import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.dto.RouteStopPointDTO;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.entity.RouteStopPoint;
import com.anil.swiftBus.enums.StopPointType;
import com.anil.swiftBus.service.RouteStopPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class RouteStopPointServiceImpl implements RouteStopPointService {

    private final RouteStopPointDAO dao;

    @PersistenceContext
    private EntityManager em;

    public RouteStopPointServiceImpl(RouteStopPointDAO dao) {
        this.dao = dao;
    }

    @Override
    public void deletePoint(Long id) {
        dao.delete(id);
    }

    @Override
    public RouteStopPointDTO getPointById(Long id) {
        RouteStopPoint entity = dao.findById(id);
        if (entity == null) {
            throw new IllegalArgumentException("RouteStopPoint not found for id " + id);
        }
        return RouteStopPointMapper.toDto(entity);
    }

    @Override
    public List<RouteStopPointDTO> getPointsByStop(Long stopId) {
        return dao.findByStopId(stopId).stream()
                .map(RouteStopPointMapper::toDto)
                .collect(Collectors.toList());
    }

	@Override
	public List<RouteDTO> getAllRoutesWithStopsAndPoints() {
		List<Route> routes = dao.getAllRoutesWithStopsAndPoints();

//		 routes.forEach(route -> {
//		        if (route.getStops() != null) {
//		            route.getStops().removeIf(stop -> !stop.isEnabled()); 
//
//		            route.getStops().forEach(stop -> {
//		                if (stop.getStopPoints() != null) {
//		                    stop.getStopPoints().removeIf(sp -> !sp.isEnabled()); 
//		                }
//		            });
//		        }
//		    });
		
		return routes.stream()
                .map(RouteMapper::toDTO)
                .collect(Collectors.toList());
	}

	@Override
	public RouteStopPointDTO saveOrUpdate(RouteStopPointDTO dto) {
		RouteStop parentStop = em.find(RouteStop.class, dto.getRouteStopId());
        if (parentStop == null) {
            throw new IllegalArgumentException("RouteStop not found for id " + dto.getRouteStopId());
        }

        RouteStopPoint entity = RouteStopPointMapper.toEntity(dto, parentStop);
        if (dto.getRouteStopPointId() == null) {
            dao.save(entity);
        } else {
            dao.update(entity);
        }
        return RouteStopPointMapper.toDto(entity);
	}
}
