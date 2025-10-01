package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.RouteStopPointDAO;
import com.anil.swiftBus.entity.Route;
import com.anil.swiftBus.entity.RouteStopPoint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RouteStopPointDAOImpl implements RouteStopPointDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public RouteStopPoint save(RouteStopPoint point) {
        em.persist(point);
        return point;
    }

    @Override
    public RouteStopPoint update(RouteStopPoint point) {
        return em.merge(point);
    }

    @Override
    public void delete(Long id) {
        RouteStopPoint point = findById(id);
        if (point != null) {
        	point.setEnabled(false);
            em.merge(point);
        }
    }

    @Override
    public RouteStopPoint findById(Long id) {
        return em.find(RouteStopPoint.class, id);
    }

    @Override
    public List<RouteStopPoint> findByStopId(Long stopId) {
        return em.createQuery("SELECT p FROM RouteStopPoint p WHERE p.routeStop.routeStopId = :stopId", RouteStopPoint.class)
                .setParameter("stopId", stopId)
                .getResultList();
    }

	@Override
	public List<Route> getAllRoutesWithStopsAndPoints() {
		List<Route> routes = em.createQuery(
		        "SELECT r FROM Route r WHERE r.enabled = true", Route.class)
		        .getResultList();

		// 2. Fetch stops for all routes
		routes.forEach(r -> r.getStops().size());

		// 3. Fetch stopPoints for all stops
		routes.forEach(r -> r.getStops()
		        .forEach(s -> s.getStopPoints().size()));
		return routes;
	}
}
