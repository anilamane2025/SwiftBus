package com.anil.swiftBus.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.anil.swiftBus.enums.BookingTicketStatus;

@Entity
@Table(name = "booking_ticket")
public class BookingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_ticket_id")
    private Long bookingTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_seat_id", nullable = false)
    private BusSeat busSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_route_stop_id", nullable = false)
    private RouteStop fromRouteStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_route_stop_id", nullable = false)
    private RouteStop toRouteStop;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_route_stop_point_id", nullable = false)
    private RouteStopPoint fromRouteStopPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_route_stop_point_id", nullable = false)
    private RouteStopPoint toRouteStopPoint;

    @Column(name = "passenger_name", nullable = false, length = 150)
    private String passengerName;

    @Column(name = "passenger_age")
    private Long passengerAge;

    @Column(name = "passenger_gender", length = 10)
    private String passengerGender;

    @Column(name = "fare_paid", nullable = false)
    private BigDecimal farePaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_ticket_status", length = 20)
    private BookingTicketStatus bookingTicketStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
	public Long getBookingTicketId() {
		return bookingTicketId;
	}

	public void setBookingTicketId(Long bookingTicketId) {
		this.bookingTicketId = bookingTicketId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public BusSeat getBusSeat() {
		return busSeat;
	}

	public void setBusSeat(BusSeat busSeat) {
		this.busSeat = busSeat;
	}

	public RouteStop getFromRouteStop() {
		return fromRouteStop;
	}

	public void setFromRouteStop(RouteStop fromRouteStop) {
		this.fromRouteStop = fromRouteStop;
	}

	public RouteStop getToRouteStop() {
		return toRouteStop;
	}

	public void setToRouteStop(RouteStop toRouteStop) {
		this.toRouteStop = toRouteStop;
	}

	public RouteStopPoint getFromRouteStopPoint() {
		return fromRouteStopPoint;
	}

	public void setFromRouteStopPoint(RouteStopPoint fromRouteStopPoint) {
		this.fromRouteStopPoint = fromRouteStopPoint;
	}

	public RouteStopPoint getToRouteStopPoint() {
		return toRouteStopPoint;
	}

	public void setToRouteStopPoint(RouteStopPoint toRouteStopPoint) {
		this.toRouteStopPoint = toRouteStopPoint;
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
