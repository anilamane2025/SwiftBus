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
/* Seat + Deck CSS */
.seat { width:36px; height:80px; color:#fff; display:flex; align-items:flex-end; justify-content:center; border-radius:6px; background-size:cover; background-repeat:no-repeat; background-position:center; position:relative; cursor:pointer; }
.seat-s { width:36px; height:33px; }
.seat-sleeper { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/734365e5.svg"); }
.seat-seater { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/584368fd.svg"); }

.deck-wrapper { display:flex; flex-wrap:wrap; gap:20px; justify-content:start; border:1px solid rgba(16,16,16,0.13);
    border-radius:12px; background:#fff; padding:15px; box-sizing:border-box; }
.deck { flex:1 1 calc(50% - 20px); min-width:280px; justify-content:center;}
.deck-title { font-weight:bold; margin-bottom:10px; text-align:center; }
.grid-seater { display:grid; grid-template-columns:repeat(6,60px); gap:5px; }
.grid-2x1 { display:grid; grid-template-columns:repeat(4,55px); gap:5px; }
.grid-2x2 { display:grid; grid-template-columns:repeat(5,55px); gap:5px; }

.seat span { font-size:10px; background:rgba(0,0,0,0.6); padding:1px 3px; border-radius:3px; margin-bottom:3px; }
.seat-tooltip { position:absolute; top:2px; right:2px; font-size:12px; color:#fff; cursor:pointer; }
</style>
<body>
<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Edit Bus</h6>
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

    <sec:authorize access="hasAuthority('BUS_EDIT')">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <div class="card basic-data-table">
            <div class="card-body">
                <form:form method="post" modelAttribute="busDTO" action="/admin/bus/edit/${busDTO.busId}">
                    <div class="row">
                    <form:hidden path="busId" id="busId"/>
                        <!-- Bus Name -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Bus Name</label>
                            <form:input path="busName" cssClass="form-control radius-8"/>
                            <form:errors path="busName" cssClass="invalid-feedback"/>
                        </div>

                        <!-- Registration No -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Registration No</label>
                            <form:input path="registrationNo" cssClass="form-control radius-8"/>
                            <form:errors path="registrationNo" cssClass="invalid-feedback"/>
                        </div>

                        <!-- Readonly fields -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Bus Type</label>
                            <form:input path="busType" cssClass="form-control radius-8" readonly="true"/>
                        </div>

                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Total Seats</label>
                            <form:input path="totalSeats" cssClass="form-control radius-8" readonly="true"/>
                        </div>

                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Seat Layout</label>
                            <form:input path="seatLayoutVersion" cssClass="form-control radius-8" readonly="true"/>
                        </div>

                        <!-- Seat Layout Preview -->
                        <div class="col-sm-10 mb-3">
                            <label>Seat Layout Preview (click to edit)</label>
                            <c:if test="${not empty seatErrors}">
    <div class="alert alert-danger">
        <c:forEach var="err" items="${seatErrors}">
            <div>${err}</div>
        </c:forEach>
    </div>
</c:if>
                            <div class="col-sm-6 mb-3">
                                <c:choose>
                                    <c:when test="${busDTO.seatLayoutVersion eq 'seater'}">
                                        <div class="deck-wrapper grid-seater" style="width:65%;">
                                            <c:forEach var="s" items="${busDTO.seats}" varStatus="i">
                                                <c:if test="${!fn:startsWith(s.seatNumber,'lobby')}">
                                                    <div class="seat-s seat-seater" onclick="editSeat(${i.index})">
                                                        <span id="seat-no-${i.index}">${s.seatNumber}</span>
                                                        <c:if test="${not empty s.extraInfo}">
                                                            <span class="seat-tooltip" id="tooltip-${i.index}" title="${s.extraInfo}">&#9432;</span>
                                                        </c:if>
                                                        <!-- hidden fields -->
                                                <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                
                                                <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                    </div>
                                                </c:if>
                                                <c:if test="${fn:startsWith(s.seatNumber,'lobby')}">
                                                    <div class="seat">
                                                     <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                     <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                    </div>
                                                </c:if>

                                                
                                            </c:forEach>
                                        </div>
                                    </c:when>

                                    <c:when test="${busDTO.seatLayoutVersion eq '2x1'}">
                                        <div style="display:flex; gap:50px;">
                                            <!-- Lower -->
                                            <div class="deck">
                                                <div class="deck-title">Lower Deck</div>
                                                <div class="grid-2x1 deck-wrapper" style="width:85%;" >
                                                	
                                                    <c:forEach var="s" items="${busDTO.seats}" varStatus="i">
                                                    
                                                        <c:if test="${s.seatType eq 'lower' && !fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="seat seat-sleeper" onclick="editSeat(${i.index})">
                                                                <span id="seat-no-${i.index}">${s.seatNumber}</span>
                                                                <c:if test="${not empty s.extraInfo}">
                                                                    <span class="seat-tooltip" id="tooltip-${i.index}" title="${s.extraInfo}">&#9432;</span>
                                                                </c:if>
                                                                <!-- hidden fields -->
                                                        <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                        <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                        
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${s.seatType eq 'lower' && fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="seat">
                                                             <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                             <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <!-- Upper -->
                                            <div class="deck">
                                                <div class="deck-title">Upper Deck</div>
                                                <div class="grid-2x1 deck-wrapper"  style="width:85%;">
                                                    <c:forEach var="s" items="${busDTO.seats}" varStatus="i">
                                                        <c:if test="${s.seatType eq 'upper' && !fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="seat seat-sleeper" onclick="editSeat(${i.index})">
                                                                <span id="seat-no-${i.index}">${s.seatNumber}</span>
                                                                <c:if test="${not empty s.extraInfo}">
                                                                    <span class="seat-tooltip" id="tooltip-${i.index}" title="${s.extraInfo}">&#9432;</span>
                                                                </c:if>
                                                                <!-- hidden fields -->
                                                        <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                        <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${s.seatType eq 'upper' && fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="">
                                                            <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                            
                                                            <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                        <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>

                                    <c:when test="${busDTO.seatLayoutVersion eq '2x2'}">
                                        <div style="display:flex; gap:20px;">
                                            <!-- Lower -->
                                            <div class="deck">
                                                <div class="deck-title">Lower Deck</div>
                                                <div class="grid-2x2 deck-wrapper">
                                                    <c:forEach var="s" items="${busDTO.seats}" varStatus="i">
                                                        <c:if test="${s.seatType eq 'lower' && !fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="seat seat-sleeper" onclick="editSeat(${i.index})">
                                                                <span id="seat-no-${i.index}">${s.seatNumber}</span>
                                                                <c:if test="${not empty s.extraInfo}">
                                                                    <span class="seat-tooltip" id="tooltip-${i.index}" title="${s.extraInfo}">&#9432;</span>
                                                                </c:if>
                                                                <!-- hidden fields -->
                                                        <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                        <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${s.seatType eq 'lower' && fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="">
                                                             <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                             <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <!-- Upper -->
                                            <div class="deck">
                                                <div class="deck-title">Upper Deck</div>
                                                <div class="grid-2x2 deck-wrapper">
                                                    <c:forEach var="s" items="${busDTO.seats}" varStatus="i">
                                                        <c:if test="${s.seatType eq 'upper' && !fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="seat seat-sleeper" onclick="editSeat(${i.index})">
                                                                <span id="seat-no-${i.index}">${s.seatNumber}</span>
                                                                <c:if test="${not empty s.extraInfo}">
                                                                    <span class="seat-tooltip" id="tooltip-${i.index}" title="${s.extraInfo}">&#9432;</span>
                                                                </c:if>
                                                                <!-- hidden fields -->
                                                        <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                        <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                        <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${s.seatType eq 'upper' && fn:startsWith(s.seatNumber,'lobby')}">
                                                            <div class="">
                                                             <input type="hidden" name="seats[${i.index}].busSeatId" value="${s.busSeatId}"/>
                                                             <input type="hidden" name="seats[${i.index}].seatRow" id="seat-row-${i.index}" value="${s.seatRow}"/>
                                                            <input type="hidden" name="seats[${i.index}].seatCol" id="seat-col-${i.index}" value="${s.seatCol}"/>
                                                <input type="hidden" name="seats[${i.index}].seatNumber" id="seat-input-${i.index}" value="${s.seatNumber}"/>
                                                <input type="hidden" name="seats[${i.index}].extraInfo" id="extra-input-${i.index}" value="${s.extraInfo}"/>
                                                <input type="hidden" name="seats[${i.index}].seatType" value="${s.seatType}"/>
                                                            </div>
                                                        </c:if>
                                                        
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>

                        <div class="col-12 mt-3">
                            <button type="submit" class="btn btn-primary w-100">Update Bus</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </sec:authorize>
</div>

<jsp:include page="templates/footer.jsp" />
<jsp:include page="templates/scripts.jsp" />

<script>
function editSeat(i){
    var seatNoEl = document.getElementById("seat-no-"+i);
    var seatInput = document.getElementById("seat-input-"+i);
    var extraInput = document.getElementById("extra-input-"+i);
    var tooltip = document.getElementById("tooltip-"+i);

    // Seat number
    var no=prompt("Edit seat number:", seatNoEl.innerText);
    if(no && no.trim()!==""){
        seatNoEl.innerText=no.trim();
        seatInput.value=no.trim();
    }

    // Extra info
    var extra=prompt("Edit extra info:", extraInput.value);
    if(extra!==null){
        extraInput.value=extra.trim();
        if(extra.trim()!==""){
            if(tooltip){
                tooltip.setAttribute("title",extra.trim());
            }else{
                var newSpan=document.createElement("span");
                newSpan.className="seat-tooltip";
                newSpan.id="tooltip-"+i;
                newSpan.title=extra.trim();
                newSpan.innerHTML="&#9432;";
                seatNoEl.parentNode.appendChild(newSpan);
            }
        }else{
            if(tooltip){ tooltip.remove(); }
        }
    }
}
</script>

</body>
</html>
