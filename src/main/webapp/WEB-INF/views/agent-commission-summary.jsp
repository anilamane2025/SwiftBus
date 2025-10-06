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
        <h6 class="fw-semibold mb-0">Agent Commission Summary Report</h6>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <a href="<c:url value='/admin/home'/>" class="d-flex align-items-center gap-1 hover-text-primary">
                    <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                    Dashboard
                </a>
            </li>
            <li>-</li>
            <li class="fw-medium">Commission Summary</li>
        </ul>
    </div>
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <ul class="d-flex align-items-center gap-2">
       <li class="fw-medium">
                <form method="get" action="<c:url value='/admin/booking/agent-commissions-summary'/>" class="mb-3 d-flex gap-2">
    <input type="date" name="startDate" value="${startDate}" class="form-control radius-8"/>
    <input type="date" name="endDate" value="${endDate}" class="form-control radius-8"/>
    <div class="form-switch switch-success d-flex align-items-center gap-3">
	    <input class="form-check-input" type="checkbox" role="switch"
	           id="settled"
	           name="settled"
	           value="true"
	           <c:if test="${settled}">checked</c:if> />
	    <label class="form-check-label line-height-1 fw-medium text-secondary-light"
	           for="settled">Settled</label>
	</div>

    <button type="submit" class="btn rounded-pill btn-info-100 text-info-600 radius-8 d-flex align-items-center gap-1">Filter</button>
</form>
</li>
       </ul>
       </div>
    

    <div class="card basic-data-table">
        <div class="card-body">
            <table class="table bordered-table mb-0" id="dataTable" data-page-length="10">
                <thead>
			        <tr>
			            <th>Agent</th>
			            <th>Total Bookings</th>
			            <th>Total Commission (₹)</th>
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach var="a" items="${agentSummaries}">
			            <tr>
			                <td>${a.agentName}</td>
			                <td>${a.totalBookings}</td>
			                <td>₹${a.totalCommission}</td>
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
