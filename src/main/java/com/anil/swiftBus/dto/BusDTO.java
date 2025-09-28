package com.anil.swiftBus.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BusDTO {

    private Long busId;
    @NotBlank(message = "Registration No is required")
    private String registrationNo;
    @NotBlank(message = "Bus name is required")
    private String busName;
    @NotBlank(message = "Bus type is required")
    private String busType;
    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;
    @NotBlank(message = "Seat layout is required")
    private String seatLayoutVersion;
    private LocalDateTime createdAt;
    private boolean enabled;
    @Valid
    private List<BusSeatDTO> seats= new ArrayList<>();;

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public String getSeatLayoutVersion() {
		return seatLayoutVersion;
	}

	public void setSeatLayoutVersion(String seatLayoutVersion) {
		this.seatLayoutVersion = seatLayoutVersion;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<BusSeatDTO> getSeats() {
		return seats;
	}

	public void setSeats(List<BusSeatDTO> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "BusDTO [busId=" + busId + ", registrationNo=" + registrationNo + ", busName=" + busName + ", busType="
				+ busType + ", totalSeats=" + totalSeats + ", seatLayoutVersion=" + seatLayoutVersion + ", createdAt="
				+ createdAt + ", enabled=" + enabled + ", seats=" + seats + "]";
	}

    
}
