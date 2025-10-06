package com.anil.swiftBus.daoImpl;

import com.anil.swiftBus.dao.BookingDAO;
import com.anil.swiftBus.entity.Booking;
import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.enums.BookingTicketStatus;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Booking save(Booking booking) {
        em.persist(booking);
        return booking;
    }

    @Override
    public Booking update(Booking booking) {
        return em.merge(booking);
    }

    @Override
    public void delete(Long bookingId) {
        Booking b = findById(bookingId);
        if (b != null) em.remove(b);
    }

    @Override
    public Booking findById(Long bookingId) {
        return em.find(Booking.class, bookingId);
    }

    @Override
    public List<Booking> findAll() {
        return em.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    }

	@Override
	public int countBookedSeatsForTrip(Long tripId) {
		 String ql = "SELECT COUNT(bt) " +
	                "FROM BookingTicket bt " +
	                "JOIN bt.booking b " +
	                "WHERE b.trip.tripId = :tripId " +
	                "AND b.status = :bookingStatus " +
	                "AND bt.bookingTicketStatus = :ticketStatus";

	    Long count = em.createQuery(ql, Long.class)
	                   .setParameter("tripId", tripId)
	                   .setParameter("bookingStatus", BookingStatus.CONFIRMED)
	                   .setParameter("ticketStatus", BookingTicketStatus.ACTIVE)
	                   .getSingleResult();

	    return count.intValue();
	}

	@Override
	public int countBookedSeatsForTripWithRouteStop(Long tripId, Long fromStopId, Long toStopId) {
		String ql = "SELECT COUNT(bt) " +
                "FROM BookingTicket bt " +
                "JOIN bt.booking b " +
                "JOIN bt.fromRouteStop frs " +
                "JOIN bt.toRouteStop trs " +
                "JOIN RouteStop searchFrom ON searchFrom.routeStopId = :fromStopId " +
                "JOIN RouteStop searchTo ON searchTo.routeStopId = :toStopId " +
                "WHERE b.trip.tripId = :tripId " +
                "AND b.status = :bookingStatus " +
                "AND bt.bookingTicketStatus = :ticketStatus " +
                "AND ( " +
                "frs.stopOrder < searchTo.stopOrder " +
                "AND trs.stopOrder > searchFrom.stopOrder " +
                ")";

		Long count = em.createQuery(ql, Long.class)
		             .setParameter("tripId", tripId)
		             .setParameter("fromStopId", fromStopId)
		             .setParameter("toStopId", toStopId)
		             .setParameter("bookingStatus", BookingStatus.CONFIRMED)
		             .setParameter("ticketStatus", BookingTicketStatus.ACTIVE)
		             .getSingleResult();
		return count.intValue();
	}
}
