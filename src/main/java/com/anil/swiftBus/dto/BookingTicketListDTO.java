package com.anil.swiftBus.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.anil.swiftBus.enums.BookingStatus;
import com.anil.swiftBus.enums.PaymentStatus;
import com.anil.swiftBus.enums.TripStatus;

public class BookingTicketListDTO {

    private Long bookingId;
    private String passengerName;
    private Long passengerAge;
    private String passengerGender;
    private LocalDateTime bookingTime;
    private String bookingTimeFormat;
    private BigDecimal totalAmount;
    private boolean bookedByAgent;
    private String bookingStatus;
    private String paymentStatus;

    private String bookedByName;
    private String bookedByPhone;

    private String fromStopName;
    private String toStopName;
    private String fromStopPointName;
    private String toStopPointName;

    private String busName;
    private String seatNumber;
    private LocalDate serviceDate;
    private String tripStatus;
    private String fromDepartureTime;
    private String toArrivalTime;
    private String duration;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
    LocalDateTime depTime=null , arrTime=null;
    
    

    
    public BookingTicketListDTO(Long bookingId, String passengerName, Long passengerAge, String passengerGender,
            LocalDateTime bookingTime, BigDecimal totalAmount, boolean bookedByAgent,
            BookingStatus bookingStatus, PaymentStatus paymentStatus, String bookedByName, String bookedByPhone,
            String fromStopName, String toStopName, String fromStopPointName, String toStopPointName,
            String busName, String seatNumber, LocalDate serviceDate, TripStatus tripStatus, Integer minutesFromStart, Integer toMinutesFromStart, LocalDateTime departureDatetime ) {
			
    		this.bookingId = bookingId;
			this.passengerName = passengerName;
			this.passengerAge = passengerAge;
			this.passengerGender = passengerGender;
			this.bookingTime = bookingTime;
			this.bookingTimeFormat = bookingTime.format(formatter);
			this.totalAmount = totalAmount;
			this.bookedByAgent = bookedByAgent;
			this.bookingStatus = bookingStatus.name();
			this.paymentStatus = paymentStatus.name();
			this.bookedByName = bookedByName;
			this.bookedByPhone = bookedByPhone;
			this.fromStopName = fromStopName;
			this.toStopName = toStopName;
			this.fromStopPointName = fromStopPointName;
			this.toStopPointName = toStopPointName;
			this.busName = busName;
			this.seatNumber = seatNumber;
			this.serviceDate = serviceDate;
			this.tripStatus = tripStatus.name();
			
			if (departureDatetime != null) {
				depTime = departureDatetime;
			    if (minutesFromStart != null) {
			        depTime = depTime.plusMinutes(minutesFromStart);
			    }
			    this.fromDepartureTime = (depTime.format(formatter));
			    
			     arrTime = departureDatetime;
			    if (toMinutesFromStart != null) {
			        arrTime = depTime.plusMinutes(toMinutesFromStart);
			    }
			    this.toArrivalTime = (arrTime.format(formatter));
			}
			
			
			if (departureDatetime != null && minutesFromStart != null && toMinutesFromStart != null) {
			
				Duration d = Duration.between(depTime, arrTime);
				this.duration = (String.format("%dh %02dm", d.toHours(), d.minusHours(d.toHours()).toMinutes()));
			} 
			
	}

    
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
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
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public boolean isBookedByAgent() {
		return bookedByAgent;
	}
	public void setBookedByAgent(boolean bookedByAgent) {
		this.bookedByAgent = bookedByAgent;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getBookedByName() {
		return bookedByName;
	}
	public void setBookedByName(String bookedByName) {
		this.bookedByName = bookedByName;
	}
	public String getBookedByPhone() {
		return bookedByPhone;
	}
	public void setBookedByPhone(String bookedByPhone) {
		this.bookedByPhone = bookedByPhone;
	}
	public String getFromStopName() {
		return fromStopName;
	}
	public void setFromStopName(String fromStopName) {
		this.fromStopName = fromStopName;
	}
	public String getToStopName() {
		return toStopName;
	}
	public void setToStopName(String toStopName) {
		this.toStopName = toStopName;
	}
	public String getFromStopPointName() {
		return fromStopPointName;
	}
	public void setFromStopPointName(String fromStopPointName) {
		this.fromStopPointName = fromStopPointName;
	}
	public String getToStopPointName() {
		return toStopPointName;
	}
	public void setToStopPointName(String toStopPointName) {
		this.toStopPointName = toStopPointName;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getSeatNumbers() {
		return seatNumber;
	}
	public void setSeatNumbers(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getTripStatus() {
		return tripStatus;
	}
	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}


	public LocalDate getServiceDate() {
		return serviceDate;
	}


	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}

	


	public String getFromDepartureTime() {
		return fromDepartureTime;
	}


	public void setFromDepartureTime(String fromDepartureTime) {
		this.fromDepartureTime = fromDepartureTime;
	}


	public String getToArrivalTime() {
		return toArrivalTime;
	}


	public void setToArrivalTime(String toArrivalTime) {
		this.toArrivalTime = toArrivalTime;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getBookingTimeFormat() {
		return bookingTimeFormat;
	}


	public void setBookingTimeFormat(String bookingTimeFormat) {
		this.bookingTimeFormat = bookingTimeFormat;
	}


	public String getSeatNumber() {
		return seatNumber;
	}


	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

    
}
