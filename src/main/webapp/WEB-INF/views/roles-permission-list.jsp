<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<body>
	<jsp:include page="templates/sidebar.jsp" />
	<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Role-Permission Management</h6>
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
            <li class="fw-medium">Role &amp; Access</li>
        </ul>
    </div>

    <sec:authorize access="hasAuthority('ROLE_PERMISSION_MANAGE')">
        <div class="card basic-data-table">
            <div class="card-body">
              <c:url var="saveRolePermissionUrl" value="/roles/save-permissions" />
<form:form method="post" action="${saveRolePermissionUrl}" modelAttribute="rolePermissionDTO">

    <c:forEach var="r" items="${roles}">
	    <div class="card mb-24 shadow-sm">
	        <div class="card-header bg-base py-12 px-24">
	            <h6 class="text-lg fw-semibold mb-0">${r.userType}</h6>
	        </div>
	        <div class="card-body p-24">
	            
	            <!-- Hidden ensures roleId is always sent, even if no checkbox checked -->
	            <input type="hidden" name="rolePermissions[${r.id}]" value="" />
	
	            <div class="row">
	                <c:forEach var="p" items="${permissions}" varStatus="status">
	                    <div class="col-md-3 col-sm-4 col-6 mb-16">
	                        <div class="form-switch switch-success d-flex align-items-center gap-3">
	                            <input class="form-check-input" type="checkbox" role="switch"
	                                   id="role${r.id}_perm${p.id}"
	                                   name="rolePermissions[${r.id}]"
	                                   value="${p.id}"
	                                   <c:if test="${rolePermissionMap[r.id].contains(p.id)}">checked</c:if> />
	                            <label class="form-check-label line-height-1 fw-medium text-secondary-light"
	                                   for="role${r.id}_perm${p.id}">${p.name}</label>
	                        </div>
	                    </div>
	                </c:forEach>
	            </div>
	        </div>
	    </div>
	</c:forEach>


    <div class="mt-3 text-end">
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </div>
</form:form>



            </div>
        </div>
    </sec:authorize>

</div>
	<jsp:include page="templates/footer.jsp" />
	
	<jsp:include page="templates/scripts.jsp" />

</body>
</html>