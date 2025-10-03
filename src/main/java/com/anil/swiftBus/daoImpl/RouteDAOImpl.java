package com.anil.swiftBus.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.RouteDAO;
import com.anil.swiftBus.entity.Route;

@Repository
@Transactional
public class RouteDAOImpl implements RouteDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Route findById(Long id) {
        return em.find(Route.class, id);
    }

    @Override
    public List<Route> findAll() {
        return em.createQuery("SELECT DISTINCT r FROM Route r LEFT JOIN FETCH r.stops s WHERE r.enabled = true and s.enabled = true ORDER BY r.routeId,s.stopOrder", Route.class)
                 .getResultList();
    }

    @Override
    public Route save(Route route) {
        em.persist(route);
        return route;
    }

    @Override
    public Route update(Route route) {
        return em.merge(route);
    }

    @Override
    public void delete(Long id) {
        Route route = em.find(Route.class, id);
        if (route != null) {
            route.setEnabled(false); // soft delete
            em.merge(route);
        }
    }

	@Override
	public boolean existsByRouteNameIgnoreCaseAndDeletedFalse(String routeName) {
		Long count = em.createQuery(
                "SELECT COUNT(r) FROM Route r WHERE LOWER(r.routeName) = LOWER(:name) AND r.enabled = true",
                Long.class)
        .setParameter("name", routeName)
        .getSingleResult();
return count > 0;
	}

	@Override
	public boolean existsByRouteNameIgnoreCaseAndDeletedFalseAndRouteIdNot(String routeName, Long excludeId) {
		Long count = em.createQuery(
                "SELECT COUNT(r) FROM Route r WHERE LOWER(r.routeName) = LOWER(:name) AND r.routeId <> :id AND r.enabled = true",
                Long.class)
        .setParameter("name", routeName)
        .setParameter("id", excludeId)
        .getSingleResult();
return count > 0;
	}
}
