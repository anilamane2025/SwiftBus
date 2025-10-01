package com.anil.swiftBus.dto;

import com.anil.swiftBus.enums.StopPointType;

public class RouteStopPointDTO {
    private Long routeStopPointId;
    private Long routeStopId; // reference to parent stop
    private String pointName;
    private StopPointType pointType; // PICKUP / DROP
    private String landmark;
    private boolean enabled;
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
	public StopPointType getPointType() {
		return pointType;
	}
	public void setPointType(StopPointType pointType) {
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
