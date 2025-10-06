package com.anil.swiftBus.ModelMapper;

import java.util.stream.Collectors;

import com.anil.swiftBus.dto.BookingDTO;
import com.anil.swiftBus.entity.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;

        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setTripId(booking.getTrip() != null ? booking.getTrip().getTripId() : null);
        dto.setAgentId(booking.getAgent() != null ? booking.getAgent().getId() : null);
        dto.setPassengerId(booking.getPassenger() != null ? booking.getPassenger().getId() : null);
        dto.setBookingTime(booking.getBookingTime());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setStatus(booking.getStatus());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());

        if (booking.getTickets() != null) {
            dto.setTickets(
                booking.getTickets().stream()
                       .map(BookingTicketMapper::toDTO)
                       .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
