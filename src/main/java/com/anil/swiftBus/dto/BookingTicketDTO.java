package com.anil.swiftBus.dto;

import com.anil.swiftBus.enums.BookingTicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingTicketDTO {

    private Long bookingTicketId;
    private Long bookingId;
    private Long busSeatId;
    private Long fromRouteStopId;
    private Long toRouteStopId;
    private Long fromRouteStopPointId;
    private Long toRouteStopPointId;
    private String passengerName;
    private Long passengerAge;
    private String passengerGender;
    private BigDecimal farePaid;
    private BookingTicketStatus bookingTicketStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getBookingTicketId() {
		return bookingTicketId;
	}
	public void setBookingTicketId(Long bookingTicketId) {
		this.bookingTicketId = bookingTicketId;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getBusSeatId() {
		return busSeatId;
	}
	public void setBusSeatId(Long busSeatId) {
		this.busSeatId = busSeatId;
	}
	public Long getFromRouteStopId() {
		return fromRouteStopId;
	}
	public void setFromRouteStopId(Long fromRouteStopId) {
		this.fromRouteStopId = fromRouteStopId;
	}
	public Long getToRouteStopId() {
		return toRouteStopId;
	}
	public void setToRouteStopId(Long toRouteStopId) {
		this.toRouteStopId = toRouteStopId;
	}
	public Long getFromRouteStopPointId() {
		return fromRouteStopPointId;
	}
	public void setFromRouteStopPointId(Long fromRouteStopPointId) {
		this.fromRouteStopPointId = fromRouteStopPointId;
	}
	public Long getToRouteStopPointId() {
		return toRouteStopPointId;
	}
	public void setToRouteStopPointId(Long toRouteStopPointId) {
		this.toRouteStopPointId = toRouteStopPointId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Long getPassengerAge() {
		return passengerAge;
	}
	public void setPassengerAge(Long passengerAge) {
		this.passengerAge = passengerAge;
	}
	public String getPassengerGender() {
		return passengerGender;
	}
	public void setPassengerGender(String passengerGender) {
		this.passengerGender = passengerGender;
	}
	public BigDecimal getFarePaid() {
		return farePaid;
	}
	public void setFarePaid(BigDecimal farePaid) {
		this.farePaid = farePaid;
	}
	public BookingTicketStatus getBookingTicketStatus() {
		return bookingTicketStatus;
	}
	public void setBookingTicketStatus(BookingTicketStatus bookingTicketStatus) {
		this.bookingTicketStatus = bookingTicketStatus;
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
