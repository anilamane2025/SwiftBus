package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.BookingTicket;

public interface BookingTicketDAO {
    BookingTicket save(BookingTicket ticket);
    BookingTicket update(BookingTicket ticket);
    void delete(Long bookingTicketId);
    BookingTicket findById(Long bookingTicketId);
    List<BookingTicket> findAll();
}
