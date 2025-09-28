package com.anil.swiftBus.entity;

import javax.persistence.*;

@Entity
@Table(name = "bus_seat",uniqueConstraints = @UniqueConstraint(columnNames = {"bus_id", "seat_number"}))
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_seat_id")
    private Long busSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "seat_number", nullable = false, length = 20)
    private String seatNumber;

    @Column(name = "seat_type", length = 50)
    private String seatType; 
    // 'LOWER','UPPER','SEATER','SLEEPER','WINDOW','AISLE','MIDDLE'

    @Column(name = "seat_row")
    private int seatRow;

    @Column(name = "seat_col")
    private int seatCol;

    @Column(name = "extra_info", length = 200)
    private String extraInfo;

	public Long getBusSeatId() {
		return busSeatId;
	}

	public void setBusSeatId(Long busSeatId) {
		this.busSeatId = busSeatId;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
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

    
}
