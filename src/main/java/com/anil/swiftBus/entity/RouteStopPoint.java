package com.anil.swiftBus.entity;

import javax.persistence.*;

import com.anil.swiftBus.enums.StopPointType;

import java.time.LocalDateTime;

@Entity
@Table(name = "route_stop_point")
public class RouteStopPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_stop_point_id")
    private Long routeStopPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_stop_id", nullable = false)
    private RouteStop routeStop;

    @Column(name = "point_name", nullable = false, length = 150)
    private String pointName;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false, length = 10)
    private StopPointType pointType;  
    // values: PICKUP, DROP

    @Column(name = "landmark", length = 200)
    private String landmark;

    @Column(name = "enabled")
    private boolean enabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getRouteStopPointId() {
        return routeStopPointId;
    }

    public void setRouteStopPointId(Long routeStopPointId) {
        this.routeStopPointId = routeStopPointId;
    }

    public RouteStop getRouteStop() {
        return routeStop;
    }

    public void setRouteStop(RouteStop routeStop) {
        this.routeStop = routeStop;
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
