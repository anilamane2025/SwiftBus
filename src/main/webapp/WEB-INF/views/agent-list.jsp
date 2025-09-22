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
        <h6 class="fw-semibold mb-0">Agents</h6>
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
            <li class="fw-medium">Agents</li>
        </ul>
    </div>

    <sec:authorize access="hasAuthority('AGENT_VIEW')">
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

        <div class="card basic-data-table">
            <div class="card-header border-bottom bg-base d-flex align-items-center justify-content-between">
                <div></div>
                <sec:authorize access="hasAuthority('AGENT_ADD')">
                    <a href="<c:url value='/admin/agent/register'/>" class="btn btn-primary text-sm btn-sm d-flex align-items-center">
                        <iconify-icon icon="ic:baseline-plus" class="icon text-xl"></iconify-icon>
                        Add New Agent
                    </a>
                </sec:authorize>
            </div>

            <div class="card-body">
                <table class="table bordered-table mb-0" id="agentTable" data-page-length='10'>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Username</th>
                            <th>Phone No</th>
                            <th>City</th>
                            <th>State</th>
                            <th>Commission</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${agents}">
                            <tr>
                                <td>${a.firstName} ${a.lastName}</td>
                                <td>${a.username}</td>
                                <td>${a.phoneNumber}</td>
                                <td>${a.cityName}</td>
                                <td>${a.cityState}</td>
                                <td>${a.commissionType} : ${a.commissionValue}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${a.enabled}">
                                            <span class="bg-success-focus text-success-600 px-20 py-4 radius-4 fw-medium text-sm">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="bg-danger-focus text-danger-600 px-20 py-4 radius-4 fw-medium text-sm">Inactive</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="d-flex gap-2">
                                        <sec:authorize access="hasAuthority('AGENT_EDIT')">
                                            <a href="<c:url value='/admin/agent/edit/${a.id}'/>" class="bg-info-focus bg-hover-info-200 text-info-600 fw-medium w-40-px h-40-px d-flex justify-content-center align-items-center rounded-circle">
                                                <iconify-icon icon="majesticons:eye-line" class="icon text-xl"></iconify-icon>
                                            </a>
                                        </sec:authorize>

                                        <sec:authorize access="hasAuthority('AGENT_DELETE')">
                                            <c:if test="${a.enabled}">
                                                <a href="<c:url value='/admin/agent/delete/${a.id}'/>"
                                                   onclick="return confirm('Are you sure you want to deactivate this agent?');"
                                                   class="w-40-px h-40-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
                                                    <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
                                                </a>
                                            </c:if>
                                            <c:if test="${!a.enabled}">
                                                <a href="<c:url value='/admin/agent/activate/${a.id}'/>"
                                                   onclick="return confirm('Are you sure you want to activate this agent?');"
                                                   class="w-40-px h-40-px bg-success-focus text-success-main rounded-circle d-inline-flex align-items-center justify-content-center">
                                                    <iconify-icon icon="mingcute:check-2-line"></iconify-icon>
                                                </a>
                                            </c:if>
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
    $('.remove-button').on('click', function() {
        $(this).closest('.alert').addClass('d-none');
    });

    let agentTable = new DataTable('#agentTable');
</script>
</body>
</html>
