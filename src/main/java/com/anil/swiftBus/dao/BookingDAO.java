package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Booking;
import java.util.List;

public interface BookingDAO {
    Booking save(Booking booking);
    Booking update(Booking booking);
    void delete(Long bookingId);
    Booking findById(Long bookingId);
    List<Booking> findAll();
	int countBookedSeatsForTrip(Long tripId);
}
