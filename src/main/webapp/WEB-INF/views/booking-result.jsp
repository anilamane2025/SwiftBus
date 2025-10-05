<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <title>SwiftBus - Search results</title>

  <link rel="stylesheet" href="<c:url value='/assets/css/lib/bootstrap.min.css'/>"/>
  <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>"/>
  <style>
    body { background:#f3f7fb; }
    .search-summary { background:#fff; padding:16px; border-radius:8px; margin-bottom:12px; }
    .filters { background:#fff; padding:14px; border-radius:8px; margin-bottom:12px; }
    .promo-strip { background:#fff; padding:8px; border-radius:8px; margin-bottom:12px; display:flex; gap:10px; overflow:auto;}
    .bus-card { background:#fff; padding:14px; border-radius:8px; margin-bottom:12px; }
    .bus-price { font-weight:700; color:#0f62e3; font-size:1.15rem }
    .select-seat { background:#0077d9; color:#fff; padding:8px 12px; border-radius:20px; text-decoration:none; }
    .tab-content { background:#f7fbff; padding:10px; border-radius:8px; margin-top:10px; }
    .pickup-list, .drop-list { list-style:none; padding:0; margin:0; max-height:160px; overflow:auto; }
    .pickup-list li, .drop-list li { padding:6px 0; border-bottom:1px dashed #e8f0fb; }
    .result-count { font-weight:600; }
    .bus-route { color: #333; font-size: 0.9rem; margin-top: 6px; }
	.bus-route b { color: #0077d9; }
    
  </style>
</head>
<body>

<!-- ================= HEADER ================= -->
<header class="bg-base shadow-sm px-20 py-12 d-flex align-items-center justify-content-between">
    <a href="<c:url value='/' />" class="d-flex align-items-center">
        <img src="<c:url value='/assets/images/logo.png' />" alt="SwiftBus Logo" height="40">
    </a>
    <nav class="d-flex align-items-center">
        <a href="<c:url value='/' />" class="mx-3 fw-semibold">Home</a>
        <a href="<c:url value='/booking' />" class="mx-3 fw-semibold">Book Tickets</a>
        <a href="<c:url value='/about' />" class="mx-3 fw-semibold">About Us</a>
        <a href="<c:url value='/contact' />" class="mx-3 fw-semibold">Contact</a>
        <div class="ms-4">
            <security:authorize access="isAnonymous()">
            <a href="<c:url value='/login' />" class="text-primary-600 fw-semibold">Sign In</a> | 
            <a href="<c:url value='/sign-up' />" class="text-primary-600 fw-semibold">Sign Up</a>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
					<div class="dropdown">
						<button
							class="d-flex justify-content-center align-items-center rounded-circle"
							type="button" data-bs-toggle="dropdown">
							<img src="<c:url value='/assets/images/user.png'/>" alt="image"
								class="w-40-px h-40-px object-fit-cover rounded-circle">
						</button>
						<div class="dropdown-menu to-top dropdown-menu-sm">
							<div
								class="py-12 px-16 radius-8 bg-primary-50 mb-16 d-flex align-items-center justify-content-between gap-2">

								<div>
									<h6 class="text-lg text-primary-light fw-semibold mb-2">
										<security:authentication property="principal.fullName" />
									</h6>
									<a href="<c:url value='/dashboard' />"
						class="text-primary-600 fw-semibold">Dashboard</a> 
								</div>
							</div>
						</div>
					</div>
				</security:authorize>
        </div>
    </nav>
</header>

<div class="container my-3">
  <!-- Top search summary -->
  <div class="search-summary d-flex justify-content-between align-items-center flex-wrap gap-2">
    <div class="d-flex gap-4 align-items-center flex-wrap">
      <div><strong>From</strong><div class="text-muted small">${from}</div></div>
      <div><strong>To</strong><div class="text-muted small">${to}</div></div>
      <div><strong>Departure</strong><div class="text-muted small">${date}</div></div>
    </div>

    <div class="d-flex gap-2 align-items-center">
      <div class="result-count" id="resultCount">${resultCount} buses found</div>
      <select id="sortSelect" class="form-select form-select-sm">
        <option value="recommended">Sort: Recommended</option>
        <option value="price-asc">Price: Low to High</option>
        <option value="price-desc">Price: High to Low</option>
        <option value="departure-asc">Departure: Earliest</option>
      </select>
      <a href="<c:url value='/booking'/>" class="btn btn-outline-primary btn-sm">Edit Search</a>
    </div>
  </div>

  <!-- promo strip -->
  <div class="promo-strip">
    <div class="badge bg-light p-2">SmartBus — Easy ticket booking</div>
    <div class="badge bg-light p-2">New Agents Deals</div>
  </div>

  <div class="row">
    <!-- LEFT: Filters -->
    <div class="col-md-3">
      <div class="filters">
        <h6>Filters</h6>

        <div class="mb-3">
          <label class="form-label small">Bus Type</label>
          <div class="form-check">
            <input class="form-check-input filter-bus-type" type="checkbox" value="Sleeper" id="ft-sleeper">
            <label class="form-check-label" for="ft-sleeper">Sleeper</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-bus-type" type="checkbox" value="Seater" id="ft-seater">
            <label class="form-check-label" for="ft-seater">Seater</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-bus-type" type="checkbox" value="AC Sleeper" id="ft-ac-sleeper">
            <label class="form-check-label" for="ft-ac-sleeper">AC Sleeper</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-bus-type" type="checkbox" value="AC Seater" id="ft-ac-seater">
            <label class="form-check-label" for="ft-ac-seater">AC Seater</label>
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label small">Pickup time</label>
          <div class="form-check">
            <input class="form-check-input filter-time" type="checkbox" value="0-6" id="time-0-6">
            <label class="form-check-label" for="time-0-6">Midnight - 6 AM</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-time" type="checkbox" value="6-12" id="time-6-12">
            <label class="form-check-label" for="time-6-12">6 AM - 12 PM</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-time" type="checkbox" value="12-18" id="time-12-18">
            <label class="form-check-label" for="time-12-18">12 PM - 6 PM</label>
          </div>
          <div class="form-check">
            <input class="form-check-input filter-time" type="checkbox" value="18-24" id="time-18-24">
            <label class="form-check-label" for="time-18-24">6 PM - Midnight</label>
          </div>
        </div>

        <div class="d-flex gap-2">
          <button id="applyFilters" class="btn btn-primary btn-sm">Apply</button>
          <button id="clearFilters" class="btn btn-outline-secondary btn-sm">Clear</button>
        </div>
      </div>
    </div>

    <!-- CENTER: Results -->
    <div class="col-md-9">
      <c:choose>
        <c:when test="${not empty trips}">
         

          <c:forEach var="trip" items="${trips}">
            <!-- attach data-* used by client filters/sort -->
            <c:set var="departureTime" value="${fn:toUpperCase(trip.departureTime)}" />
            
            <div class="bus-card" 
                 data-price="${trip.price}" 
                 data-bustype="${fn:escapeXml(trip.busType)}" 
                 data-seattype="${fn:escapeXml(trip.busName)}"
                 data-departure="${departureTime}">
              <div class="d-flex justify-content-between">
                <div style="flex:1">
                  <div class="d-flex align-items-center gap-3">
                    <div>
                      
                      <div class="text-muted small">${trip.busName} • ${trip.busType}</div>
                    </div>
                  </div>

                  <div class="d-flex gap-4 mt-3">
                    <div>
                      <div class="fw-bold">${trip.departureTime}</div>
                      <div class="text-muted small">Departure</div>
                    </div>
                    <div>
                      <div class="fw-bold">${trip.duration}</div>
                      <div class="text-muted small">Duration</div>
                    </div>
                    <div>
                      <div class="fw-bold">${trip.arrivalTime}</div>
                      <div class="text-muted small">Arrival</div>
                    </div>
                    <div>
                      <div class="fw-bold">${trip.availableSeats}</div>
                      <div class="text-muted small">Seats left</div>
                    </div>
                  </div>
                </div>

                <div style="text-align:right; min-width:170px;">
                  <div class="bus-price">₹<c:out value="${trip.price}"/></div>
                  <div class="text-muted small">Per seat</div>
                  <div class="mt-3">
                    <a class="select-seat" href="<c:url value='/booking/seat-select/${trip.id}/${trip.fareSegmentId}/${trip.busId}/' />">Select Seat</a>
                  </div>
                </div>
              </div>

              <!-- Expandable details: pickup/drop & cancellation -->
              <div class="mt-3">
                <div class="accordion" id="acc-${trip.id}">
                  <div class="accordion-item">
                    <h2 class="accordion-header" id="heading-${trip.id}">
                      <button class="accordion-button collapsed btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-${trip.id}" aria-expanded="false">
                        View details
                      </button>
                    </h2>
                    <div id="collapse-${trip.id}" class="accordion-collapse collapse" data-bs-parent="#acc-${trip.id}">
                      <div class="accordion-body">
	                      <c:if test="${not empty trip.routeStops}">
						  <div class="mt-2 text-muted small">
						    <strong>Bus route:</strong>
						    <span>
						      <c:forEach var="stop" items="${trip.routeStops}" varStatus="loop">
						        <c:choose>
						          <c:when test="${fn:toLowerCase(stop.stopName) == fn:toLowerCase(from) or fn:toLowerCase(stop.stopName) == fn:toLowerCase(to)}">
						            <b>${stop.stopName}</b>
						          </c:when>
						          <c:otherwise>
						            ${stop.stopName}
						          </c:otherwise>
						        </c:choose>
						        <c:if test="${!loop.last}"> → </c:if>
						      </c:forEach>
						    </span>
						  </div>
						  <hr class="m-2"/>
						</c:if>
						                      
                        <div class="row">
                          <div class="col-md-6">
                            <strong>Pick-up points</strong>
                            <ul class="pickup-list">
                              <c:forEach var="p" items="${trip.pickupPoints}">
                                <li><div class="d-flex justify-content-between"><div>${p.pointName}</div><div class="text-muted small">${p.landmark}</div></div></li>
                              </c:forEach>
                            </ul>
                          </div>
                          <div class="col-md-6">
                            <strong>Drop-off points</strong>
                            <ul class="drop-list">
                              <c:forEach var="d" items="${trip.dropPoints}">
                                <li><div class="d-flex justify-content-between"><div>${d.pointName}</div><div class="text-muted small">${d.landmark}</div></div></li>
                              </c:forEach>
                            </ul>
                          </div>
                        </div>

                        <div class="mt-3">
						  <strong>Policy</strong>
						  <ul class="text-muted small mb-0">
						    <c:forEach var="p" items="${policyList}">
						      <li>${p}</li>
						    </c:forEach>
						  </ul>
						</div>

                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div> <!-- end bus-card -->
          </c:forEach>
        </c:when>

        <c:otherwise>
          <div class="alert alert-warning">No buses found for selected route/date.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<script src="<c:url value='/assets/js/lib/jquery-3.7.1.min.js'/>"></script>
<script src="<c:url value='/assets/js/lib/bootstrap.bundle.min.js'/>"></script>
<script>
(function(){
  const items = Array.from(document.querySelectorAll('.bus-card'));
  const busTypeChecks = Array.from(document.querySelectorAll('.filter-bus-type'));
  const timeChecks = Array.from(document.querySelectorAll('.filter-time'));
  const applyBtn = document.getElementById('applyFilters');
  const clearBtn = document.getElementById('clearFilters');
  const sortSelect = document.getElementById('sortSelect');

  function parseDepartureMinutes(dep){
	  const m = dep.match(/(\d{1,2}):(\d{2})\s*(AM|PM)/i);
	  if(!m) return 9999;
	  let h = parseInt(m[1],10), min = parseInt(m[2],10);
	  let ampm = m[3].toUpperCase();
	  if(ampm==='PM' && h!==12) h += 12;
	  if(ampm==='AM' && h===12) h = 0;
	  return h*60 + min;
	}

  function applyFilters(){
    const selectedTypes = busTypeChecks.filter(c=>c.checked).map(c=>c.value.toLowerCase());
    const selectedTimes = timeChecks.filter(c=>c.checked).map(c=>c.value);

    items.forEach(card=>{
      let show = true;
      const bustype = (card.dataset.bustype||'').toLowerCase();

      if(selectedTypes.length){
        show = selectedTypes.some(t => bustype.includes(t.toLowerCase()));
      }
      if(show && selectedTimes.length){
        const dep = card.dataset.departure || '';
        const depMin = parseDepartureMinutes(dep);
        show = selectedTimes.some(range=>{
          const [lo,hi] = range.split('-').map(Number);
          return depMin >= lo * 60 && depMin < hi * 60; // range expressed in hours -> minutes
        });
      }
      card.style.display = show ? '' : 'none';
    });
    updateCount();
  }

  function clearFilters(){
    busTypeChecks.forEach(c=>c.checked=false);
    timeChecks.forEach(c=>c.checked=false);
    items.forEach(c=>c.style.display='');
    updateCount();
  }

  function updateCount(){
    const visible = items.filter(i => i.style.display !== 'none').length;
    document.getElementById('resultCount').textContent = visible + ' buses found';
  }

  applyBtn.addEventListener('click', applyFilters);
  clearBtn.addEventListener('click', clearFilters);

  sortSelect.addEventListener('change', function(){
    const val = this.value;
    const container = document.querySelector('.col-md-9');
    const visibleCards = Array.from(container.querySelectorAll('.bus-card')).filter(c=>c.style.display!=='none');

    let sorted;
    if(val === 'price-asc'){
      sorted = visibleCards.sort((a,b) => parseFloat(a.dataset.price) - parseFloat(b.dataset.price));
    } else if(val === 'price-desc'){
      sorted = visibleCards.sort((a,b) => parseFloat(b.dataset.price) - parseFloat(a.dataset.price));
    } else if(val === 'departure-asc'){
      sorted = visibleCards.sort((a,b) => parseDepartureMinutes(a.dataset.departure) - parseDepartureMinutes(b.dataset.departure));
    } else {
      return; // recommended -> keep original order
    }
    sorted.forEach(s => container.appendChild(s));
  });

  // initial
  updateCount();
})();
</script>
<footer class="py-40 bg-dark text-white text-center">
  <p class="mb-0">© <%= java.time.Year.now() %> SwiftBus. All rights reserved.</p>
</footer>

</body>
</html>
