package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.BookingDTO;
import com.anil.swiftBus.dto.BookingRequestDTO;
import com.anil.swiftBus.dto.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingDTO save(BookingDTO bookingDTO);
    BookingDTO update(BookingDTO bookingDTO);
    void delete(Long bookingId);
    BookingDTO findById(Long bookingId);
    List<BookingDTO> findAll();
    
    BookingResponseDTO confirmBooking(BookingRequestDTO request);
}
