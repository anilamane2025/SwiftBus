package com.anil.swiftBus.dto;

import javax.validation.constraints.NotBlank;

public class BusSeatDTO {

    private Long busSeatId;
    @NotBlank(message = "Seat number cannot be empty")
    private String seatNumber;
    private String seatType;
    private int seatRow;
    private int seatCol;
    private String extraInfo;
    private boolean booked = false;
	public Long getBusSeatId() {
		return busSeatId;
	}
	public void setBusSeatId(Long busSeatId) {
		this.busSeatId = busSeatId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public int getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}
	public int getSeatCol() {
		return seatCol;
	}
	public void setSeatCol(int seatCol) {
		this.seatCol = seatCol;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	public boolean isBooked() {
		return booked;
	}
	public void setBooked(boolean booked) {
		this.booked = booked;
	}

    
}
