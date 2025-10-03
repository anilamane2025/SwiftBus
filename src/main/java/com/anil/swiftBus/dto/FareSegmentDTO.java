package com.anil.swiftBus.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class FareSegmentDTO {

    private Long fareSegmentId;

    @NotNull(message = "Route is required")
    private Long routeId;

    @NotNull(message = "From stop is required")
    private Long fromRouteStopId;

    @NotNull(message = "To stop is required")
    private Long toRouteStopId;

    @NotNull(message = "Fare amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fare must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid fare format")
    private BigDecimal fareAmount;
    
    private boolean enabled = true;
    
    private String toStopName; 
    
    private int toStopOrder;

    public Long getFareSegmentId() {
        return fareSegmentId;
    }

    public void setFareSegmentId(Long fareSegmentId) {
        this.fareSegmentId = fareSegmentId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
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

    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getToStopName() {
		return toStopName;
	}

	public void setToStopName(String toStopName) {
		this.toStopName = toStopName;
	}

	public int getToStopOrder() {
		return toStopOrder;
	}

	public void setToStopOrder(int toStopOrder) {
		this.toStopOrder = toStopOrder;
	}

	
	
    
    
}
