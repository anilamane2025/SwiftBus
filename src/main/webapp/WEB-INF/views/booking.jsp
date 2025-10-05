<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SwiftBus – Book Tickets</title>
  <link rel="icon" type="image/png" href="<c:url value='/assets/images/favicon.png' />" sizes="16x16">
    <link rel="stylesheet" href="<c:url value='/assets/css/remixicon.css' />">
  <!-- BootStrap css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/bootstrap.min.css' />">
  <!-- Apex Chart css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/apexcharts.css' />">
  <!-- Data Table css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/dataTables.min.css' />">
  <!-- Text Editor css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor-katex.min.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor.atom-one-dark.min.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor.quill.snow.css' />">
  <!-- Date picker css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/flatpickr.min.css' />">
  <!-- Calendar css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/full-calendar.css' />">
  <!-- Vector Map css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/jquery-jvectormap-2.0.5.css' />">
  <!-- Popup css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/magnific-popup.css' />">
  <!-- Slick Slider css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/slick.css' />">
  <!-- main css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
  <style>
    /* small helper styles to look nice */
    .search-card { max-width: 980px; margin: 0 auto; padding: 20px; background: rgba(255,255,255,0.95); border-radius: 10px; box-shadow: 0 6px 18px rgba(0,0,0,.06); }
    .autocomplete-suggestions { position: absolute; z-index: 1000; width:100%; background:#fff; border:1px solid #ddd; max-height:220px; overflow:auto; }
    .autocomplete-suggestion { padding:8px 10px; cursor:pointer; }
    .autocomplete-suggestion:hover { background:#f0f0f0; }
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
					<a href="<c:url value='/login' />"
						class="text-primary-600 fw-semibold">Sign In</a> | 
            <a href="<c:url value='/sign-up' />"
						class="text-primary-600 fw-semibold">Sign Up</a>
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

   <c:if test="${not empty bookingId}">
    <div class="alert alert-success bg-success-100 text-success-600 border-success-600 border-start-width-4-px px-24 py-13 mb-4 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
        <div class="d-flex align-items-center gap-2">
            <iconify-icon icon="mingcute:check-line" class="icon text-xl"></iconify-icon>
            Booking successful! Your Booking ID is <strong>${bookingId}</strong>.
        </div>
        <button class="remove-button text-danger-600 text-xxl line-height-1">
                        <iconify-icon icon="iconamoon:sign-times-light" class="icon">X</iconify-icon>
                    </button>
    </div>
</c:if>
<!-- SEARCH FORM -->
<section class="hero position-relative d-flex align-items-center justify-content-center" 
    style="background: url('<c:url value="/assets/images/hero-bus.png" />') center/cover no-repeat; min-height: 70vh;">
    <div class="bg-dark bg-opacity-50 position-absolute top-0 start-0 w-100 h-100"></div>
    
    <div class="container position-relative text-center">
        <h1 class="display-4 fw-bold mb-4 text-white">Find Your Perfect Bus Ride</h1>
        
        <div >
        <c:if test="${not empty error}">
                <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between"
                     role="alert">
                    <div class="d-flex align-items-center gap-2">
                        <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                        ${error}
                    </div>
                    <button class="remove-button text-danger-600 text-xxl line-height-1">
                        <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                    </button>
                </div>
            </c:if>
         
            
        
      <form id="searchForm" action="<c:url value='/booking/search'/>" method="get" class="row g-3 justify-content-center bg-white p-4 radius-16 shadow-lg">
        <div class="row g-3 align-items-end">
          <div class="col-md-4 position-relative">
            <label class="form-label fw-semibold">From</label>
            <input id="fromInput" name="from" class="form-control" autocomplete="off" placeholder="City or stop name" required/>
            
            <div id="fromSuggestions" class="autocomplete-suggestions d-none"></div>
          </div>

          <div class="col-md-4 position-relative">
            <label class="form-label fw-semibold">To</label>
            <input id="toInput" name="to" class="form-control" autocomplete="off" placeholder="City or stop name" required/>
            
            <div id="toSuggestions" class="autocomplete-suggestions d-none"></div>
          </div>

          <div class="col-md-2">
            <label class="form-label fw-semibold">Journey Date</label>
            <input id="dateInput" name="date" type="date" class="form-control" min="<%= java.time.LocalDate.now() %>" required/>
          </div>

          <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100 radius-12">Search</button>
          </div>
        </div>
      </form>
    </div>
    </div>
    
</section>

<!-- ================= FOOTER ================= -->
<footer class="py-40 bg-dark text-white text-center">
  <p class="mb-0">© <%= java.time.Year.now() %> SwiftBus. All rights reserved.</p>
</footer>

<!-- JS assets -->
<script src="<c:url value='/assets/js/lib/jquery-3.7.1.min.js' />"></script>
<script src="<c:url value='/assets/js/lib/bootstrap.bundle.min.js' />"></script>
<script src="<c:url value='/assets/js/lib/slick.min.js' />"></script>
<script src="<c:url value='/assets/js/app.js' />"></script>
<script>
(function(){
  // helper
  function debounce(fn, delay){ let t; return function(){ clearTimeout(t); t = setTimeout(()=>fn.apply(this, arguments), delay); }; }

  // set min date to today
  const dateInput = document.getElementById('dateInput');
  const today = new Date().toISOString().slice(0,10);
  dateInput.min = today;
  dateInput.value = today;

  // autocomplete function
 function wireAutocomplete(inputId, suggestionsId){
    const input = document.getElementById(inputId);
    const box = document.getElementById(suggestionsId);

    input.addEventListener('input', debounce(function(){
      const q = this.value.trim();
      
      if (q.length < 2) { box.classList.add('d-none'); box.innerHTML=''; return; }
      fetch('<c:url value="/api/stops/search"/>?q=' + encodeURIComponent(q))
        .then(r => r.json())
        .then(items => {
          if (!items || items.length===0) { box.classList.add('d-none'); box.innerHTML=''; return; }
          box.innerHTML = items.map(it => '<div class="autocomplete-suggestion" data-name="'+it.displayName+'">'+it.displayName+'</div>').join('');
          box.classList.remove('d-none');
        });
    }, 220));

    // click suggestion
    box.addEventListener('click', function(e){
      const el = e.target.closest('.autocomplete-suggestion');
      if (!el) return;
      const name = el.getAttribute('data-name');
      input.value = name;
      box.classList.add('d-none');
    });

    // hide on blur (small timeout to allow click)
    input.addEventListener('blur', () => setTimeout(()=>box.classList.add('d-none'), 200));
  }

 wireAutocomplete('fromInput','fromSuggestions');
 wireAutocomplete('toInput','toSuggestions');

//prevent selecting same from / to
  document.getElementById('searchForm').addEventListener('submit', function(e){
      const fromCity = document.getElementById('fromInput').value.trim();
      const toCity = document.getElementById('toInput').value.trim();

      if (!fromCity || !toCity) {
          e.preventDefault();
          alert('Please choose both FROM and TO cities.');
          return;
      }

      if (fromCity.toLowerCase() === toCity.toLowerCase()) {
          e.preventDefault();
          alert('From and To cannot be the same city.');
      }
  });

})();

$('.remove-button').on('click', function () {
    $(this).closest('.alert').addClass('d-none')
});
</script>
</body>
</html>
