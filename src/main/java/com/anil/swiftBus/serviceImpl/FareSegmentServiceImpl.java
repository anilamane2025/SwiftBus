package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.ModelMapper.FareSegmentMapper;
import com.anil.swiftBus.ModelMapper.RouteMapper;
import com.anil.swiftBus.dao.FareSegmentDAO;
import com.anil.swiftBus.dao.RouteDAO;
import com.anil.swiftBus.dto.FareSegmentDTO;
import com.anil.swiftBus.dto.RouteDTO;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.service.FareSegmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FareSegmentServiceImpl implements FareSegmentService {

    private final FareSegmentDAO fareSegmentDao;
    
    private final RouteDAO routeDAO;

    @PersistenceContext
    private EntityManager entityManager;

    public FareSegmentServiceImpl(FareSegmentDAO fareSegmentDao,RouteDAO routeDAO) {
        this.fareSegmentDao = fareSegmentDao;
        this.routeDAO = routeDAO;
    }

    @Override
    public FareSegmentDTO save(FareSegmentDTO dto) {
        Route route = entityManager.find(Route.class, dto.getRouteId());
        RouteStop fromStop = entityManager.find(RouteStop.class, dto.getFromRouteStopId());
        RouteStop toStop = entityManager.find(RouteStop.class, dto.getToRouteStopId());

        FareSegment entity = FareSegmentMapper.toEntity(dto, route, fromStop, toStop);
        FareSegment saved = fareSegmentDao.save(entity);
        return FareSegmentMapper.toDto(saved);
    }

    @Override
    public FareSegmentDTO findById(Long id) {
        return FareSegmentMapper.toDto(fareSegmentDao.findById(id));
    }

    @Override
    public List<FareSegmentDTO> findByRoute(Long routeId) {
        return fareSegmentDao.findByRouteId(routeId)
                .stream()
                .map(FareSegmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        fareSegmentDao.delete(id);
    }

	@Override
	public void generateMissingFaresForRoute(Long routeId) {
		Route route = routeDAO.findById(routeId);
        if (route == null || !route.isEnabled()) {
            throw new IllegalArgumentException("Route not found or disabled");
        }

        List<RouteStop> stops = route.getStops()
            .stream()
            .filter(RouteStop::isEnabled)
            .sorted(Comparator.comparingInt(RouteStop::getStopOrder))
            .collect(Collectors.toList());

        for (int i = 0; i < stops.size(); i++) {
            for (int j = i + 1; j < stops.size(); j++) {
                RouteStop from = stops.get(i);
                RouteStop to = stops.get(j);

                FareSegment existing = fareSegmentDao.findByRouteAndStops(
                        routeId, from.getRouteStopId(), to.getRouteStopId());

                if (existing == null) {
                    FareSegment fs = new FareSegment();
                    fs.setRoute(route);
                    fs.setFromRouteStop(from);
                    fs.setToRouteStop(to);
                    fs.setFareAmount(BigDecimal.ZERO); // default value
                    fs.setEnabled(true);
                    fareSegmentDao.save(fs);
                }
            }
        }
	}

	@Override
	public void updateFare(FareSegmentDTO dto) {
		FareSegment fs = fareSegmentDao.findById(dto.getFareSegmentId());
        if (fs != null && fs.isEnabled()) {
            fs.setFareAmount(dto.getFareAmount());
            fareSegmentDao.save(fs);
        }
	}

	@Override
	public List<RouteDTO> getAllRoutesWithStopsAndFare() {
		List<Route> routes = fareSegmentDao.getAllRoutesWithStopsAndFare();

//		 routes.forEach(route -> {
//		        if (route.getStops() != null) {
//		            route.getStops().removeIf(stop -> !stop.isEnabled()); 
//
//		            route.getStops().forEach(stop -> {
//		                if (stop.getFaresFrom() != null) {
//		                    stop.getFaresFrom().removeIf(sp -> !sp.isEnabled()); 
//		                }
//		            });
//		        }
//		    });
		
		return routes.stream()
               .map(RouteMapper::toDTOWithFare)
               .collect(Collectors.toList());
	}
}
