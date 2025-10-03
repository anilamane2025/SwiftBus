<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<body>
	<jsp:include page="templates/sidebar.jsp" />
	<jsp:include page="templates/header.jsp" />

	<div class="dashboard-main-body">
		<div
			class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
			<h6 class="fw-semibold mb-0">Routes</h6>
			<ul class="d-flex align-items-center gap-2">
				<li class="fw-medium">
				
				<sec:authorize access="hasRole('ADMIN')">
       <a href="<c:url value='/admin/home' />"
					class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
							icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
						Dashboard
				</a>
    </sec:authorize>
				</li>
				<li>-</li>
    			<li class="fw-medium">Routes</li>
			</ul>
		</div>

		
		<sec:authorize access="hasAuthority('ROUTES_VIEW')">
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
			<div class="card-header border-bottom bg-base d-flex align-items-center flex-wrap  justify-content-between">
                <div></div>
                <sec:authorize access="hasAuthority('ROUTES_ADD')">
                <a href="<c:url value='/admin/bus-routes/add'/>" type="button" class="btn btn-primary text-sm btn-sm d-flex align-items-center " >
                    <iconify-icon icon="ic:baseline-plus" class="icon text-xl line-height-1"><template shadowrootmode="open"><style data-style="data-style">:host{display:inline-block;vertical-align:0}span,svg{display:block}</style><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="M19 12.998h-6v6h-2v-6H5v-2h6v-6h2v6h6z"></path></svg></template></iconify-icon>
                    Add New Route
                </a>
                </sec:authorize>
            </div>
      <div class="card-body">
        <table class="table bordered-table mb-0" id="dataTable" data-page-length='10'>
          <thead>
            <tr>
              <th scope="col" >Name</th>
              <th>Distance KM</th>
	            <th>Stops</th>
	            <th>Actions</th>
            </tr>
          </thead>
          <tbody>
         <c:forEach var="route" items="${routes}">
            <tr>
                <td>${route.routeName}</td>
                <td>${route.distanceKm}</td>
                <td>
                <button class="toggle-stops btn btn-sm btn-light" data-route="${route.routeId}">
                    <iconify-icon icon="akar-icons:chevron-down"></iconify-icon>
                </button>
            </td>
                <td>
                 <div class="d-flex gap-2">
                    <sec:authorize access="hasAuthority('ROUTES_EDIT')">
                        <a type="button"
         href="<c:url value='/admin/bus-routes/edit/${route.routeId}'/>"
         class="bg-info-focus bg-hover-info-200 text-info-600 fw-medium w-40-px h-40-px d-flex justify-content-center align-items-center rounded-circle"> 
        <iconify-icon icon="majesticons:eye-line" class="icon text-xl"></iconify-icon>
      </a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('ROUTES_DELETE')">
                        <a href="<c:url value='/admin/bus-routes/delete/${route.routeId}'/>"
           onclick="return confirm('Are you sure you want to delete this route?');"
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
        <div id="stops-container"></div>
      </div>
    </div>
			
		</sec:authorize>

		

	</div>
	
    
    

	<jsp:include page="templates/footer.jsp" />
	
	<jsp:include page="templates/scripts.jsp" />
	
<script>    
    $('.remove-button').on('click', function() {
        $(this).closest('.alert').addClass('d-none')
    }); 
</script>
<script>
  var routesData = [
  <c:forEach var="r" items="${routes}" varStatus="routeStatus">
    {
      routeId: ${r.routeId},
      routeName: '${r.routeName}',
      distanceKm: ${r.distanceKm},
      stops: [
        <c:forEach var="s" items="${r.stops}" varStatus="stopStatus">
        {
          stopOrder: ${s.stopOrder},
          stopName: '${s.stopName}',
          cityName: '${s.cityName}',
          cityState: '${s.cityState}',
          distanceFromOriginKm: ${s.distanceFromOriginKm},
          minutesFromStart: ${s.minutesFromStart}
        }<c:if test="${!stopStatus.last}">,</c:if>
        </c:forEach>
      ]
    }<c:if test="${!routeStatus.last}">,</c:if>
  </c:forEach>
  ];
</script>


<script>
  let table = new DataTable('#dataTable');

  $(document).on('click', '.toggle-stops', function() {
	    var routeId = $(this).data('route');
	    var $btn = $(this);
	    var $row = $btn.closest('tr');
	    var $existing = $('#stops-' + routeId);

	    if ($existing.length) {
	        // Collapse
	        $existing.remove();
	        $btn.find('iconify-icon').attr('icon', 'akar-icons:chevron-down');
	    } else {
	        // Expand
	        var route = routesData.find(r => r.routeId == routeId);
	        if (!route) return;

	        // Sort stops by order
	        route.stops.sort((a, b) => a.stopOrder - b.stopOrder);

	        // Build Stops Table
	        var html = '<tr id="stops-' + routeId + '" class="stops-row">' +
	                     '<td colspan="4">' +
	                       '<table class="table table-sm table-bordered mb-0">' +
	                         '<thead class="table-light">' +
	                           '<tr>' +
	                             '<th>#</th>' +
	                             '<th>Stop Name</th>' +
	                             '<th>City</th>' +
	                             '<th>Distance (Km)</th>' +
	                             '<th>Minutes From Start</th>' +
	                           '</tr>' +
	                         '</thead>' +
	                         '<tbody>';

	        route.stops.forEach(function(stop) {
	            html += '<tr>' +
	                        '<td>' + stop.stopOrder + '</td>' +
	                        '<td>' + stop.stopName + '</td>' +
	                        '<td>' + stop.cityName + ', ' + stop.cityState + '</td>' +
	                        '<td>' + stop.distanceFromOriginKm + '</td>' +
	                        '<td>' + stop.minutesFromStart + '</td>' +
	                    '</tr>';
	        });

	        html +=        '</tbody>' +
	                     '</table>' +
	                   '</td>' +
	                 '</tr>';

	        // Insert below the clicked row
	        $row.after(html);

	        // Change arrow
	        $btn.find('iconify-icon').attr('icon', 'akar-icons:chevron-up');
	    }
	});




</script>



	
</body>
</html>