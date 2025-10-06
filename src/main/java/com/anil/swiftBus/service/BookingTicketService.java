package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.BookingTicketDTO;

public interface BookingTicketService {
    BookingTicketDTO save(BookingTicketDTO ticketDTO);
    BookingTicketDTO update(BookingTicketDTO ticketDTO);
    void delete(Long bookingTicketId);
    BookingTicketDTO findById(Long bookingTicketId);
    List<BookingTicketDTO> findAll();
}
