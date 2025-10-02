<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<body>
<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Fare Segments</h6>
        <ul class="d-flex align-items-center gap-2">
            <li class="fw-medium">
                <sec:authorize access="hasRole('ADMIN')">
                    <a href="<c:url value='/admin/home'/>" class="d-flex align-items-center gap-1 hover-text-primary">
                        <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                        Dashboard
                    </a>
                </sec:authorize>
            </li>
            <li>-</li>
            <li class="fw-medium">Fare Segments</li>
        </ul>
    </div>

    <sec:authorize access="hasAuthority('FARE_SEGMENT_VIEW')">
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
            <div class="card-header border-bottom bg-base d-flex align-items-center flex-wrap justify-content-between">
                <div></div>
            </div>
            <div class="card-body">
                <table class="table bordered-table mb-0" id="dataTable" data-page-length='10'>
                    <thead>
                    <tr>
                        <th>Route Name</th>
                        <th>Total Stops</th>
                        <th>Fares</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="route" items="${routes}">
                        <tr>
                            <td>${route.routeName}</td>
                            <td>${route.stops.size()}</td>
                            <td>
                                <button class="toggle-fares btn btn-sm btn-light" data-route="${route.routeId}">
                                    <iconify-icon icon="akar-icons:chevron-down"></iconify-icon>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </sec:authorize>
</div>

<jsp:include page="templates/footer.jsp"/>
<jsp:include page="templates/scripts.jsp"/>

<script>
$('.remove-button').on('click', function() {
    $(this).closest('.alert').addClass('d-none')
});

var faresData = [
	<c:forEach var="r" items="${routes}" varStatus="rs">
	{
	    routeId: ${r.routeId},
	    routeName: '${r.routeName}',
	    stops: [
	        <c:forEach var="s" items="${r.stops}" varStatus="ss">
	        {
	            stopOrder: ${s.stopOrder},
	            stopName: '${s.stopName}',
	            fares: [
	                <c:forEach var="f" items="${s.fares}" varStatus="fs">
	                {
	                    fareSegmentId: ${f.fareSegmentId},
	                    toStopName: '${f.toStopName}',
	                    toStopOrder: ${f.toStopOrder},
	                    fareAmount: ${f.fareAmount}
	                }<c:if test="${!fs.last}">,</c:if>
	                </c:forEach>
	            ]
	        }<c:if test="${!ss.last}">,</c:if>
	        </c:forEach>
	    ]
	}<c:if test="${!rs.last}">,</c:if>
	</c:forEach>
	];

</script>

<script>
let table = new DataTable('#dataTable');

$('#dataTable tbody tr').each(function(index) {
    var $row = $(this);
    var routeId = $row.find('.toggle-fares').data('route');
    var route = faresData.find(r => r.routeId == routeId);

    if (!route) return;

    let anyStopMissingFares = route.stops.some(s => s.fares.length === 0);

    if (anyStopMissingFares) {
        $row.find('td:last').append(
            '<a href="/admin/fare-segment/generate/'+route.routeId+'" class="btn btn-sm btn-outline-success ms-2">Generate Auto Fares</a>'
        );
    }
});

$(document).on('click', '.toggle-fares', function() {
    var routeId = $(this).data('route');
    var $btn = $(this), $row = $btn.closest('tr'), $existing = $('#fares-' + routeId);

    if ($existing.length) {
        $existing.remove();
        $btn.find('iconify-icon').attr('icon', 'akar-icons:chevron-down');
    } else {
        var route = faresData.find(r => r.routeId == routeId);
        if (!route) return;
        route.stops.sort((a,b)=>a.stopOrder-b.stopOrder);
        
        let stopsWithoutLast = route.stops.slice(0, -1);

        var html = '<tr id="fares-'+routeId+'" class="fares-row"><td colspan="3">';
        stopsWithoutLast.forEach(function(stop){
            html += '<div class="mb-3">';
            html += '<h6>Stop: '+stop.stopOrder+' - '+stop.stopName+' <button class="toggle-stop-fares btn btn-sm btn-outline-primary" data-stop="'+stop.stopOrder+'" data-route="'+routeId+'">View Fares</button></h6>';
            
            if(stop.fares.length === 0){
                html += '<div class="text-danger mb-2">No fares available for this stop.</div>';
            } else {
            	html += '<div id="stop-fares-'+routeId+'-'+stop.stopOrder+'" class="d-none">';
                html += '<form action="/admin/fare-segment/saveAll" method="post">';
                html += '<input type="hidden" name="routeId" value="'+routeId+'"/>';
                html += '<table class="table table-sm table-bordered mb-2">';
                html += '<thead><tr><th>To Stop</th><th>Fare Amount</th></tr></thead><tbody>';
                
                stop.fares.sort((a,b)=>a.toStopOrder - b.toStopOrder);
                
                stop.fares.forEach(function(f){
                    html += '<tr '+(f.fareAmount==0?'class="table-warning"':'')+'>';
                    html += '<td>'+f.toStopName+'</td>';
                    html += '<td>';
                    html += '<input type="hidden" name="fareSegmentIds" value="'+f.fareSegmentId+'"/>';
                    html += '<input type="number" name="fareAmounts" value="'+f.fareAmount+'" step="0.01" min="0" class="form-control" required/>';
                    html += '</td></tr>';
                });
                html += '</tbody></table>';
                html += '<button type="submit" class="btn btn-primary btn-sm mb-2">Save Stop Fares</button>';
                html += '</form></div>';
            }

            html += '</div>';
        });

        html += '</td></tr>';
        $row.after(html);
        $btn.find('iconify-icon').attr('icon', 'akar-icons:chevron-up');
    }
});

$(document).on('click', '.toggle-stop-fares', function(){
    var stopOrder = $(this).data('stop');
    var routeId = $(this).closest('tr.fares-row').attr('id').split('-')[1]; // extract routeId
    $('#stop-fares-'+routeId+'-'+stopOrder).toggleClass('d-none');
});

</script>

</body>
</html>
