package com.anil.swiftBus.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripDTO {

    private Long tripId;
    private Long busId;
    private Long routeId;
    private String busName;
    private String routeName;
    private LocalDate serviceDate;
    private LocalDateTime departureDatetime;
    private LocalDateTime arrivalDatetime;
    private String status; // SCHEDULED, CANCELLED, COMPLETED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	public Long getBusId() {
		return busId;
	}
	public void setBusId(Long busId) {
		this.busId = busId;
	}
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public LocalDate getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}
	public LocalDateTime getDepartureDatetime() {
		return departureDatetime;
	}
	public void setDepartureDatetime(LocalDateTime departureDatetime) {
		this.departureDatetime = departureDatetime;
	}
	public LocalDateTime getArrivalDatetime() {
		return arrivalDatetime;
	}
	public void setArrivalDatetime(LocalDateTime arrivalDatetime) {
		this.arrivalDatetime = arrivalDatetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
