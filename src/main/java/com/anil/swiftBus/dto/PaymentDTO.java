package com.anil.swiftBus.dto;

import com.anil.swiftBus.enums.PaymentMethod;
import com.anil.swiftBus.enums.PaymentTxnStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {

    private Long paymentId;
    private Long bookingId;
    private BigDecimal amount;
    private String gatewayTransactionId;
    private PaymentMethod paymentMethod;
    private PaymentTxnStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getGatewayTransactionId() {
		return gatewayTransactionId;
	}
	public void setGatewayTransactionId(String gatewayTransactionId) {
		this.gatewayTransactionId = gatewayTransactionId;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public PaymentTxnStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentTxnStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
}
