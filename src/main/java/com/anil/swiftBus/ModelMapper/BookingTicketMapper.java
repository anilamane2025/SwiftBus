package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.BookingTicketDTO;
import com.anil.swiftBus.entity.BookingTicket;

public class BookingTicketMapper {

    public static BookingTicketDTO toDTO(BookingTicket ticket) {
        if (ticket == null) return null;

        BookingTicketDTO dto = new BookingTicketDTO();
        dto.setBookingTicketId(ticket.getBookingTicketId());
        dto.setBookingId(ticket.getBooking() != null ? ticket.getBooking().getBookingId() : null);
        dto.setBusSeatId(ticket.getBusSeat() != null ? ticket.getBusSeat().getBusSeatId() : null);
        dto.setFromRouteStopId(ticket.getFromRouteStop() != null ? ticket.getFromRouteStop().getRouteStopId() : null);
        dto.setToRouteStopId(ticket.getToRouteStop() != null ? ticket.getToRouteStop().getRouteStopId() : null);
        dto.setFromRouteStopPointId(ticket.getFromRouteStopPoint() != null ? ticket.getFromRouteStopPoint().getRouteStopPointId() : null);
        dto.setToRouteStopPointId(ticket.getToRouteStopPoint() != null ? ticket.getToRouteStopPoint().getRouteStopPointId() : null);
        dto.setPassengerName(ticket.getPassengerName());
        dto.setPassengerAge(ticket.getPassengerAge());
        dto.setPassengerGender(ticket.getPassengerGender());
        dto.setFarePaid(ticket.getFarePaid());
        dto.setBookingTicketStatus(ticket.getBookingTicketStatus());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());

        return dto;
    }
}
