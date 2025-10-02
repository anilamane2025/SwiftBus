package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.FareSegmentDAO;
import com.anil.swiftBus.entity.FareSegment;
import com.anil.swiftBus.entity.Route;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FareSegmentDaoImpl implements FareSegmentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FareSegment save(FareSegment fareSegment) {
        if (fareSegment.getFareSegmentId() == null) {
            entityManager.persist(fareSegment);
            return fareSegment;
        } else {
            return entityManager.merge(fareSegment);
        }
    }

    @Override
    public FareSegment findById(Long id) {
        return entityManager.find(FareSegment.class, id);
    }

    @Override
    public List<FareSegment> findByRouteId(Long routeId) {
        return entityManager.createQuery(
                "SELECT f FROM FareSegment f " +
                "WHERE f.route.routeId = :routeId AND f.enabled = true", FareSegment.class)
                .setParameter("routeId", routeId)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        FareSegment f = findById(id);
        if (f != null) {
        	f.setEnabled(false);
            entityManager.merge(f);
        }
    }

	@Override
	public FareSegment findByRouteAndStops(Long routeId, Long fromStopId, Long toStopId) {
		List<FareSegment> results = entityManager.createQuery(
                "SELECT f FROM FareSegment f " +
                "WHERE f.route.routeId = :routeId " +
                "AND f.fromRouteStop.routeStopId = :fromStopId " +
                "AND f.toRouteStop.routeStopId = :toStopId " +
                "AND f.enabled = true", FareSegment.class)
            .setParameter("routeId", routeId)
            .setParameter("fromStopId", fromStopId)
            .setParameter("toStopId", toStopId)
            .getResultList();

        return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<Route> getAllRoutesWithStopsAndFare() {
		List<Route> routes = entityManager.createQuery(
		        "SELECT r FROM Route r WHERE r.enabled = true", Route.class)
		        .getResultList();

		// 2. Fetch stops for all routes
		routes.forEach(r -> r.getStops().size());

		// 3. Fetch stopPoints for all fares
		routes.forEach(r -> r.getStops()
		        .forEach(s -> s.getFaresFrom().size()));
		return routes;
	}
}
