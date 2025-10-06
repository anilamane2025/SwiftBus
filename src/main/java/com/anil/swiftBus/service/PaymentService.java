package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO save(PaymentDTO paymentDTO);
    PaymentDTO update(PaymentDTO paymentDTO);
    void delete(Long paymentId);
    PaymentDTO findById(Long paymentId);
    List<PaymentDTO> findAll();
    List<PaymentDTO> findByBookingId(Long bookingId);
}
