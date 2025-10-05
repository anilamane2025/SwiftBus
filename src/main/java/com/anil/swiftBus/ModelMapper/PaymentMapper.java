package com.anil.swiftBus.ModelMapper;

import com.anil.swiftBus.dto.PaymentDTO;
import com.anil.swiftBus.entity.Payment;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking() != null ? payment.getBooking().getBookingId() : null);
        dto.setAmount(payment.getAmount());
        dto.setGatewayTransactionId(payment.getGatewayTransactionId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());

        return dto;
    }
}
