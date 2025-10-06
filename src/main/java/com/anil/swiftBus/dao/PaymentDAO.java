package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.Payment;

public interface PaymentDAO {
    Payment save(Payment payment);
    Payment update(Payment payment);
    void delete(Long paymentId);
    Payment findById(Long paymentId);
    List<Payment> findAll();
    List<Payment> findByBookingId(Long bookingId);
}
