package com.anil.swiftBus.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "route_stop",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"route_id", "stop_name"})
    })
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_stop_id")
    private Long routeStopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "stop_name", length = 150)
    private String stopName;

    @Column(name = "stop_order")
    private int stopOrder;

    @Column(name = "distance_from_origin_km")
    private Long distanceFromOriginKm;

    @Column(name = "minutes_from_start")
    private Integer minutesFromStart;

    @Column(name = "enabled")
    private boolean enabled = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "routeStop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RouteStopPoint> stopPoints;
    
    @OneToMany(mappedBy = "fromRouteStop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FareSegment> faresFrom;
    
    @OneToMany(mappedBy = "toRouteStop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FareSegment> faresTo;

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

    // getters & setters
    public Long getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(Long routeStopId) {
        this.routeStopId = routeStopId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

	public List<RouteStopPoint> getStopPoints() {
		return stopPoints;
	}

	public void setStopPoints(List<RouteStopPoint> stopPoints) {
		this.stopPoints = stopPoints;
	}

	public List<FareSegment> getFaresFrom() {
		return faresFrom;
	}

	public void setFaresFrom(List<FareSegment> faresFrom) {
		this.faresFrom = faresFrom;
	}

	public List<FareSegment> getFaresTo() {
		return faresTo;
	}

	public void setFaresTo(List<FareSegment> faresTo) {
		this.faresTo = faresTo;
	}

	
	
}
