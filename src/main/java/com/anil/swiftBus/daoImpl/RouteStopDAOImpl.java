package com.anil.swiftBus.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.RouteStopDAO;
import com.anil.swiftBus.entity.RouteStop;

@Repository
@Transactional
public class RouteStopDAOImpl implements RouteStopDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public RouteStop findById(Long id) {
        return em.find(RouteStop.class, id);
    }

    @Override
    public List<RouteStop> findByRouteId(Long routeId) {
        return em.createQuery("SELECT rs FROM RouteStop rs WHERE rs.route.routeId = :routeId", RouteStop.class)
                 .setParameter("routeId", routeId)
                 .getResultList();
    }

    @Override
    public RouteStop save(RouteStop stop) {
        em.persist(stop);
        return stop;
    }

    @Override
    public RouteStop update(RouteStop stop) {
        return em.merge(stop);
    }

    @Override
    public void delete(Long id) {
        RouteStop stop = em.find(RouteStop.class, id);
        if (stop != null) {
            em.remove(stop);
        }
    }

	@Override
	public List<RouteStop> searchEnabledStopsByNameOrCity(String q, int limit) {
		String jpql = "SELECT rs FROM RouteStop rs JOIN FETCH rs.city c WHERE rs.enabled = true AND " +
                "(LOWER(rs.stopName) LIKE :q OR LOWER(c.cityName) LIKE :q)";
		return em.createQuery(jpql, RouteStop.class)
                .setParameter("q", "%" + q.toLowerCase() + "%")
                .setMaxResults(limit)
                .getResultList();
	}
}
