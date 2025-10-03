package com.anil.swiftBus.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RouteDTO {

    private Long routeId;

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotNull(message = "Distance is required")
    @Min(value = 10, message = "Distance must be at least 10 km")
    private int distanceKm;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled = true;

    @Valid
    @NotEmpty(message = "At least two stop is required")
    private List<RouteStopDTO> stops = new ArrayList<>();

    // Getters & Setters
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RouteStopDTO> getStops() {
        return stops;
    }

    public void setStops(List<RouteStopDTO> stops) {
        this.stops = stops;
    }
}
