<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Seat Selection - ${trip.busName}</title>
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/bootstrap.min.css'/>" />
<link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>" />
<style>
/* Your existing CSS (unchanged) */
body {
	background: #f3f7fb;
}

.search-summary, .promo-strip, .bus-card, .legendContainer {
	background: #fff;
	padding: 16px;
	border-radius: 8px;
	margin-bottom: 12px;
}

.promo-strip {
	display: flex;
	gap: 10px;
	overflow: auto;
	padding: 8px;
}

.bus-card {
	justify-content: center;
	align-items: center;
	padding: 14px;
}

.bus-price {
	font-weight: 700;
	color: #0f62e3;
	font-size: 1.15rem;
}

.select-seat {
	background: #0077d9;
	color: #fff;
	padding: 8px 12px;
	border-radius: 20px;
	text-decoration: none;
}

.tab-content {
	background: #f7fbff;
	padding: 10px;
	border-radius: 8px;
	margin-top: 10px;
}

.pickup-list, .drop-list {
	list-style: none;
	padding: 0;
	margin: 0;
	max-height: 160px;
	overflow: auto;
}

.pickup-list li, .drop-list li {
	padding: 6px 0;
	border-bottom: 1px dashed #e8f0fb;
}

.result-count {
	font-weight: 600;
}

.bus-route {
	color: #333;
	font-size: 0.9rem;
	margin-top: 6px;
}

.bus-route b {
	color: #0077d9;
}

.deck-wrapper {
	display: grid;
	gap: 5px;
	margin-bottom: 20px;
}

.seat {
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 6px;
	width: 55px;
	height: 55px;
	cursor: pointer;
	position: relative;
	font-size: 10px;
	padding: 2px;
}

.seat.sleeper {
	width: 32px;
	height: 70px;
}

.seat .seat-price {
	position: absolute;
	bottom: 8px;
	font-size: 7px;
	color: #333;
	border-radius: 3px;
	padding: 1px 4px;
}

.seat .tooltip-box {
	background: #333;
	color: #fff;
	padding: 5px;
	border-radius: 3px;
	font-size: 8px;
	position: absolute;
	top: -20px;
	left: 50%;
	transform: translateX(-50%);
	display: none;
	white-space: nowrap;
	z-index: 999;
}

.seat:hover .tooltip-box {
	display: block;
}

.legendTitle {
	font-weight: bold;
	margin-bottom: 10px;
}

.legendItem {
	display: flex;
	justify-content: center;
	margin: 5px 0;
	font-size: 12px;
}

.legendItem img {
	width: 25px;
	height: auto;
}

#action-buttons {
	margin-top: 20px;
}

#action-buttons button {
	padding: 10px 20px;
	background: #007bff;
	border: none;
	color: #fff;
	border-radius: 5px;
	cursor: pointer;
}

#action-buttons button:hover {
	background: #0056b3;
}
/* Seat types background images */
.seat.seater {
	width: 55px;
	height: 55px;
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/seater_available.svg");
	background-size: cover;
}

.seat.sleeper {
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/sl_available.svg");
	background-size: cover;
}

.seat.seater.selected {
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/seater_selected.svg");
}

.seat.sleeper.selected {
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/sl_selected.svg");
}

.seat.seater.booked {
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/seater_booked.svg");
}

.seat.sleeper.booked {
	background-image:
		url("https://s3.rdbuz.com/Images/seat/newLegends/responsive/sl_booked.svg");
}
</style>
</head>
<body>
	<header
		class="bg-base shadow-sm px-20 py-12 d-flex align-items-center justify-content-between">
		<a href="<c:url value='/' />" class="d-flex align-items-center"> <img
			src="<c:url value='/assets/images/logo.png' />" alt="SwiftBus Logo"
			height="40">
		</a>
		<nav class="d-flex align-items-center">
			<a href="<c:url value='/' />" class="mx-3 fw-semibold">Home</a> <a
				href="<c:url value='/booking' />" class="mx-3 fw-semibold">Book
				Tickets</a> <a href="<c:url value='/about' />" class="mx-3 fw-semibold">About
				Us</a> <a href="<c:url value='/contact' />" class="mx-3 fw-semibold">Contact</a>
			<div class="ms-4">
				 <security:authorize access="isAnonymous()">
            <a href="<c:url value='/login' />" class="text-primary-600 fw-semibold">Sign In</a> | 
            <a href="<c:url value='/sign-up' />" class="text-primary-600 fw-semibold">Sign Up</a>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
					<div class="dropdown">
						<button
							class="d-flex justify-content-center align-items-center rounded-circle"
							type="button" data-bs-toggle="dropdown">
							<img src="<c:url value='/assets/images/user.png'/>" alt="image"
								class="w-40-px h-40-px object-fit-cover rounded-circle">
						</button>
						<div class="dropdown-menu to-top dropdown-menu-sm">
							<div
								class="py-12 px-16 radius-8 bg-primary-50 mb-16 d-flex align-items-center justify-content-between gap-2">

								<div>
									<h6 class="text-lg text-primary-light fw-semibold mb-2">
										<security:authentication property="principal.fullName" />
									</h6>
									<a href="<c:url value='/dashboard' />"
						class="text-primary-600 fw-semibold">Dashboard</a> 
								</div>
							</div>
						</div>
					</div>
				</security:authorize>
			</div>
		</nav>
	</header>

	<div class="container my-3">
		<div class="row">
			<div class="col-md-9">
			<input type="hidden" id="isAuthenticated"
    value="<security:authorize access='isAuthenticated()'>true</security:authorize>
           <security:authorize access='isAnonymous()'>false</security:authorize>" />


				<c:choose>
					<c:when test="${not empty trip}">

						<!-- Step 1: Seat Selection -->
						<div id="step-seat" class="step active">
							<div class="bus-card" data-price="${trip.price}"
								data-bustype="${fn:escapeXml(trip.busType)}"
								data-seattype="${fn:escapeXml(trip.busName)}">
								<div class="d-flex justify-content-between">
									<div style="flex: 1">
										<div class="d-flex align-items-center gap-3">
											<div>
												<div class="text-muted  medium">
													<b>Bus : </b>${trip.busName} • ${trip.busType}
												</div>
												<div class="text-muted  medium">
													<b>Stop : </b>${trip.fromName} • ${trip.toName}
												</div>
											</div>
										</div>
										<div class="d-flex gap-4 mt-3">
											<div>
												<div class="fw-bold">${trip.departureTime}</div>
												<div class="text-muted small">Departure</div>
											</div>
											<div>
												<div class="fw-bold">${trip.duration}</div>
												<div class="text-muted small">Duration</div>
											</div>
											<div>
												<div class="fw-bold">${trip.arrivalTime}</div>
												<div class="text-muted small">Arrival</div>
											</div>
											<div>
												<div class="fw-bold">${trip.availableSeats}</div>
												<div class="text-muted small">Seats left</div>
											</div>
										</div>
									</div>
									<div style="text-align: right; min-width: 170px;">
										<div class="bus-price">
											₹
											<c:out value="${trip.price}" />
										</div>
										<div class="text-muted small">Per seat</div>
									</div>
								</div>
								<hr class="mt-3" />
								<div class="mt-3 legendContainer">
									<div class="mb-3 legendItem">
										<h6>Select Seat</h6>
									</div>
									<div class="legendItem">
										<!-- Your existing seat layout code (unchanged) -->
										<c:choose>
											<c:when test="${bus.seatLayoutVersion == 'seater'}">
												<div class="deck-wrapper"
													style="grid-template-columns: repeat(6, 55px);">
													<c:forEach var="seat" items="${bus.seats}">
														<c:if test="${!fn:startsWith(seat.seatNumber,'lobby')}">
															<div
																class="seat seater <c:if test='${seat.booked}'>booked</c:if>"
																id="seat-${seat.seatNumber}"
																data-seat="${seat.busSeatId}"
																data-price="${trip.price}"
																onclick="selectSeat('${seat.seatNumber}')">
																${seat.seatNumber}
																<div class="tooltip-box">${trip.price}₹
																	(${seat.seatType} seat)</div>
															</div>
														</c:if>
														<c:if test="${fn:startsWith(seat.seatNumber,'lobby')}">
															<div></div>
														</c:if>
													</c:forEach>
												</div>
											</c:when>


											<c:when test="${bus.seatLayoutVersion == '2x1'}">
												<div style="display: flex; gap: 20px;">
													<!-- Lower Deck -->
													<div>
														<strong>Lower Deck</strong>
														<div class="deck-wrapper"
															style="grid-template-columns: repeat(4, 55px);">
															<c:forEach var="seat" items="${bus.seats}">
																<c:if
																	test="${seat.seatType=='lower' && !fn:startsWith(seat.seatNumber,'lobby')}">
																	<div
																		class="seat sleeper <c:if test='${seat.booked}'>booked</c:if>"
																		id="seat-${seat.seatNumber}"
																		data-seat="${seat.busSeatId}"
																		data-price="${trip.price}"
																		onclick="selectSeat('${seat.seatNumber}')">
																		${seat.seatNumber}
																		<div class="tooltip-box">${trip.price}₹
																			(${seat.seatType} seat)</div>
																	</div>
																</c:if>
																<c:if
																	test="${seat.seatType=='lower' && fn:startsWith(seat.seatNumber,'lobby')}">
																	<div></div>
																</c:if>
															</c:forEach>
														</div>
													</div>

													<!-- Upper Deck -->
													<div>
														<strong>Upper Deck</strong>
														<div class="deck-wrapper"
															style="grid-template-columns: repeat(4, 55px);">
															<c:forEach var="seat" items="${bus.seats}">
																<c:if
																	test="${seat.seatType=='upper' && !fn:startsWith(seat.seatNumber,'lobby')}">
																	<div
																		class="seat sleeper <c:if test='${seat.booked}'>booked</c:if>"
																		id="seat-${seat.seatNumber}"
																		data-seat="${seat.busSeatId}"
																		data-price="${trip.price}"
																		onclick="selectSeat('${seat.seatNumber}')">
																		${seat.seatNumber}
																		<div class="tooltip-box">${trip.price}₹
																			(${seat.seatType} seat)</div>
																	</div>
																</c:if>
																<c:if
																	test="${seat.seatType=='upper' && fn:startsWith(seat.seatNumber,'lobby')}">
																	<div></div>
																</c:if>
															</c:forEach>
														</div>
													</div>
												</div>
											</c:when>

											<c:when test="${bus.seatLayoutVersion == '2x2'}">
												<div style="display: flex; gap: 20px;">
													<!-- Lower Deck -->
													<div>
														<strong>Lower Deck</strong>
														<div class="deck-wrapper"
															style="grid-template-columns: repeat(5, 55px);">
															<c:forEach var="seat" items="${bus.seats}">
																<c:if
																	test="${seat.seatType=='lower' && !fn:startsWith(seat.seatNumber,'lobby')}">
																	<div
																		class="seat sleeper <c:if test='${seat.booked}'>booked</c:if>"
																		id="seat-${seat.seatNumber}"
																		data-seat="${seat.busSeatId}"
																		data-price="${trip.price}"
																		onclick="selectSeat('${seat.seatNumber}')">
																		${seat.seatNumber}
																		<div class="tooltip-box">${trip.price}₹
																			(${seat.seatType} seat)</div>
																	</div>
																</c:if>
																<c:if
																	test="${seat.seatType=='lower' && fn:startsWith(seat.seatNumber,'lobby')}">
																	<div></div>
																</c:if>
															</c:forEach>
														</div>
													</div>

													<!-- Upper Deck -->
													<div>
														<strong>Upper Deck</strong>
														<div class="deck-wrapper"
															style="grid-template-columns: repeat(5, 55px);">
															<c:forEach var="seat" items="${bus.seats}">
																<c:if
																	test="${seat.seatType=='upper' && !fn:startsWith(seat.seatNumber,'lobby')}">
																	<div
																		class="seat sleeper <c:if test='${seat.booked}'>booked</c:if>"
																		id="seat-${seat.seatNumber}"
																		data-seat="${seat.busSeatId}"
																		data-price="${trip.price}"
																		onclick="selectSeat('${seat.seatNumber}')">
																		${seat.seatNumber}
																		<div class="tooltip-box">${trip.price}₹
																			(${seat.seatType} seat)</div>
																	</div>
																</c:if>
																<c:if
																	test="${seat.seatType=='upper' && fn:startsWith(seat.seatNumber,'lobby')}">
																	<div></div>
																</c:if>
															</c:forEach>
														</div>
													</div>
												</div>
											</c:when>
										</c:choose>
									</div>
									<div class="mt-3">
										<div class="accordion" id="acc-${trip.id}">
											<div class="accordion-item">
												<h2 class="accordion-header" id="heading-${trip.id}">
													<button class="accordion-button collapsed btn-sm"
														type="button" data-bs-toggle="collapse"
														data-bs-target="#collapse-${trip.id}"
														aria-expanded="false">View details</button>
												</h2>
												<div id="collapse-${trip.id}"
													class="accordion-collapse collapse"
													data-bs-parent="#acc-${trip.id}">
													<div class="accordion-body">
														<c:if test="${not empty trip.routeStops}">
															<div class="mt-2 text-muted small">
																<strong>Bus route:</strong> <span> <c:forEach
																		var="stop" items="${trip.routeStops}" varStatus="loop">
																		<c:choose>
																			<c:when
																				test="${fn:toLowerCase(stop.stopName) == fn:toLowerCase(trip.fromName) or fn:toLowerCase(stop.stopName) == fn:toLowerCase(trip.toName)}">
																				<b>${stop.stopName}</b>
																			</c:when>
																			<c:otherwise> ${stop.stopName} </c:otherwise>
																		</c:choose>
																		<c:if test="${!loop.last}"> → </c:if>
																	</c:forEach>
																</span>
															</div>
															<hr class="m-2" />
														</c:if>
														<div class="row">
															<div class="col-md-6">
																<strong>Pick-up points</strong>
																<ul class="pickup-list">
																	<c:forEach var="p" items="${trip.pickupPoints}">
																		<li><div class="d-flex justify-content-between">
																				<div>${p.pointName}</div>
																				<div class="text-muted small">${p.landmark}</div>
																			</div></li>
																	</c:forEach>
																</ul>
															</div>
															<div class="col-md-6">
																<strong>Drop-off points</strong>
																<ul class="drop-list">
																	<c:forEach var="d" items="${trip.dropPoints}">
																		<li><div class="d-flex justify-content-between">
																				<div>${d.pointName}</div>
																				<div class="text-muted small">${d.landmark}</div>
																			</div></li>
																	</c:forEach>
																</ul>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
								<!-- Step 2: Pickup/Drop Selection -->
								<div id="step-pickup" class="step" style="display: none;">
								    <c:choose>
								        <c:when test="${not empty trip and not empty trip.pickupPoints}">
								            <div class="row">
								                <!-- Pickup Points -->
								                <div class="col-md-6">
								                    <strong>Pick-up points</strong>
								                    <ul class="pickup-list">
								                        <c:forEach var="p" items="${trip.pickupPoints}">
								                            <li>
								                                <label style="cursor:pointer;">
								                                    <input type="radio" name="pickup" value="${p.routeStopPointId}" class="form-check-input me-2"/>
								                                    <span>${p.pointName}</span><br/>
								                                    <small class="text-muted">${p.landmark}</small>
								                                </label>
								                            </li>
								                        </c:forEach>
								                    </ul>
								                </div>
								
								                <!-- Drop-off Points -->
								                <div class="col-md-6">
								                    <strong>Drop-off points</strong>
								                    <ul class="drop-list">
								                        <c:forEach var="d" items="${trip.dropPoints}">
								                            <li>
								                                <label style="cursor:pointer;">
								                                    <input type="radio" name="drop" value="${d.routeStopPointId}" class="form-check-input me-2"/>
								                                    <span>${d.pointName}</span><br/>
								                                    <small class="text-muted">${d.landmark}</small>
								                                </label>
								                            </li>
								                        </c:forEach>
								                    </ul>
								                </div>
								            </div>
								        </c:when>
								        <c:otherwise>
								            <div class="alert alert-warning mt-3">
								                Pickup and drop-off points are not available for this trip.
								            </div>
								        </c:otherwise>
								    </c:choose>
								</div>

								<!-- Step 3: Passenger Details -->
								<div id="step-passenger" class="step" style="display: none;">
									
									<security:authorize access="isAnonymous()">
             <h3>Please login or register to continue</h3>
			            <div id="authModal" class="col-sm-2" style="display:none; text-align:center;">
			 
			  <button class="form-control m-2 radius-8" onclick="goLogin()">Login</button>
			  <button class="form-control m-2 radius-8" onclick="goRegister()">Register</button>
			</div>
            
            						
            </security:authorize>
            
            <security:authorize access="isAuthenticated()">
					<h6>Passenger Details</h6>
									<c:choose>
								        <c:when test="${empty passenger}">
											<div class="mb-2">
												<p>Passenger Details not found!</p> Please login.
											</div>
									</form>
									</c:when>
								        <c:otherwise>
								            <ul>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Full
										Name</span> <span class="w-70 text-secondary-light fw-medium">:
										${passenger.firstName} ${passenger.lastName}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Email</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${passenger.username}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Phone</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${passenger.phoneNumber}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Gender</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${passenger.gender == 'M' ? 'Male' : 'Female'}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">City</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${passenger.cityName}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">State</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${passenger.cityState}</span></li>
							</ul>
							<form id="passengerForm">
							    <input type="hidden" name="tripId" value="${trip.id}">
							    <input type="hidden" name="busId" value="${trip.busId}">
							    <input type="hidden" name="fareSegmentId" value="${trip.fareSegmentId}">
							    <input type="hidden" name="fromName" value="${trip.fromName}">
							    <input type="hidden" name="toName" value="${trip.toName}">
							    <input type="hidden" name="passengerId" value="${passenger.id}">
							    <input type="hidden" name="agentId" value="">
							    
							    <div class="mb-20">
							        <label>Passenger Name:</label> 
							        <input type="text" name="passengerName" class="form-control radius-8" placeholder="Name" required>
							    </div>
							    <div class="mb-20">
							        <label>Passenger Age:</label> 
							        <input type="number" name="passengerAge" min="1" class="form-control radius-8" placeholder="Age" required>
							    </div>
							    <div class="mb-20">
							        <label class="form-label">Gender</label>
							        <select name="gender" class="form-control radius-8" required>
							            <option value="">Select gender</option>
							            <option value="M">Male</option>
							            <option value="F">Female</option>
							        </select>
							    </div>
							</form>

								        </c:otherwise>
								    </c:choose>
						
				</security:authorize>
								</div>
								
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">No buses found for selected
							route/date.</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<!-- Step Action Buttons -->
		<div id="action-buttons">
			<button id="btn-back" style="display: none;" onclick="prevStep()">Back</button>
			<button id="btn-next" onclick="nextStep()">Next</button>
			<button id="btn-confirm" style="display: none;"
				onclick="confirmBooking()">Confirm</button>
		</div>
	</div>

	<script src="<c:url value='/assets/js/lib/jquery-3.7.1.min.js'/>"></script>
	<script src="<c:url value='/assets/js/lib/bootstrap.bundle.min.js'/>"></script>
	<script>
    // Existing seat selection
    function selectSeat(seatId){
        var seat = document.getElementById("seat-" + seatId);
        console.log("Seat clicked:", seatId);
        if(seat.classList.contains("booked")){
            alert("This seat is already booked.");
            return;
        }
        seat.classList.toggle("selected");
    }

    // Multi-step navigation
    let currentStep = 1;
const totalSteps = 3;

function showStep(){
    // Hide all steps
    for(let i=1;i<=totalSteps;i++){
        const step = document.getElementById("step-" + (i===1?"seat":i===2?"pickup":"passenger"));
        if(step) step.style.display = "none";
    }

    // Show current step
    const current = document.getElementById("step-" + (currentStep===1?"seat":currentStep===2?"pickup":"passenger"));
    if(current) current.style.display = "block";

    // Update buttons
    document.getElementById("btn-back").style.display = (currentStep>1)?"inline-block":"none";
    document.getElementById("btn-next").style.display = (currentStep<totalSteps)?"inline-block":"none";
    document.getElementById("btn-confirm").style.display = (currentStep===totalSteps)?"inline-block":"none";
}

function goLogin(){
  const currentUrl = window.location.pathname + window.location.search;
  window.location.assign("/login?redirect=" + encodeURIComponent(currentUrl));
}

function goRegister(){
  const currentUrl = window.location.pathname + window.location.search;
  window.location.assign("/sign-up?redirect=" + encodeURIComponent(currentUrl));
}

function nextStep() {
    const isAuth = document.getElementById("isAuthenticated").value === "true";

    // seat selection validation
    if (currentStep === 1) {
        const seats = document.querySelectorAll(".seat.selected");
        if (seats.length === 0) {
            alert("Please select at least one seat.");
            return;
        }
    }

    // pickup/drop validation
    if (currentStep === 2) {
        const pickup = document.querySelector("input[name='pickup']:checked");
        const drop = document.querySelector("input[name='drop']:checked");
        if (!pickup || !drop) {
            alert("Please select pickup and drop points.");
            return;
        }

        // Check authentication before moving to passenger details
        if (!isAuth) {
            const authModal = document.getElementById("authModal");
            if (authModal) {
                authModal.style.display = "block";
            }
            document.getElementById("btn-confirm").style.display = "none";
        }

    }

    if (currentStep < totalSteps) {
        currentStep++;
        showStep();
    }
}


function prevStep(){
    if(currentStep > 1){
        currentStep--;
        showStep();
    }
}

// Initial display
showStep();


    function confirmBooking() {
        const seats = Array.from(document.querySelectorAll(".seat.selected")).map(s => s.dataset.seat);
        const pickup = document.querySelector("input[name='pickup']:checked");
        const drop = document.querySelector("input[name='drop']:checked");

        // Basic validations
        if (seats.length === 0) {
            alert("Please select at least one seat.");
            return;
        }
        if (!pickup || !drop) {
            alert("Please select pickup and drop points.");
            return;
        }

        const form = document.getElementById("passengerForm");
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        const formData = Object.fromEntries(new FormData(form).entries());
        formData.selectedSeats = seats;
        formData.pickupPoint = pickup.value;
        formData.dropPoint = drop.value;
        

        console.log("Booking data:", formData);

        // TODO: send to backend using AJAX or form submit
	        
	   
    fetch("/booking/confirm", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData)
    })
        .then(res => res.json())
        .then(res => {
            if (res.status === "SUCCESS") {
                alert("Booking confirmed! Booking ID: " + res.bookingId);
                window.location.href = "/booking/success?bookingId=" + res.bookingId;
            } else {
                alert("Booking failed: " + res.message);
            }
        })
        .catch(err => alert("Error: " + err));
    }

</script>
</body>
</html>
