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
        <h6 class="fw-semibold mb-0">Booking List</h6>
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
    

    <!-- Trips Table -->
    <div class="card basic-data-table">
        

        <div class="card-body">
          <table class="table bordered-table mb-0" id="dataTable" data-page-length="10">
			    <thead>
			        <tr>
			            <th>Booking ID</th>
			            <th>Passenger</th>
			            <th>Booking Time</th>
			            <th>Total Amount</th>
			            <th>Booked By</th>
			            <th>Status</th>
			            <th>Payment Status</th>
			            <th>Action</th> <!-- for "view details" -->
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach var="b" items="${bookings}" varStatus="status">
			            <tr>
			                <td>${b.bookingId}</td>
			                <td>${b.passengerName} (${b.passengerGender == 'M' ? 'Male' : 'Female'}, Age: ${b.passengerAge})</td>
			                <td>${b.bookingTimeFormat}</td>
			                <td>₹${b.totalAmount}</td>
			                <td>
			                    <c:choose>
			                        <c:when test="${b.bookedByAgent}">Agent: ${b.bookedByName}</c:when>
			                        <c:otherwise>Passenger: ${b.bookedByName}</c:otherwise>
			                    </c:choose>
			                    <br/>
			                    ${b.bookedByPhone}
			                </td>
			                <td>${b.bookingStatus}</td>
			                <td>${b.paymentStatus} </td>
			                <td>
			                <div class="d-flex gap-2">
								  <button class="btn btn-sm mb-2 btn-primary view-details" data-index="${status.index}">
								    View Details
								  </button>
								  <c:if test="${b.bookingStatus == 'CONFIRMED'}">
								  <c:choose>
	                                	<c:when test="${b.tripStatus == 'SCHEDULED'}">
												<div class="mb-2">
												<form action="/booking/cancel" method="post">
												<input type="hidden" name="bookingId" value="${b.bookingId}"/>
													<button type="submit" class="btn btn-primary btn-sm mb-2">Cancel Ticket</button>
												</form>
												</div>
										</c:when>
							        </c:choose>
								  </c:if>
								  </div>
							</td>

			            </tr>
			        </c:forEach>
			    </tbody>
			</table>
				<div class="modal fade" id="bookingDetailsModal" tabindex="-1" role="dialog">
				  <div class="modal-dialog modal-lg" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title">Booking Details</h5>
				      </div>
				      <div class="modal-body">
				        <table class="table">
				            <tr><th>From → To</th><td id="fromTo"></td></tr>
				            <tr><th>Pickup → Drop Point</th><td id="stopPointfromTo"></td></tr>
				            <tr><th>Bus</th><td id="busName"></td></tr>
				            <tr><th>Bus No.</th><td id="registrationNo"></td></tr>
				            <tr><th>Seats</th><td id="seats"></td></tr>
				            <tr><th>Trip Date</th><td id="tripDate"></td></tr>
				            <tr><th>Trip Status</th><td id="tripStatus"></td></tr>
				            <tr><th>Departure time</th><td id="fromDepartureTime"></td></tr>
				            <tr><th>Arrival Time</th><td id="toArrivalTime"></td></tr>
				            <tr><th>Duration</th><td id="duration"></td></tr>
				        </table>
				      </div>
				    </div>
				  </div>
				</div>

        </div>
    </div>
</div>

<jsp:include page="templates/footer.jsp" />
<jsp:include page="templates/scripts.jsp" />
<script>
  var bookingsData = [
    <c:forEach var="b" items="${bookings}" varStatus="status">
      {
        bookingId: ${b.bookingId},
        passengerName: '${b.passengerName}',
        passengerAge: ${b.passengerAge},
        passengerGender: '${b.passengerGender}',
        bookingTimeFormat: '${b.bookingTimeFormat}',
        totalAmount: ${b.totalAmount},
        bookedByAgent: ${b.bookedByAgent},
        bookedByName: '${b.bookedByName}',
        bookedByPhone: '${b.bookedByPhone}',
        fromStopName: '${b.fromStopName}',
        toStopName: '${b.toStopName}',
        fromStopPointName: '${b.fromStopPointName}',
        toStopPointName: '${b.toStopPointName}',
        bookingStatus: '${b.bookingStatus}',
        paymentStatus: '${b.paymentStatus}',
        busName: '${b.busName}',
        registrationNo: '${b.registrationNo}',
        seatNumbers: '${b.seatNumbers}',
        serviceDate: '${b.serviceDate}',
        tripStatus: '${b.tripStatus}',
        fromDepartureTime: '${b.fromDepartureTime}',
        toArrivalTime: '${b.toArrivalTime}',
        duration: '${b.duration}'
      }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];
</script>


<script>
$('.remove-button').on('click', function () {
    $(this).closest('.alert').addClass('d-none')
});
    let table = new DataTable('#dataTable');

    $(document).on('click', '.view-details', function() {
        var index = $(this).data('index');
        var b = bookingsData[index];
    
        $('#fromTo').text(b.fromStopName +  ' → ' + b.toStopName);
        $('#stopPointfromTo').text(b.fromStopPointName+ ' → ' + b.toStopPointName);
        $('#busName').text(b.busName);
        $('#registrationNo').text(b.registrationNo);
        $('#seats').text(b.seatNumbers);
        $('#tripDate').text(b.serviceDate);
        $('#tripStatus').text(b.tripStatus);
        $('#fromDepartureTime').text(b.fromDepartureTime);
        $('#toArrivalTime').text(b.toArrivalTime);
        $('#duration').text(b.duration);

        $('#bookingDetailsModal').modal('show');
    });
</script>


</body>
</html>
