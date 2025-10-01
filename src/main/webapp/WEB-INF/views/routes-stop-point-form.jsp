<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">
            <c:choose>
                <c:when test="${routeStopPointDTO.routeStopPointId != null}">Edit Route Stop Point</c:when>
                <c:otherwise>Add New Route Stop Point</c:otherwise>
            </c:choose>
        </h6>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <sec:authorize access="hasRole('ADMIN')">
                    <a href="<c:url value='/admin/home' />" class="d-flex align-items-center gap-1 hover-text-primary">
                        <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                        Dashboard
                    </a>
                </sec:authorize>
            </li>
            <li>-</li>
            <li class="fw-medium">
                <c:choose>
                    <c:when test="${routeStopPointDTO.routeStopPointId != null}">Edit</c:when>
                    <c:otherwise>Add</c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>

   		<c:if test="${not empty success}">
   <div class="alert alert-success bg-success-100 text-success-600 border-success-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                        <div class="d-flex align-items-center gap-2">
                            <iconify-icon icon="akar-icons:double-check" class="icon text-xl"></iconify-icon>
                            ${success}
                        </div>
                        <button class="remove-button text-success-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                    </div>
</c:if>
<c:if test="${not empty error}">
   <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                        <div class="d-flex align-items-center gap-2">
                            <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                            ${error}
                        </div>
                        <button class="remove-button text-danger-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                    </div>
</c:if>

    <div class="card basic-data-table">
        <div class="card-body">
            <form:form method="post" modelAttribute="routeStopPointDTO"
                       action="/admin/route-stop-point/save">
                <form:hidden path="routeStopPointId"/>
                <form:hidden path="routeStopId"/>

                <!-- Point Name -->
                <div class="mb-20">
                    <label class="form-label">Point Name</label>
                    <form:input path="pointName" cssClass="form-control radius-8"/>
                    <form:errors path="pointName" cssClass="invalid-feedback"/>
                </div>

               

                <!-- Landmark -->
                <div class="mb-20">
                    <label class="form-label">Landmark</label>
                    <form:input path="landmark" cssClass="form-control radius-8"/>
                    <form:errors path="landmark" cssClass="invalid-feedback"/>
                </div>
                
                 <div class="col-sm-2 mb-20">
                    <label class="form-label">Point Type</label>
					<form:select path="pointType" cssClass="form-control radius-8">
			            <form:options  items="${pointTypes}" />
			        </form:select>
			        
                    <form:errors path="pointType" cssClass="invalid-feedback"/>
                </div>

               
                 <c:choose>
			        <c:when test="${routeStopPointDTO.routeStopPointId == null}">
			            <sec:authorize access="hasAuthority('ROUTES_STOP_POINT_ADD')">
			                <button type="submit" class="btn btn-primary radius-12 mt-32 w-100">Save</button>
			            </sec:authorize>
			        </c:when>
			        <c:otherwise>
			            <sec:authorize access="hasAuthority('ROUTES_STOP_POINT_EDIT')">
			                <button type="submit" class="btn btn-warning radius-12 mt-32 w-100">Update</button>
			            </sec:authorize>
			        </c:otherwise>
			    </c:choose>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="templates/footer.jsp" />
<jsp:include page="templates/scripts.jsp" />
</body>
</html>
