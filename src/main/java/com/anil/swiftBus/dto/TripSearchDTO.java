package com.anil.swiftBus.dto;

import java.util.List;

public class TripSearchDTO {
	 private Long id;
	    private Long busId;
	    private String busName;         // e.g., "Pavan"
	    private String busType;         // "AC Sleeper", "AC Seater", "Sleeper", "Seater"
	    private String departureTime;   // "11:15 PM"
	    private String arrivalTime;     // "05:45 AM"
	    private String duration;        // "06h 30m"
	    private int availableSeats;
	    private double price;
	    private List<RouteStopPointDTO> pickupPoints;
	    private List<RouteStopPointDTO> dropPoints;
	    private List<RouteStopDTO> routeStops;
	    private String routeName;
	    private Long fareSegmentId;
	    private String fromName;
	    private String toName;
	    private Long fromRouteStopId;
	    private Long toRouteStopId;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		public Long getBusId() {
			return busId;
		}
		public void setBusId(Long busId) {
			this.busId = busId;
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
		public String getDepartureTime() {
			return departureTime;
		}
		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}
		public String getArrivalTime() {
			return arrivalTime;
		}
		public void setArrivalTime(String arrivalTime) {
			this.arrivalTime = arrivalTime;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public int getAvailableSeats() {
			return availableSeats;
		}
		public void setAvailableSeats(int availableSeats) {
			this.availableSeats = availableSeats;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public List<RouteStopPointDTO> getPickupPoints() {
			return pickupPoints;
		}
		public void setPickupPoints(List<RouteStopPointDTO> pickupPoints) {
			this.pickupPoints = pickupPoints;
		}
		public List<RouteStopPointDTO> getDropPoints() {
			return dropPoints;
		}
		public void setDropPoints(List<RouteStopPointDTO> dropPoints) {
			this.dropPoints = dropPoints;
		}
		public List<RouteStopDTO> getRouteStops() {
			return routeStops;
		}
		public void setRouteStops(List<RouteStopDTO> routeStops) {
			this.routeStops = routeStops;
		}
		public String getRouteName() {
			return routeName;
		}
		public void setRouteName(String routeName) {
			this.routeName = routeName;
		}
		public Long getFareSegmentId() {
			return fareSegmentId;
		}
		public void setFareSegmentId(Long fareSegmentId) {
			this.fareSegmentId = fareSegmentId;
		}
		public String getFromName() {
			return fromName;
		}
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		public String getToName() {
			return toName;
		}
		public void setToName(String toName) {
			this.toName = toName;
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
		
	    
}
