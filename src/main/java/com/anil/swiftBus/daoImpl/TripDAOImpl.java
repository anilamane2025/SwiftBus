package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.TripDAO;
import com.anil.swiftBus.entity.Trip;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class TripDAOImpl implements TripDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Trip save(Trip trip) {
        em.persist(trip);
        return trip;
    }

    @Override
    public Trip update(Trip trip) {
        return em.merge(trip);
    }

    @Override
    public Trip findById(Long tripId) {
        return em.find(Trip.class, tripId);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
    }

    @Override
    public List<Trip> findByBusId(Long busId) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.bus.busId = :busId", Trip.class)
                 .setParameter("busId", busId)
                 .getResultList();
    }

    @Override
    public List<Trip> findByRouteId(Long routeId) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.route.routeId = :routeId", Trip.class)
                 .setParameter("routeId", routeId)
                 .getResultList();
    }

    @Override
    public List<Trip> findByServiceDate(LocalDate date) {
        return em.createQuery("SELECT t FROM Trip t WHERE t.serviceDate = :date", Trip.class)
                 .setParameter("date", date)
                 .getResultList();
    }

}
