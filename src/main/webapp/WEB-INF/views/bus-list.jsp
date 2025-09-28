<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<style>
.seat { width:36px; height:80px;  color:#fff; display:flex; align-items:flex-end; justify-content:center; border-radius:6px; }
.seat-s { width:36px; height:33px; }
.seat-sleeper { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/734365e5.svg"); }
.seat-seater { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/584368fd.svg"); }

.deck-wrapper { display: flex; flex-wrap: wrap; gap: 20px; justify-content: start;border: 1px solid rgba(16,16,16,0.13);
    border-radius: 12px;
    background: #fff;
    padding: 15px;
    box-sizing: border-box; }

</style>
<body>
    <jsp:include page="templates/sidebar.jsp" />
    <jsp:include page="templates/header.jsp" />

    <div class="dashboard-main-body">
        <!-- Page Title & Breadcrumb -->
        <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
            <h6 class="fw-semibold mb-0">Bus List</h6>
            <ul class="d-flex align-items-center gap-2">
                <li class="fw-medium">
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value='/admin/home' />"
                           class="d-flex align-items-center gap-1 hover-text-primary">
                           <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                           Dashboard
                        </a>
                    </sec:authorize>
                </li>
                <li>-</li>
                <li class="fw-medium">Bus</li>
            </ul>
        </div>

        <!-- Success/Error messages -->
        <sec:authorize access="hasAuthority('BUS_VIEW')">
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

            <!-- Bus List Table -->
            <div class="card basic-data-table">
                <div class="card-header border-bottom bg-base d-flex align-items-center flex-wrap justify-content-between">
                    <div></div>
                    <sec:authorize access="hasAuthority('BUS_ADD')">
                        <a href="<c:url value='/admin/bus/add'/>"
                           class="btn btn-primary text-sm btn-sm d-flex align-items-center">
                            <iconify-icon icon="ic:baseline-plus" class="icon text-xl"></iconify-icon>
                            Add New Bus
                        </a>
                    </sec:authorize>
                </div>

                <div class="card-body">
                    <table class="table bordered-table mb-0" id="dataTable" data-page-length="10">
                        <thead>
                            <tr>
                                <th scope="col">Bus Name</th>
                                <th scope="col">Registration No</th>
                                <th scope="col">Type</th>
                                <th scope="col">Total Seats</th>
                                <th scope="col">Layout</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="bus" items="${buses}">
                                <tr>
                                    <td>${bus.busName}</td>
                                    <td>${bus.registrationNo}</td>
                                    <td>${bus.busType}</td>
                                    <td>${bus.totalSeats}</td>
                                   <td>
    <c:choose>
        <c:when test="${bus.seatLayoutVersion == 'seater'}">
            <div class="deck-wrapper" style="display:grid;width:60%; grid-template-columns:repeat(6,60px); gap:5px;">
                <c:forEach var="seat" items="${bus.seats}">
                    <c:if test="${!fn:startsWith(seat.seatNumber,'lobby')}">
                        <div class="seat-s seat-seater" style="display:flex;align-items:flex-end;justify-content:center;">
                            ${seat.seatNumber}
                        </div>
                    </c:if>
                    <c:if test="${fn:startsWith(seat.seatNumber,'lobby')}">
                              <div class=""></div>
                            </c:if>
                </c:forEach>
            </div>
        </c:when>

        <c:when test="${bus.seatLayoutVersion == '2x1'}">
            <div style="display:flex; gap:20px;">
                <!-- Lower Deck -->
                <div >
                    <strong>Lower Deck</strong>
                    <div class="deck-wrapper" style="display:grid; grid-template-columns:repeat(4,55px); gap:5px;">
                        <c:forEach var="seat" items="${bus.seats}">
                            <c:if test="${seat.seatType=='lower' && !fn:startsWith(seat.seatNumber,'lobby')}">
                                <div class="seat seat-sleeper">
                                    ${seat.seatNumber}
                                </div>
                            </c:if>
                            <c:if test="${seat.seatType=='lower' && fn:startsWith(seat.seatNumber,'lobby')}">
                              <div class=""></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <!-- Upper Deck -->
                <div>
                    <strong>Upper Deck</strong>
                    <div class="deck-wrapper" style="display:grid; grid-template-columns:repeat(4,55px); gap:5px;">
                        <c:forEach var="seat" items="${bus.seats}">
                            <c:if test="${seat.seatType=='upper' && !fn:startsWith(seat.seatNumber,'lobby')}">
                                <div class="seat seat-sleeper" style="display:flex;align-items:flex-end;justify-content:center;">
                                    ${seat.seatNumber}
                                </div>
                            </c:if>
                            <c:if test="${seat.seatType=='upper' && fn:startsWith(seat.seatNumber,'lobby')}">
                              <div class=""></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:when>

        <c:when test="${bus.seatLayoutVersion == '2x2'}">
            <div style="display:flex; gap:20px;">
                <div>
                    <strong>Lower Deck</strong>
                    <div class="deck-wrapper" style="display:grid; grid-template-columns:repeat(5,55px); gap:5px;">
                        <c:forEach var="seat" items="${bus.seats}">
                            <c:if test="${seat.seatType=='lower' && !fn:startsWith(seat.seatNumber,'lobby')}">
                                <div class="seat seat-sleeper" style="display:flex;align-items:flex-end;justify-content:center;">
                                    ${seat.seatNumber}
                                </div>
                            </c:if>
                            <c:if test="${seat.seatType=='lower' && fn:startsWith(seat.seatNumber,'lobby')}">
                              <div class=""></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div>
                    <strong>Upper Deck</strong>
                    <div class="deck-wrapper" style="display:grid; grid-template-columns:repeat(5,55px); gap:5px;">
                        <c:forEach var="seat" items="${bus.seats}">
                            <c:if test="${seat.seatType=='upper' && !fn:startsWith(seat.seatNumber,'lobby')}">
                                <div class="seat seat-sleeper" style="display:flex;align-items:flex-end;justify-content:center;">
                                    ${seat.seatNumber}
                                </div>
                            </c:if>
                            <c:if test="${seat.seatType=='upper' && fn:startsWith(seat.seatNumber,'lobby')}">
                              <div class=""></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>
</td>
                                   
                                    
                                    <td>
	                                    <div class="d-flex gap-2">
	                                        <sec:authorize access="hasAuthority('BUS_EDIT')">
	                                            <a href="<c:url value='/admin/bus/edit/${bus.busId}'/>" class="bg-info-focus bg-hover-info-200 text-info-600 fw-medium w-40-px h-40-px d-flex justify-content-center align-items-center rounded-circle">
	                                                <iconify-icon icon="majesticons:eye-line" class="icon text-xl"></iconify-icon>
	                                            </a>
	                                        </sec:authorize>
	
	                                        <sec:authorize access="hasAuthority('BUS_DELETE')">
	                                            
	                                                <a href="<c:url value='/admin/bus/delete/${bus.busId}'/>"
	                                                   onclick="return confirm('Are you sure you want to delete this bus?\nMake sure not having any active trips!');"
	                                                   class="w-40-px h-40-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
	                                                    <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
	                                                </a>
	                                           
	                                        </sec:authorize>
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
