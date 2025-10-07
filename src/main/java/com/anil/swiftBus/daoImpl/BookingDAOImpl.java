package com.anil.swiftBus.daoImpl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.BookingDAO;
import com.anil.swiftBus.dto.BookingTicketListDTO;
import com.anil.swiftBus.entity.Booking;
import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.enums.BookingTicketStatus;
import com.anil.swiftBus.enums.PaymentStatus;

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
		String ql = "SELECT COUNT(distinct bt.busSeat.busSeatId) " +
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

	@Override
	public Long findTotalIncome() {
		String ql = "SELECT SUM(b.totalAmount) " +
                "FROM Booking b " +
                "WHERE b.status = :bookingStatus " +
                "AND b.paymentStatus = :paymentStatus";
		
		BigDecimal count = em.createQuery(ql, BigDecimal.class)
		             .setParameter("bookingStatus", BookingStatus.CONFIRMED)
		             .setParameter("paymentStatus", PaymentStatus.PAID)
		             .getSingleResult();
		
		return (count != null)?count.longValue():0;
	}

	@Override
	public List<BookingTicketListDTO> getAllBookingTicketsForAdmin() {
	    String ql = ""+
	        "SELECT new com.anil.swiftBus.dto.BookingTicketListDTO( " +
	            "b.bookingId, " +
	            "bt.passengerName, " +
	            "bt.passengerAge, " +
	            "bt.passengerGender, " +
	            "b.bookingTime, " +
	            "b.totalAmount, " +
	            "CASE WHEN b.agent IS NOT NULL THEN true ELSE false END, " +
	            "b.status, " +
	            "b.paymentStatus, " +
	            "COALESCE(CONCAT(a.firstName, ' ', a.lastName), CONCAT(p.firstName, ' ', p.lastName))," +
	            "COALESCE(a.phoneNumber, p.phoneNumber), " +
	            "frs.stopName, " +
	            "trs.stopName, " +
	            "frsp.pointName, " +
	            "trsp.pointName, " +
	            "bus.busName, " +
	            "bus.registrationNo, " +
	            "bs.seatNumber, " + 
	            "trip.serviceDate, " +
	            "trip.status, " +
	            "frs.minutesFromStart, " +
	            "trs.minutesFromStart, " +
	            "trip.departureDatetime " +
	        ") " +
	        "FROM BookingTicket bt " +
	        "JOIN bt.booking b " +
	        "JOIN b.trip trip " +
	        "JOIN trip.bus bus " +
	        "JOIN bt.busSeat bs " +
	        "JOIN bt.fromRouteStop frs " +
	        "JOIN bt.toRouteStop trs " +
	        "JOIN bt.fromRouteStopPoint frsp " +
	        "JOIN bt.toRouteStopPoint trsp " +
	        "LEFT JOIN b.agent a " +
	        "JOIN b.passenger p " +
	        "ORDER BY b.bookingTime DESC ";

	    return em.createQuery(ql, BookingTicketListDTO.class).getResultList();
	}

	@Override
	public List<BookingTicketListDTO> getAllBookingTicketsByUser(Long userId) {
		String ql = ""+
		        "SELECT new com.anil.swiftBus.dto.BookingTicketListDTO( " +
		            "b.bookingId, " +
		            "bt.passengerName, " +
		            "bt.passengerAge, " +
		            "bt.passengerGender, " +
		            "b.bookingTime, " +
		            "b.totalAmount, " +
		            "CASE WHEN b.agent IS NOT NULL THEN true ELSE false END, " +
		            "b.status, " +
		            "b.paymentStatus, " +
		            "COALESCE(CONCAT(a.firstName, ' ', a.lastName), CONCAT(p.firstName, ' ', p.lastName))," +
		            "COALESCE(a.phoneNumber, p.phoneNumber), " +
		            "frs.stopName, " +
		            "trs.stopName, " +
		            "frsp.pointName, " +
		            "trsp.pointName, " +
		            "bus.busName, " +
		            "bus.registrationNo, " +
		            "bs.seatNumber, " + 
		            "trip.serviceDate, " +
		            "trip.status, " +
		            "frs.minutesFromStart, " +
		            "trs.minutesFromStart, " +
		            "trip.departureDatetime " +
		        ") " +
		        "FROM BookingTicket bt " +
		        "JOIN bt.booking b " +
		        "JOIN b.trip trip " +
		        "JOIN trip.bus bus " +
		        "JOIN bt.busSeat bs " +
		        "JOIN bt.fromRouteStop frs " +
		        "JOIN bt.toRouteStop trs " +
		        "JOIN bt.fromRouteStopPoint frsp " +
		        "JOIN bt.toRouteStopPoint trsp " +
		        "LEFT JOIN b.agent a " +
		        "JOIN b.passenger p " +
		        "WHERE p.id = :userId " +
		        "ORDER BY b.bookingTime DESC ";

		    return em.createQuery(ql, BookingTicketListDTO.class)
		    		.setParameter("userId", userId)
		    		.getResultList();
	}

	
}
