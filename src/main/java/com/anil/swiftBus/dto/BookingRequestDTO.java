package com.anil.swiftBus.dto;

import java.util.List;

public class BookingRequestDTO {
    private Long tripId;
    private Long busId;
    private Long fareSegmentId;
    private String fromName;
    private String toName;
    private String passengerName;
    private Integer passengerAge;
    private String gender;
    private List<String> selectedSeats;
    private Long pickupPoint;
    private Long dropPoint;
    private Long agentId; // optional, if booking via agent
    private Long passengerId;
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
	public Long getFareSegmentId() {
		return fareSegmentId;
	}
	public void setFareSegmentId(Long fareSegmentId) {
		this.fareSegmentId = fareSegmentId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Integer getPassengerAge() {
		return passengerAge;
	}
	public void setPassengerAge(Integer passengerAge) {
		this.passengerAge = passengerAge;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<String> getSelectedSeats() {
		return selectedSeats;
	}
	public void setSelectedSeats(List<String> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}
	public Long getPickupPoint() {
		return pickupPoint;
	}
	public void setPickupPoint(Long pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	public Long getDropPoint() {
		return dropPoint;
	}
	public void setDropPoint(Long dropPoint) {
		this.dropPoint = dropPoint;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public Long getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

    
}

