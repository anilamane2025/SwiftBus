package com.anil.swiftBus.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.anil.swiftBus.ModelMapper.RouteStopMapper;
import com.anil.swiftBus.dao.RouteStopDAO;
import com.anil.swiftBus.dto.RouteStopDTO;
import com.anil.swiftBus.entity.RouteStop;
import com.anil.swiftBus.service.RouteStopService;

@Service
@Transactional
public class RouteStopServiceImpl implements RouteStopService {

    private final RouteStopDAO routeStopDAO;

    public RouteStopServiceImpl(RouteStopDAO routeStopDAO) {
        this.routeStopDAO = routeStopDAO;
    }

    @Override
    public RouteStopDTO getById(Long id) {
        RouteStop stop = routeStopDAO.findById(id);
        return RouteStopMapper.toDTO(stop);
    }

    @Override
    public List<RouteStopDTO> getByRouteId(Long routeId) {
        return routeStopDAO.findByRouteId(routeId).stream()
                .map(RouteStopMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteStopDTO create(RouteStopDTO stopDTO) {
        RouteStop stop = RouteStopMapper.toEntity(stopDTO);
        routeStopDAO.save(stop);
        return RouteStopMapper.toDTO(stop);
    }

    @Override
    public RouteStopDTO update(RouteStopDTO stopDTO) {
        RouteStop stop = RouteStopMapper.toEntity(stopDTO);
        RouteStop updated = routeStopDAO.update(stop);
        return RouteStopMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        routeStopDAO.delete(id);
    }

	@Override
	public List<RouteStopDTO> searchEnabledStopsByNameOrCity(String q, int limit) {
		
  List<RouteStop> list = routeStopDAO.searchEnabledStopsByNameOrCity( q,  limit);
  return list.stream().map(RouteStopMapper::toDTO).collect(Collectors.toList());
	}
}
