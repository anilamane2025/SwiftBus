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
        <h6 class="fw-semibold mb-0">Route Management</h6>
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
            <li class="fw-medium">${formfor} Route</li>
        </ul>
    </div>

    <sec:authorize access="hasAuthority('ROUTES_ADD')">
        <!-- Global Error Display -->
        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <c:forEach items="${errors.allErrors}" var="err">
                    <div>${err.defaultMessage}</div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <div class="card basic-data-table">
            <div class="card-body">
                <form:form method="post" modelAttribute="routeDTO" action="/admin/bus-routes/save">
                    <h6>Route </h6>
                    <form:hidden path="routeId" id="routeId"/>
                    <div class="row">
                        <!-- Route Name -->
                        <div class="col-sm-6">
                            <label class="form-label">Route Name</label>
                            <form:input path="routeName" cssClass="form-control radius-8"/>
                            <form:errors path="routeName" cssClass="invalid-feedback"/>
                        </div>

                        <!-- Total Distance -->
                        <div class="col-sm-6">
                            <label class="form-label">Total Distance (Km)</label>
                            <form:input path="distanceKm" cssClass="form-control radius-8" readonly="true"/>
                            <form:errors path="distanceKm" cssClass="invalid-feedback"/>
                        </div>
                    </div>

                    <hr style="margin: 1%"/>
                    <h6>Route Stops</h6>
                    <div id="stops-container">
                        <c:forEach var="stop" items="${routeDTO.stops}" varStatus="status">
                            <div class="stop-item mb-3" data-index="${status.index}">
                                <%-- <input type="hidden" name="stops[${status.index}].routeStopId" value="${stop.routeStopId}"/>
                                <input type="hidden" name="stops[${status.index}].cityId" value="${stop.cityId}"/> --%>
                                
                                <form:hidden path="stops[${status.index}].routeStopId" value="${stop.routeStopId}" />
							    <form:hidden path="stops[${status.index}].cityId" value="${stop.cityId}" />
							    <form:hidden path="stops[${status.index}].routeId" value="${stop.routeId}" />
                                

                                <div class="row g-3 align-items-center">
                                    <div class="col-sm-2">
                                        <label>State</label>
                                        <select name="stops[${status.index}].cityState" class="form-control state-select">
                                            <option value="">--Select State--</option>
                                            <c:forEach var="state" items="${states}">
                                                <option value="${state}" <c:if test="${stop.cityState==state}">selected</c:if>>${state}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <label>City</label>
                                        <select class="form-control city-select">
                                            <option value="${stop.cityId}" selected>${stop.cityName}</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
									    <label>Stop Name</label>
									    <input type="text" name="stops[${status.index}].stopName"
									           value="${stop.stopName}" 
									           class="form-control stop-name"/>
									</div>
									                                    
                                    <div class="col-sm-1">
                                        <label>Stop Order</label>
                                        <input type="number" name="stops[${status.index}].stopOrder" readonly class="form-control stop-order"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <label>Distance (Km)</label>
                                        <%-- <input type="number" name="stops[${status.index}].distanceFromOriginKm" class="form-control distance"/> --%>
                                        <form:input path="stops[${status.index}].distanceFromOriginKm" value="${stop.distanceFromOriginKm}" class="form-control distance"/>
                                        <form:errors path="stops[${status.index}].distanceFromOriginKm" cssClass="invalid-feedback"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <label>Minutes from Start</label>
                                        <%-- <input type="number" name="stops[${status.index}].minutesFromStart" class="form-control minutes"/> --%>
                                        <form:input path="stops[${status.index}].minutesFromStart" value="${stop.minutesFromStart}" class="form-control minutes"/>
                                        <form:errors path="stops[${status.index}].minutesFromStart" cssClass="invalid-feedback"/>
                                    </div>
                                    <div class="col-sm-1">
                                        <button type="button" class="btn btn-danger remove-stop mt-24">X</button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <button type="button" class="btn btn-secondary mt-3" id="add-stop">Add Stop</button>

                    <hr style="margin: 1%"/>
                    <button type="submit" class="btn btn-primary mt-3">Save Route</button>
                </form:form>
            </div>
        </div>
    </sec:authorize>
</div>

<jsp:include page="templates/footer.jsp"/>
<jsp:include page="templates/scripts.jsp"/>

<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

<script>
// Drag and Drop
function updateStopIndexes() {
    var stops = $("#stops-container .stop-item");
    var prevDistance = 0;
    var prevMinutes = 0;

    stops.each(function(index) {
        $(this).attr("data-index", index);

        // Update stop order
        $(this).find(".stop-order").val(index + 1);

        // Update input names
        $(this).find("input, select").each(function() {
            var name = $(this).attr("name");
            if (name) {
                name = name.replace(/\[\d+\]/, "[" + index + "]");
                $(this).attr("name", name);
            }
        });

        // Distance calculation: ensure increasing
        var distanceInput = $(this).find(".distance");
        var distanceVal = parseFloat(distanceInput.val());
        if (isNaN(distanceVal) || distanceVal <= prevDistance) {
            distanceVal = prevDistance + 1; // default increment 1 km
          //  distanceInput.val(distanceVal.toFixed(1));
            distanceInput.val(Math.round(distanceVal));
        }
        prevDistance = distanceVal;

        // Minutes from start: ensure increasing
        var minutesInput = $(this).find(".minutes");
        var minVal = parseFloat(minutesInput.val());
        if (isNaN(minVal) || minVal <= prevMinutes) {
            minVal = prevMinutes + 1; // default increment 1 min
            minutesInput.val(Math.round(minVal));
        }
        prevMinutes = minVal;
    });

    // Update total distance = last stop distance
    //$("input[name='distanceKm']").val(prevDistance.toFixed(1));
    $("input[name='distanceKm']").val(Math.round(prevDistance));

}

// Trigger recalculation on distance or minutes input change
$(document).on("input", ".stop-item .distance, .stop-item .minutes", function() {
    updateStopIndexes();
});

$("#stops-container").sortable({ update: updateStopIndexes });

$("#add-stop").click(function() {
    var index = $("#stops-container .stop-item").length;
    var stopHtml = `
    <div class="stop-item mb-3" data-index="` + index + `">
        <input type="hidden" name="stops[` + index + `].cityId"/>
        <input type="hidden" name="stops[` + index + `].routeStopId"/>
        <div class="row g-3 align-items-center">
            <div class="col-sm-2">
                <label>State</label>
                <select name="stops[` + index + `].cityState" class="form-control state-select">
                    <option value="">--Select State--</option>
                    <c:forEach var="state" items="${states}">
                        <option value="${state}">${state}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
                <label>City</label>
                <select class="form-control city-select">
                    <option value="">--Select City--</option>
                </select>
            </div>
            <div class="col-sm-2">
                <label>Stop Name</label>
                <input type="text" name="stops[` + index + `].stopName" class="form-control stop-name"/>
            </div>
            <div class="col-sm-1">
                <label>Stop Order</label>
                <input type="number" name="stops[` + index + `].stopOrder" readonly class="form-control stop-order"/>
            </div>
            <div class="col-sm-2">
                <label>Distance (Km)</label>
                <input type="number" name="stops[` + index + `].distanceFromOriginKm" class="form-control distance" step="1"/>
            </div>
            <div class="col-sm-2">
                <label>Minutes from Start</label>
                <input type="number" name="stops[` + index + `].minutesFromStart" class="form-control minutes" step="1"/>
            </div>
            <div class="col-sm-1">
                <button type="button" class="btn btn-danger remove-stop mt-24">X</button>
            </div>
        </div>
    </div>`;
    $("#stops-container").append(stopHtml);
    updateStopIndexes();
});


// Remove Stop
$(document).on("click", ".remove-stop", function() {
    $(this).closest(".stop-item").remove();
    updateStopIndexes();
});

// State → City auto-fill
$(document).on("change", ".state-select", function() {
    var $stateSelect = $(this);
    var $stopItem = $stateSelect.closest(".stop-item");
    var state = $stateSelect.val();
    var $citySelect = $stopItem.find(".city-select");
    $citySelect.empty().append('<option value="">--Select City--</option>');

    if (state) {
        $.ajax({
            url: '<c:url value="/cities/cities-by-state"/>',
            type: 'GET',
            data: { state: state },
            success: function(cities) {
                $.each(cities, function(_, city) {
                    $citySelect.append('<option value="' + city.cityId + '">' + city.cityName + '</option>');
                });
            }
        });
    }
});

$(document).on("change", ".city-select", function() {
    var $citySelect = $(this);
    var $stopItem = $citySelect.closest(".stop-item");
    var cityName = $citySelect.find("option:selected").text();

    // Auto-set cityId
    $stopItem.find("input[name$='.cityId']").val($citySelect.val());

    var $stopNameInput = $stopItem.find(".stop-name");
    if (!$stopNameInput.val()) {
        $stopNameInput.val(cityName);
    }
    updateStopIndexes();
});


// Initial update
$(document).ready(function() {
    updateStopIndexes();
});

function validateUniqueStops() {
    var stopNames = [];
    var duplicateFound = false;

    $("#stops-container .stop-item").each(function () {
        var stopName = $(this).find(".stop-name").val()?.trim();
        if (stopName) {
            if (stopNames.includes(stopName)) {
                alert("Duplicate stop name found: " + stopName);
                duplicateFound = true;
                return false; // break loop
            }
            stopNames.push(stopName);
        }
    });

    return !duplicateFound;
}

$("form").on("submit", function (e) {
    if (!validateUniqueStops()) {
        e.preventDefault();
    }
});


</script>

</body>
</html>
