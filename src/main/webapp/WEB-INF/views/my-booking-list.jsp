<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="templates/head.jsp" />

<body>
<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">

    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">My Bookings 
        		</h6>
        		<a href="<c:url value='/booking'/>"
                   class="btn rounded-pill btn-info-100 text-info-600 radius-8 d-flex align-items-center gap-1 ">
                   
                   Book Ticket Now.
                </a>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <a href="<c:url value='/dashboard'/>"
                   class="d-flex align-items-center gap-1 hover-text-primary">
                   <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                   Dashboard
                </a>
            </li>
            <li>-</li>
            <li class="fw-medium">My Booking Ticket</li>
        </ul>
        
    </div>

<c:if test="${not empty success}">
            <div class="alert alert-success bg-success-100 text-success-600 border-success-600 border-start-width-4-px px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                <div class="d-flex align-items-center gap-2">
                    <iconify-icon icon="akar-icons:double-check" class="icon text-xl"></iconify-icon>
                    ${success}
                </div>
                <button class="remove-button text-success-600 text-xxl line-height-1">
                    <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 border-start-width-4-px px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                <div class="d-flex align-items-center gap-2">
                    <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                    ${error}
                </div>
                <button class="remove-button text-danger-600 text-xxl line-height-1">
                    <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                </button>
            </div>
        </c:if>
    <!-- Tabs for Booking Status -->
    <ul class="nav nav-tabs" id="bookingTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="confirmed-tab" data-bs-toggle="tab" data-bs-target="#confirmed" type="button">Confirmed</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="cancelled-tab" data-bs-toggle="tab" data-bs-target="#cancelled" type="button">Cancelled</button>
        </li>
       <li class="nav-item" role="presentation">
            <button class="nav-link" id="completed-tab" data-bs-toggle="tab" data-bs-target="#completed" type="button">Completed</button>
        </li>
       
    </ul>

    <div class="tab-content mt-3">
        <!-- CONFIRMED -->
        <div class="tab-pane fade show active" id="confirmed">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Passenger</th>
                        <th>Booking Time</th>
                        <th>Total</th>
                        <th>Trip Status</th>
                        <th>Payment Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${bookings}"  varStatus="status">
                        <c:if test="${b.bookingStatus == 'CONFIRMED'}">
                            <tr>
                                <td>${b.bookingId}</td>
                                <td>${b.passengerName} (${b.passengerGender == 'M' ? 'Male' : 'Female'}, Age: ${b.passengerAge})</td>
                                <td>${b.bookingTimeFormat}</td>
                                <td>₹${b.totalAmount}</td>
                                <td>${b.tripStatus}</td>
                                <td>${b.paymentStatus}</td>
                                <td>
                                <div class="d-flex gap-2">
                                <button class="btn btn-sm mb-2 btn-primary view-details" data-index="${status.index}">View</button>
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
						        </div>
						        </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- CANCELLED -->
        <div class="tab-pane fade" id="cancelled">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Passenger</th>
                        <th>Booking Time</th>
                        <th>Total</th>
                        <th>Trip Status</th>
                        <th>Payment Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${bookings}"  varStatus="status">
                        <c:if test="${b.bookingStatus eq 'CANCELLED'}">
                            <tr>
                                <td>${b.bookingId}</td>
                                <td>${b.passengerName} (${b.passengerGender == 'M' ? 'Male' : 'Female'}, Age: ${b.passengerAge})</td>
                                <td>${b.bookingTimeFormat}</td>
                                <td>₹${b.totalAmount}</td>
                                <td>${b.tripStatus}</td>
                                <td>${b.paymentStatus}</td>
                                <td>
                                <div class="d-flex gap-2">
                                <button class="btn btn-sm mb-2 btn-primary view-details" data-index="${status.index}">View</button>
                                
						        </div>
						        </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- COMPLETED -->
        <div class="tab-pane fade" id="completed">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Passenger</th>
                        <th>Booking Time</th>
                        <th>Total</th>
                        <th>Trip Status</th>
                        <th>Payment Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${bookings}"  varStatus="status">
                        <c:if test="${b.bookingStatus eq 'COMPLETED'}">
                            <tr>
                                <td>${b.bookingId}</td>
                                <td>${b.passengerName} (${b.passengerGender == 'M' ? 'Male' : 'Female'}, Age: ${b.passengerAge})</td>
                                <td>${b.bookingTimeFormat}</td>
                                <td>₹${b.totalAmount}</td>
                                <td>${b.tripStatus}</td>
                                <td>${b.paymentStatus}</td>
                                <td>
                                 <div class="d-flex gap-2">
                                <button class="btn btn-sm mb-2 btn-primary view-details" data-index="${status.index}">View</button>
                                </div>
						        </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Booking Details Modal -->
    <div class="modal fade" id="bookingDetailsModal" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Booking Details</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <table class="table">
                <tr><th>From → To</th><td id="fromTo"></td></tr>
                <tr><th>Pickup → Drop</th><td id="stopPointfromTo"></td></tr>
                <tr><th>Bus</th><td id="busName"></td></tr>
                <tr><th>Seats</th><td id="seats"></td></tr>
                <tr><th>Trip Date</th><td id="tripDate"></td></tr>
                <tr><th>Trip Status</th><td id="tripStatus"></td></tr>
                <tr><th>Departure Time</th><td id="fromDepartureTime"></td></tr>
                <tr><th>Arrival Time</th><td id="toArrivalTime"></td></tr>
                <tr><th>Duration</th><td id="duration"></td></tr>
            </table>
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
        bookingStatus: '${b.bookingStatus}',
        paymentStatus: '${b.paymentStatus}',
        fromStopName: '${b.fromStopName}',
        toStopName: '${b.toStopName}',
        fromStopPointName: '${b.fromStopPointName}',
        toStopPointName: '${b.toStopPointName}',
        busName: '${b.busName}',
        seatNumbers: '${b.seatNumbers}',
        serviceDate: '${b.serviceDate}',
        tripStatus: '${b.tripStatus}',
        fromDepartureTime: '${b.fromDepartureTime}',
        toArrivalTime: '${b.toArrivalTime}',
        duration: '${b.duration}'
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
];

$(document).on('click', '.view-details', function() {
    var index = $(this).data('index');
    var b = bookingsData[index];

    $('#fromTo').text(b.fromStopName + ' → ' + b.toStopName);
    $('#stopPointfromTo').text(b.fromStopPointName + ' → ' + b.toStopPointName);
    $('#busName').text(b.busName);
    $('#seats').text(b.seatNumbers);
    $('#tripDate').text(b.serviceDate);
    $('#tripStatus').text(b.tripStatus);
    $('#fromDepartureTime').text(b.fromDepartureTime);
    $('#toArrivalTime').text(b.toArrivalTime);
    $('#duration').text(b.duration);

    $('#bookingDetailsModal').modal('show');
});

$('.remove-button').on('click', function () {
    $(this).closest('.alert').addClass('d-none')
});
    let table = new DataTable('#dataTable');
</script>
</body>
</html>
