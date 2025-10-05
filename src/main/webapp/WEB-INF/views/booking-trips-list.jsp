<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<style>

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
<body>

<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">

    <!-- Page Title -->
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Trip List</h6>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <a href="<c:url value='/admin/home'/>"
                   class="d-flex align-items-center gap-1 hover-text-primary">
                   <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                   Dashboard
                </a>
            </li>
            <li>-</li>
            <li class="fw-medium">Bus Booking Ticket</li>
        </ul>
    </div>
<sec:authorize access="hasAuthority('TRIP_VIEW')">
    

    <!-- Trips Table -->
    <div class="card basic-data-table">
        

        <div class="card-body">
            <table class="table bordered-table mb-0" id="dataTable" data-page-length="10">
                <thead>
                    <tr>
                        <th>Bus</th>
                        <th>Route</th>
                        <th>Service Date</th>
                        <th>Departure</th>
                        <th>Arrival</th>
                        <th>Status</th>
                        <th>View</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trip" items="${trips}">
                        <tr>
                            <td>${trip.busName}</td>
                            <td>${trip.routeName}</td>
                            <td>${trip.serviceDate}</td>
                            <td>${trip.departureDatetime}</td>
                            <td>${trip.arrivalDatetime}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${trip.status == 'SCHEDULED'}">
                                        <span class="badge bg-success">Scheduled</span>
                                    </c:when>
                                    <c:when test="${trip.status == 'CANCELLED'}">
                                        <span class="badge bg-danger">Cancelled</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${trip.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                               <div class="mt-3">
										<button class="view-seats btn btn-primary btn-sm"
								        data-trip-id="${trip.tripId}"
								        data-bus-id="${trip.busId}">
								    View Seats
								</button>
										
                                </div>
                                </div>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
<div id="seat-container-${trip.id}" style="margin-top: 20px;"></div>

        </div>
    </div>
    </sec:authorize>
</div>

<jsp:include page="templates/footer.jsp" />
<jsp:include page="templates/scripts.jsp" />
<script>
$('.remove-button').on('click', function () {
    $(this).closest('.alert').addClass('d-none')
});
    let table = new DataTable('#dataTable');
    
   
</script>
<script>
$(document).on('click', '.view-seats', function() {
    const tripId = $(this).data('trip-id');
    const busId = $(this).data('bus-id');
    const container = $('#seat-container-' + tripId);

    // Toggle container visibility if already loaded
    if(container.is(':visible')) {
        container.slideUp();
        return;
    }

    container.html('<p>Loading seats...</p>').slideDown();

    $.getJSON('/admin/api/trip/' + tripId + '/bus/' + busId + '/seats')
        .done(function(bus) {
            container.empty();

            var html = '<div class="legendItem">';

            // Seater layout
            if(bus.seatLayoutVersion === 'seater') {
                html += '<div class="deck-wrapper" style="grid-template-columns: repeat(6, 55px);">';
                bus.seats.forEach(function(seat){
                    if(!seat.seatNumber.startsWith('lobby')) {
                        html += '<div class="seat seater ' + (seat.booked ? 'booked':'') +
                                '" id="seat-' + seat.seatNumber + '" data-seat="' + seat.busSeatId + 
                                '" data-price="' + bus.price + '" onclick="selectSeat(\'' + seat.seatNumber + '\')">' +
                                seat.seatNumber +
                                '<div class="tooltip-box">' + bus.price + '₹ (' + seat.seatType + ' seat)</div>' +
                                '</div>';
                    } else {
                        html += '<div></div>';
                    }
                });
                html += '</div>';
            }

            // 2x1 or 2x2 layout
            if(bus.seatLayoutVersion === '2x1' || bus.seatLayoutVersion === '2x2') {
                var columns = (bus.seatLayoutVersion === '2x1') ? 4 : 5;
                ['lower', 'upper'].forEach(function(deckType){
                    html += '<div style="display:flex; gap:20px;">';
                    html += '<div><strong>' + deckType.charAt(0).toUpperCase() + deckType.slice(1) + ' Deck</strong>';
                    html += '<div class="deck-wrapper" style="grid-template-columns: repeat(' + columns + ',55px)">';
                    bus.seats.forEach(function(seat){
                        if(seat.seatType === deckType) {
                            if(!seat.seatNumber.startsWith('lobby')) {
                                html += '<div class="seat sleeper ' + (seat.booked ? 'booked':'') +
                                        '" id="seat-' + seat.seatNumber + '" data-seat="' + seat.busSeatId +
                                        '" data-price="' + bus.price + '" onclick="selectSeat(\'' + seat.seatNumber + '\')">' +
                                        seat.seatNumber +
                                        '<div class="tooltip-box">' + bus.price + '₹ (' + seat.seatType + ' seat)</div>' +
                                        '</div>';
                            } else {
                                html += '<div></div>';
                            }
                        }
                    });
                    html += '</div></div></div>';
                });
            }

            // Legend
            html += '<div class="mt-3">' +
                    '<span class="seat seater" style="margin-right:5px;"></span> Available' +
                    '<span class="seat seater booked" style="margin-left:15px;"></span> Booked' +
                    '<span class="seat seater selected" style="margin-left:15px;"></span> Selected' +
                    '</div>';

            html += '</div>';

            container.html(html);
        })
        .fail(function() {
            container.html('<p style="color:red;">Failed to load seats</p>');
        });
});
</script>


</body>
</html>
