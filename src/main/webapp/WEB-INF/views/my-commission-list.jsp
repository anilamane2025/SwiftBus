<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<style>
.status-true { color: green; font-weight: bold; }
.status-false { color: red; font-weight: bold; }
</style>

<body>
<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">

    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Agent Commission Report</h6>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <a href="<c:url value='/dashboard'/>" class="d-flex align-items-center gap-1 hover-text-primary">
                    <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                    Dashboard
                </a>
            </li>
            <li>-</li>
            <li class="fw-medium">Agent Commission Report</li>
        </ul>
    </div>

    <div class="card basic-data-table">
        <div class="card-body">
            <table class="table bordered-table mb-0" id="dataTable" data-page-length="10">
                <thead>
                    <tr>
                        <th>Booked At</th>
                        <th>Booking ID</th>
                        <th>Booking Amount</th>
                        <th>Commission</th>
                        <th>Settled</th>
                        
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${commissions}" varStatus="status">
                        <tr>
                            <td>${r.bookedAtFormat}</td>
                            <td>${r.bookingId}</td>
                            <td>₹${r.bookingTotalAmount}</td>
                            <td>₹${r.commissionAmount}</td>
                            <td>
                                <span class="${r.settled ? 'status-true' : 'status-false'}">
                                    ${r.settled ? 'Yes' : 'No'}
                                </span>
                            </td>
                            
                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

           

        </div>
    </div>
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
