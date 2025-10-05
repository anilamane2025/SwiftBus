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

    <!-- Title -->
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">
        <c:choose>
                <c:when test="${trip.tripId != null}">Edit Trip</c:when>
                <c:otherwise>Add Trip</c:otherwise>
            </c:choose>
        </h6>
        <ul class="d-flex align-items-center gap-2">
            <li><a href="<c:url value='/admin/home'/>">Dashboard</a></li>
            <li>-</li>
            <li>Trips</li>
        </ul>
    </div>
<sec:authorize access="hasAuthority('TRIP_ADD')">
    <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
    <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
    <c:choose>
         <c:when test="${editable}">
    <div class="card">
        <div class="card-body">
            <form:form method="post" modelAttribute="trip" action="/admin/trips/save">
    <form:hidden path="tripId"/>
    <div class="col-sm-12 mb-3">
        <label class="form-label">Bus:</label>
        <form:select path="busId" cssClass="form-control radius-8">
            <form:options items="${buses}"  itemValue="busId" itemLabel="busName"/>
        </form:select>
        <form:errors path="busId" cssClass="invalid-feedback"/>
    </div>

    <div class="col-sm-12 mb-3">
        <label class="form-label">Route:</label>
        <form:select path="routeId" cssClass="form-control radius-8">
            <form:options items="${routes}" itemValue="routeId" itemLabel="routeName"/>
        </form:select>
        <form:errors path="routeId" cssClass="invalid-feedback"/>
    </div>

<div class='row'>
    <div class="col-sm-6 mb-3">
        <label class="form-label">Service Date:</label>
        <form:input path="serviceDate" cssClass="form-control radius-8" type="date"/>
        <form:errors path="serviceDate"   cssClass="invalid-feedback"/>
    </div>

    <div class="col-sm-6 mb-3">
        <label class="form-label">Departure Datetime:</label>
        <form:input path="departureDatetime"  cssClass="form-control radius-8" type="datetime-local"/>
        <form:errors path="departureDatetime" cssClass="invalid-feedback"/>
    </div>
</div>
    

    <button type="submit" class="btn btn-primary w-100">
    <c:choose>
                            <c:when test="${trip.tripId != null}">Update Trip</c:when>
                            <c:otherwise>Save Trip</c:otherwise>
                        </c:choose>
    </button>
</form:form>

        </div>
    </div>
    </c:when>
         <c:otherwise>You can't edit trip because some booking already done.</c:otherwise>
         </c:choose>
    </sec:authorize>
</div>

<jsp:include page="templates/footer.jsp" />
<jsp:include page="templates/scripts.jsp" />
</body>
</html>
