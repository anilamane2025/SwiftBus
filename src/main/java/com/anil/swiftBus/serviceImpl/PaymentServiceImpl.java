package com.anil.swiftBus.serviceImpl;

import com.anil.swiftBus.dao.PaymentDAO;
import com.anil.swiftBus.dto.PaymentDTO;
import com.anil.swiftBus.entity.Payment;
import com.anil.swiftBus.ModelMapper.PaymentMapper;
import com.anil.swiftBus.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setGatewayTransactionId(paymentDTO.getGatewayTransactionId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        paymentDAO.save(payment);
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) {
        Payment payment = paymentDAO.findById(paymentDTO.getPaymentId());
        if (payment != null) {
            payment.setAmount(paymentDTO.getAmount());
            payment.setGatewayTransactionId(paymentDTO.getGatewayTransactionId());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setStatus(paymentDTO.getStatus());
            payment = paymentDAO.update(payment);
        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public void delete(Long paymentId) {
        paymentDAO.delete(paymentId);
    }

    @Override
    public PaymentDTO findById(Long paymentId) {
        return PaymentMapper.toDTO(paymentDAO.findById(paymentId));
    }

    @Override
    public List<PaymentDTO> findAll() {
        return paymentDAO.findAll().stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findByBookingId(Long bookingId) {
        return paymentDAO.findByBookingId(bookingId).stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
