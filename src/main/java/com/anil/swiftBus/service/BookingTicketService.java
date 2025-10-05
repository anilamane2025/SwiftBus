package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.BookingTicketDTO;
import java.util.List;

public interface BookingTicketService {
    BookingTicketDTO save(BookingTicketDTO ticketDTO);
    BookingTicketDTO update(BookingTicketDTO ticketDTO);
    void delete(Long bookingTicketId);
    BookingTicketDTO findById(Long bookingTicketId);
    List<BookingTicketDTO> findAll();
}
