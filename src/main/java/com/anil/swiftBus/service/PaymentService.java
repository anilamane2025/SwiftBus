package com.anil.swiftBus.service;

import com.anil.swiftBus.dto.PaymentDTO;
import java.util.List;

public interface PaymentService {
    PaymentDTO save(PaymentDTO paymentDTO);
    PaymentDTO update(PaymentDTO paymentDTO);
    void delete(Long paymentId);
    PaymentDTO findById(Long paymentId);
    List<PaymentDTO> findAll();
    List<PaymentDTO> findByBookingId(Long bookingId);
}
