package com.anil.swiftBus.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RouteStopPointDTO {
    private Long routeStopPointId;
    @NotNull(message = "Route Stop ID is required")
    private Long routeStopId;
    @NotBlank(message = "Point name is required")
    @Size(max = 100, message = "Point name must not exceed 100 characters")
    private String pointName;
    
    @NotBlank(message = "Point name is required")
    private String pointType; // PICKUP / DROP
    
    @NotBlank(message = "Landmark is required")
    @Size(max = 200, message = "Landmark must not exceed 200 characters")
    private String landmark;
    private boolean enabled = true;
	public Long getRouteStopPointId() {
		return routeStopPointId;
	}
	public void setRouteStopPointId(Long routeStopPointId) {
		this.routeStopPointId = routeStopPointId;
	}
	public Long getRouteStopId() {
		return routeStopId;
	}
	public void setRouteStopId(Long routeStopId) {
		this.routeStopId = routeStopId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    
}
