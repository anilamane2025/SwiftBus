package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.dto.BookingTicketListDTO;
import com.anil.swiftBus.entity.Booking;

public interface BookingDAO {
    Booking save(Booking booking);
    Booking update(Booking booking);
    void delete(Long bookingId);
    Booking findById(Long bookingId);
    List<Booking> findAll();
	int countBookedSeatsForTrip(Long tripId);
	int countBookedSeatsForTripWithRouteStop(Long tripId, Long routeStopId, Long routeStopId2);
	Long findTotalIncome();
	List<BookingTicketListDTO> getAllBookingTicketsForAdmin();
	List<BookingTicketListDTO> getAllBookingTicketsByUser(Long userId);
}
