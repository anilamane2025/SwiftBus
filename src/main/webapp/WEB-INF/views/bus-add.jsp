<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en" data-theme="light">
<style>
.bus-container { max-width: 1200px; margin: 20px auto; font-family: Arial, sans-serif; }
.deck-wrapper { display: flex; flex-wrap: wrap; gap: 20px; justify-content: start; }
.deck {
    flex: 1 1 calc(50% - 20px);
    border: 1px solid rgba(16,16,16,0.13);
    border-radius: 12px;
    background: #fff;
    padding: 15px;
    box-sizing: border-box;
    min-width: 300px;
}
.deck-title { font-weight: bold; margin-bottom: 10px; text-align: center; }
.seat-grid { display: grid; grid-template-columns: repeat(4, 60px); gap: 10px 20px; justify-content: center; }
.seat-grid.seat-grid-seater { grid-template-columns: repeat(6, 60px); gap: 10px 15px; }
.seat-grid.seat-2-grid-seater { grid-template-columns: repeat(5, 60px); gap: 10px 15px; }
.seat, .seat-s { width: 60px; height: 135px; border-radius: 6px; background-size: cover; background-repeat: no-repeat; background-position: center; position: relative; cursor: pointer; display: flex; align-items: flex-end; justify-content: center; }
.seat-s { width: 55px; height: 50px; }
.seat span, .seat-s span { font-size: 11px; color: #fff; background: rgba(0,0,0,0.5); border-radius: 3px; padding: 1px 3px; position: absolute; bottom: 4px; cursor:pointer; }
.seat-sleeper { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/734365e5.svg"); }
.seat-seater { background-image: url("https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/584368fd.svg"); }
.driver { font-family: sans-serif; font-size: 12px; font-weight: 400; color: grey; text-align: right; margin-top: 10px; margin-bottom: 10px; }
.seat-error { border: 2px solid red !important; }
.seat-error-msg { pointer-events: none; }
</style>

<jsp:include page="templates/head.jsp" />
<body>
<jsp:include page="templates/sidebar.jsp" />
<jsp:include page="templates/header.jsp" />

<div class="dashboard-main-body">
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
        <h6 class="fw-semibold mb-0">Add New Bus</h6>
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

    <sec:authorize access="hasAuthority('BUS_ADD')">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <div class="card basic-data-table">
            <div class="card-body">
                <form:form method="post" modelAttribute="busDTO" action="/admin/bus/add">
                    <div class="row">
                        <!-- Bus Name -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Bus Name</label>
                            <form:input path="busName" cssClass="form-control radius-8" />
                            <form:errors path="busName" cssClass="invalid-feedback"/>
                        </div>

                        <!-- Registration No -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Registration No</label>
                            <form:input path="registrationNo" cssClass="form-control radius-8" />
                            <form:errors path="registrationNo" cssClass="invalid-feedback"/>
                        </div>

                        <!-- Bus Type -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Bus Type</label>
                            <form:select path="busType" cssClass="form-control radius-8">
                                <form:option value="Seater">Seater</form:option>
                                <form:option value="Sleeper">Sleeper</form:option>
                                <form:option value="AC Seater">AC Seater</form:option>
                                <form:option value="AC Sleeper">AC Sleeper</form:option>
                            </form:select>
                        </div>

                        <!-- Total Seats -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Total Seats</label>
                            <form:input path="totalSeats" cssClass="form-control radius-8" readonly="true" id="totalSeatsInput"/>
                        </div>

                        <!-- Seat Layout -->
                        <div class="col-sm-6 mb-3">
                            <label class="form-label">Seat Layout</label>
                            <form:select path="seatLayoutVersion" cssClass="form-control radius-8">
                                <form:option value="seater">2x3 Seater</form:option>
                                <form:option value="2x1">2x1 Sleeper</form:option>
                                <form:option value="2x2">2x2 Sleeper</form:option>
                            </form:select>
                        </div>

                        <!-- Seat Layout Preview -->
                        <div class="col-12 mb-3">
                            <label>Seat Layout Preview (click on a seat to edit):</label>
                <c:if test="${not empty seatErrors}">
    <div class="alert alert-danger">
        <c:forEach var="err" items="${seatErrors}">
            <div>${err}</div>
        </c:forEach>
    </div>
</c:if>
                

                            <div id="seatLayoutContainer" class="deck-wrapper"></div>
                        </div>

                        <div class="col-12 mt-3">
                            <button type="submit" class="btn btn-primary w-100">Save Bus</button>
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
function generateSeatLayout() {
    var container = document.getElementById('seatLayoutContainer');
    container.innerHTML = '';
    var layout = document.querySelector('select[name="seatLayoutVersion"]').value;
    var lowerDeck = [], upperDeck = [];
    var lobbyCounter = 1;

    if(layout==='2x1'){ // 2x1 Sleeper
        for(var r=1;r<=6;r++){
        	if(r < 6) { // add lobby for all rows except last
                lowerDeck.push(['L'+((r-1)*3+1),'lobby'+(lobbyCounter++),'L'+((r-1)*3+2),'L'+((r-1)*3+3)]);
                upperDeck.push(['U'+((r-1)*3+1),'lobby'+(lobbyCounter++),'U'+((r-1)*3+2),'U'+((r-1)*3+3)]);
            } else { // last row no lobby
                lowerDeck.push(['L'+((r-1)*3+1),'L'+((r-1)*3+2),'L'+((r-1)*3+3),'L'+((r-1)*3+4)]);
                upperDeck.push(['U'+((r-1)*3+1),'U'+((r-1)*3+2),'U'+((r-1)*3+3),'U'+((r-1)*3+4)]);
            }
        }
        
        renderDecks(lowerDeck, upperDeck, true);
    } else if(layout==='2x2'){ // 2x2 Sleeper
        for(var r=1;r<=6;r++){
            lowerDeck.push(['L'+((r-1)*4+1),'L'+((r-1)*4+2),'lobby'+(lobbyCounter++),'L'+((r-1)*4+3),'L'+((r-1)*4+4)]);
            upperDeck.push(['U'+((r-1)*4+1),'U'+((r-1)*4+2),'lobby'+(lobbyCounter++),'U'+((r-1)*4+3),'U'+((r-1)*4+4)]);
        }
        renderDecks(lowerDeck, upperDeck, true);
    } else if(layout==='seater'){ // 2x3 Seater
        for(var r=1;r<=10;r++){
            lowerDeck.push(['S'+((r-1)*5+1),'S'+((r-1)*5+2),'lobby'+(lobbyCounter++),'S'+((r-1)*5+3),'S'+((r-1)*5+4),'S'+((r-1)*5+5)]);
        }
        renderDecks(lowerDeck, [], false);
    }

    function renderDecks(lower, upper, showUpper){
        var html = '<div class="deck-wrapper">';
        var gridClass = "seat-grid";
        if(layout==='seater') gridClass += ' seat-grid-seater';
        else if(layout==='2x2') gridClass += ' seat-2-grid-seater';
        var seatCounter = 0;

        // Lower Deck
        html += '<div class="deck"><div class="deck-title">Lower Berth</div><div class="driver">Driver Cabin <img src="https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/922a00e3.svg"/></div><div class="'+gridClass+'">';
        //lower.forEach(row => row.forEach(col => { html += seatDiv(col, layout, seatCounter); seatCounter++; }));
        lower.forEach((rowArray, rowIndex) => {
            rowArray.forEach((colSeat, colIndex) => {
                html += seatDiv(colSeat,layout, "lower", seatCounter, rowIndex, colIndex);
                seatCounter++;
            });
        });
        html += '</div></div>';

        // Upper Deck
        if(showUpper){
            html += '<div class="deck"><div class="deck-title">Upper Berth</div><div class="driver">Driver Cabin <img src="https://travel-assets-akamai.paytm.com/travel/mweb-bus/assets/922a00e3.svg"/></div><div class="'+gridClass+'">';
            //upper.forEach(row => row.forEach(col => { html += seatDiv(col, layout, seatCounter); seatCounter++; }));
            upper.forEach((rowArray, rowIndex) => {
                rowArray.forEach((colSeat, colIndex) => {
                    html += seatDiv(colSeat,layout, "upper", seatCounter, rowIndex, colIndex);
                    seatCounter++;
                });
            });
            html += '</div></div>';
        }

        html += '</div>';
        container.innerHTML = html;

        document.querySelectorAll('.seat, .seat-s').forEach(el => el.addEventListener('click', () => editSeat(el)));
    }

    
    function seatDiv(seat, layout,layoutType, index, row, col, extraInfo='') {
        if(!seat) return '<div style="width:60px;height:135px;"></div>';
        var isLobby = seat.startsWith('lobby');
        var displaySeat = isLobby ? '' : seat;
        console.log("layoutType"+layoutType);
        var seatClass = (layout==='seater') ? 'seat-seater' : 'seat-sleeper';
        var seatS = (layout==='seater') ? 'seat-s' : 'seat';
        
        var html = '<div class="'+seatS+' '+seatClass+'" style="'+(isLobby?'visibility:hidden;':'')+'">';
        html += '<input type="hidden" name="seats['+index+'].seatType" value="'+layoutType+'"/>';
        html += '<input type="hidden" name="seats['+index+'].seatNumber" value="'+seat+'" class="seat-number-input"/>';
        html += '<input type="hidden" name="seats['+index+'].seatRow" value="'+row+'"/>';
        html += '<input type="hidden" name="seats['+index+'].seatCol" value="'+col+'"/>';
        html += '<input type="hidden" name="seats['+index+'].extraInfo" value="'+extraInfo+'" class="seat-extra-input"/>';
        if(!isLobby) html += '<span>'+displaySeat+'</span>';
        if(extraInfo.trim() !== '' && !isLobby) html += '<span class="seat-tooltip" title="'+extraInfo+'">&#9432;</span>';
        html += '</div>';
        return html;
    }

}

function editSeat(el){
    var span = el.querySelector('span');
    var seatInput = el.querySelector('.seat-number-input');
    var extraInput = el.querySelector('.seat-extra-input');
    var newSeat = null;
    do { newSeat = prompt("Enter seat number (cannot be empty):", span.innerText); if(newSeat === null) return; newSeat = newSeat.trim(); } while(newSeat === '');
    span.innerText = newSeat; seatInput.value = newSeat;
    var extra = prompt("Enter extra info:", extraInput.value);
    if(extra !== null){
        extraInput.value = extra;
        var oldTooltip = el.querySelector('.seat-tooltip'); if(oldTooltip) oldTooltip.remove();
        if(extra.trim() !== '') {
            var tooltip = document.createElement('span');
            tooltip.className = 'seat-tooltip';
            tooltip.title = extra;
            tooltip.style.position = 'absolute';
            tooltip.style.top = '2px';
            tooltip.style.right = '2px';
            tooltip.style.fontSize = '12px';
            tooltip.style.height = '20px';
            tooltip.style.color = '#fff';
            tooltip.style.cursor = 'pointer';
            tooltip.innerHTML = '&#9432;';
            el.appendChild(tooltip);
        }
    }
}

function updateLayoutByBusType() {
    var busTypeSelect = document.querySelector('select[name="busType"]');
    var layoutSelect = document.querySelector('select[name="seatLayoutVersion"]');
    var totalSeatsInput = document.getElementById('totalSeatsInput');
    if(busTypeSelect.value.includes('Seater')) { layoutSelect.value='seater'; totalSeatsInput.value=50; }
    else { layoutSelect.value='2x1'; totalSeatsInput.value=38; }
    generateSeatLayout();
}

function updateBusTypeByLayout() {
    var busTypeSelect = document.querySelector('select[name="busType"]');
    var layoutSelect = document.querySelector('select[name="seatLayoutVersion"]');
    var totalSeatsInput = document.getElementById('totalSeatsInput');
    switch(layoutSelect.value){
        case 'seater': busTypeSelect.value='Seater'; totalSeatsInput.value=50; break;
        case '2x1': case '2x2': busTypeSelect.value='Sleeper'; totalSeatsInput.value=(layoutSelect.value==='2x1'?38:48); break;
    }
    generateSeatLayout();
}

document.addEventListener('DOMContentLoaded', function(){
    var busTypeSelect = document.querySelector('select[name="busType"]');
    var layoutSelect = document.querySelector('select[name="seatLayoutVersion"]');
    busTypeSelect.addEventListener('change', updateLayoutByBusType);
    layoutSelect.addEventListener('change', updateBusTypeByLayout);
    updateLayoutByBusType();

    var container = document.getElementById('seatLayoutContainer');
    var errorContainer = document.createElement('div');
    errorContainer.id = 'seatError';
    errorContainer.style.color = 'red';
    errorContainer.style.marginBottom = '10px';
    container.parentNode.insertBefore(errorContainer, container);
    <c:if test="${not empty org.springframework.validation.BindingResult.busDTO.globalErrors}">
    errorContainer.innerText = "<c:forEach var='err' items='${org.springframework.validation.BindingResult.busDTO.globalErrors}'>${err.defaultMessage}</c:forEach>";
</c:if>

    document.querySelector('form').addEventListener('submit', function(e){
        var seatInputs = document.querySelectorAll('.seat-number-input');
        var values = [];
        var hasDuplicate=false;
        seatInputs.forEach(function(inp){
            if(values.includes(inp.value.trim())) hasDuplicate=true;
            values.push(inp.value.trim());
        });
        if(hasDuplicate){
            e.preventDefault();
            errorContainer.innerText="Duplicate seat numbers detected!";
            window.scrollTo(0, container.offsetTop-50);
        }
    });
});
</script>

</body>
</html>
