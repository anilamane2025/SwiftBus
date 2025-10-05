package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.dao.BookingTicketDAO;
import com.anil.swiftBus.dto.BookingTicketDTO;
import com.anil.swiftBus.entity.BookingTicket;
import com.anil.swiftBus.ModelMapper.BookingTicketMapper;
import com.anil.swiftBus.service.BookingTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingTicketServiceImpl implements BookingTicketService {

    private final BookingTicketDAO ticketDAO;

    public BookingTicketServiceImpl(BookingTicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    public BookingTicketDTO save(BookingTicketDTO ticketDTO) {
        BookingTicket ticket = new BookingTicket();
        ticket.setPassengerName(ticketDTO.getPassengerName());
        ticket.setPassengerAge(ticketDTO.getPassengerAge());
        ticket.setPassengerGender(ticketDTO.getPassengerGender());
        ticket.setFarePaid(ticketDTO.getFarePaid());
        ticket.setBookingTicketStatus(ticketDTO.getBookingTicketStatus());
        ticketDAO.save(ticket);
        return BookingTicketMapper.toDTO(ticket);
    }

    @Override
    public BookingTicketDTO update(BookingTicketDTO ticketDTO) {
        BookingTicket ticket = ticketDAO.findById(ticketDTO.getBookingTicketId());
        if (ticket != null) {
            ticket.setPassengerName(ticketDTO.getPassengerName());
            ticket.setPassengerAge(ticketDTO.getPassengerAge());
            ticket.setPassengerGender(ticketDTO.getPassengerGender());
            ticket.setFarePaid(ticketDTO.getFarePaid());
            ticket.setBookingTicketStatus(ticketDTO.getBookingTicketStatus());
            ticket = ticketDAO.update(ticket);
        }
        return BookingTicketMapper.toDTO(ticket);
    }

    @Override
    public void delete(Long bookingTicketId) {
        ticketDAO.delete(bookingTicketId);
    }

    @Override
    public BookingTicketDTO findById(Long bookingTicketId) {
        return BookingTicketMapper.toDTO(ticketDAO.findById(bookingTicketId));
    }

    @Override
    public List<BookingTicketDTO> findAll() {
        return ticketDAO.findAll().stream()
                .map(BookingTicketMapper::toDTO)
                .collect(Collectors.toList());
    }
}
