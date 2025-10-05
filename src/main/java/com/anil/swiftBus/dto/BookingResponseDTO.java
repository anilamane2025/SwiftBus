package com.anil.swiftBus.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookingResponseDTO {
	private Long bookingId;
    private String status;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private List<String> bookedSeats;
    private String message;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<String> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    

    
}
