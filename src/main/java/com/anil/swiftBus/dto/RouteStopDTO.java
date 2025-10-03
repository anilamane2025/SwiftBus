package com.anil.swiftBus.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RouteStopDTO {

    private Long routeStopId;

    //@NotNull(message = "Route ID is required")
    private Long routeId;

    @NotNull(message = "City ID is required")
    private Long cityId;
    
    private String cityName;  
    
    @NotBlank(message = "State is required")
    private String cityState;

    @NotBlank(message = "Stop name is required")
    private String stopName;

    @Min(value = 1, message = "Stop order must be at least 1")
    private int stopOrder;

    @NotNull(message = "Distance is required")
    @Min(value = 0, message = "Distance cannot be negative")
    private Long distanceFromOriginKm;
    
    @NotNull(message = "Minutes from start is required")
    @Min(value = 0, message = "Minutes cannot be negative")
    private Integer minutesFromStart;
    
    private boolean enabled = true;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private List<RouteStopPointDTO> stopPoints;
    
    private List<FareSegmentDTO> fares;

    // Getters & Setters
    public Long getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(Long routeStopId) {
        this.routeStopId = routeStopId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    
    public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public Long getDistanceFromOriginKm() {
        return distanceFromOriginKm;
    }

    public void setDistanceFromOriginKm(Long distanceFromOriginKm) {
        this.distanceFromOriginKm = distanceFromOriginKm;
    }

    public Integer getMinutesFromStart() {
		return minutesFromStart;
	}

	public void setMinutesFromStart(Integer minutesFromStart) {
		this.minutesFromStart = minutesFromStart;
	}
	
	

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public List<RouteStopPointDTO> getStopPoints() {
		return stopPoints;
	}

	public void setStopPoints(List<RouteStopPointDTO> stopPoints) {
		this.stopPoints = stopPoints;
	}

	public List<FareSegmentDTO> getFares() {
		return fares;
	}

	public void setFares(List<FareSegmentDTO> fares) {
		this.fares = fares;
	}
    
}
