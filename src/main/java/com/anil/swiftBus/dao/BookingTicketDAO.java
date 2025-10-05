package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.BookingTicket;
import java.util.List;

public interface BookingTicketDAO {
    BookingTicket save(BookingTicket ticket);
    BookingTicket update(BookingTicket ticket);
    void delete(Long bookingTicketId);
    BookingTicket findById(Long bookingTicketId);
    List<BookingTicket> findAll();
}
