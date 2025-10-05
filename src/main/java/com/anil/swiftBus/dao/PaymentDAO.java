package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Payment;
import java.util.List;

public interface PaymentDAO {
    Payment save(Payment payment);
    Payment update(Payment payment);
    void delete(Long paymentId);
    Payment findById(Long paymentId);
    List<Payment> findAll();
    List<Payment> findByBookingId(Long bookingId);
}
