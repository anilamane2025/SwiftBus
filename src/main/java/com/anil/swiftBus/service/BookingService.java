package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.BookingDTO;
import com.anil.swiftBus.dto.BookingRequestDTO;
import com.anil.swiftBus.dto.BookingResponseDTO;
import com.anil.swiftBus.dto.BookingTicketListDTO;

public interface BookingService {
    BookingDTO save(BookingDTO bookingDTO);
    BookingDTO update(BookingDTO bookingDTO);
    void delete(Long bookingId);
    BookingDTO findById(Long bookingId);
    List<BookingDTO> findAll();
    
    BookingResponseDTO confirmBooking(BookingRequestDTO request);
	Long findTotalIncome();
	List<BookingTicketListDTO> getBookingTicketsForAdmin() ;
	List<BookingTicketListDTO> getBookingTicketsByUser(Long userId);
	void cancelBooking(Long bookingId, String name);
	
}
