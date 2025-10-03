<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
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
            <li class="fw-medium">Trips</li>
        </ul>
    </div>
<sec:authorize access="hasAuthority('TRIP_VIEW')">
    <c:if test="${not empty success}">
                <div class="alert alert-success bg-success-100 text-success-600 border-success-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between"
                     role="alert">
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
                <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between"
                     role="alert">
                    <div class="d-flex align-items-center gap-2">
                        <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                        ${error}
                    </div>
                    <button class="remove-button text-danger-600 text-xxl line-height-1">
                        <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                    </button>
                </div>
            </c:if>

    <!-- Trips Table -->
    <div class="card basic-data-table">
        <div class="card-header border-bottom bg-base d-flex align-items-center flex-wrap justify-content-between">
            <div></div>
            <sec:authorize access="hasAuthority('TRIP_ADD')">
                <a href="<c:url value='/admin/trips/add'/>"
                   class="btn btn-primary text-sm btn-sm d-flex align-items-center">
                    <iconify-icon icon="ic:baseline-plus" class="icon text-xl"></iconify-icon>
                    Add New Trip
                </a>
            </sec:authorize>
        </div>

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
                        <th>Action</th>
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
                                <c:if test="${trip.status == 'SCHEDULED'}">
                                    <sec:authorize access="hasAuthority('TRIP_EDIT')">
                                        <a href="<c:url value='/admin/trips/edit/${trip.tripId}'/>"
                                           class="bg-info-focus bg-hover-info-200 text-info-600 fw-medium w-40-px h-40-px d-flex justify-content-center align-items-center rounded-circle">
                                           <iconify-icon icon="majesticons:eye-line" class="icon text-xl"></iconify-icon>
                                        </a>
                                    </sec:authorize>
                                    
                                    <sec:authorize access="hasAuthority('TRIP_DELETE')">
                                        <a href="<c:url value='/admin/trips/cancel/${trip.tripId}'/>"
                                           onclick="return confirm('Cancel this trip? Tickets will remain but trip will be marked cancelled.');"
                                           class="w-40-px h-40-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
                                           <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
	                                                </a>
                                    </sec:authorize>
                                    </c:if>
                                </div>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
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
</body>
</html>
