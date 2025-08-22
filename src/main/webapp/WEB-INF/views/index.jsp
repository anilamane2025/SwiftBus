<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SwiftBus – Book Your Ride Swiftly</title>
  <link rel="icon" type="image/png" href="<c:url value='/assets/images/favicon.png' />" sizes="16x16">
  <!-- remix icon font css  -->
  <link rel="stylesheet" href="<c:url value='/assets/css/remixicon.css' />">
  <!-- BootStrap css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/bootstrap.min.css' />">
  <!-- Slick Slider css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/slick.css' />">
  <!-- main css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
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
            <a href="<c:url value='/login' />" class="text-primary-600 fw-semibold">Sign In</a> | 
            <a href="<c:url value='/sign-up' />" class="text-primary-600 fw-semibold">Sign Up</a>
        </div>
    </nav>
</header>

<!-- ================= HERO ================= -->
<section class="hero position-relative text-white text-center d-flex align-items-center justify-content-center" 
    style="background: url('assets/images/hero-bus.png') center/cover no-repeat; min-height: 85vh;">
    <div class="bg-dark bg-opacity-50 position-absolute top-0 start-0 w-100 h-100"></div>
    <div class="container position-relative">
        <h1 class="display-3  fw-bold mb-3 text-white">Book Your Ride Swiftly</h1>
        <p class="lead mb-4">Fast, Easy & Reliable Bus Ticket Booking</p>
        <a href="<c:url value='/booking' />" class="btn btn-primary btn-lg radius-12">Book Now</a>
    </div>
</section>

<!-- ================= FEATURES ================= -->
<section class="py-80 bg-base">
  <div class="container text-center">
    <h2 class="fw-bold mb-40">Why Choose SwiftBus?</h2>
    <div class="row g-4">
      <div class="col-md-4">
        <i class="ri-time-line text-primary-600 fs-1 mb-3"></i>
        <h5 class="fw-semibold">Fast Booking</h5>
        <p class="text-secondary-light">Book tickets in just a few clicks without hassle.</p>
      </div>
      <div class="col-md-4">
        <i class="ri-shield-check-line text-success fs-1 mb-3"></i>
        <h5 class="fw-semibold">Safe & Reliable</h5>
        <p class="text-secondary-light">Trusted by thousands of daily travelers.</p>
      </div>
      <div class="col-md-4">
        <i class="ri-customer-service-2-line text-warning fs-1 mb-3"></i>
        <h5 class="fw-semibold">24/7 Support</h5>
        <p class="text-secondary-light">We are always here to assist you on your journey.</p>
      </div>
    </div>
  </div>
</section>

<!-- ================= IMAGE + TEXT SECTION ================= -->
<section class="py-80">
  <div class="container">
    <div class="row align-items-center g-5">
      <div class="col-md-6">
        <img src="<c:url value='/assets/images/bus-interior.png' /> " alt="Comfortable Travel" class="img-fluid radius-16 shadow">
      </div>
      <div class="col-md-6">
        <h2 class="fw-bold mb-3">Travel in Comfort</h2>
        <p class="text-secondary-light mb-4">
          Experience luxury seating, on-time departures, and the safest journeys with SwiftBus.
        </p>
        <a href="<c:url value='/about' />" class="btn btn-outline-primary radius-12">Learn More</a>
      </div>
    </div>
  </div>
</section>

<!-- ================= TESTIMONIALS ================= -->
<section class="py-80 bg-light">
  <div class="container text-center">
    <h2 class="fw-bold mb-40">What Our Customers Say</h2>
    <div class="testimonial-slider">
      <!-- Testimonial 1 -->
      <div class="p-4">
        <div class="card radius-16 shadow-sm p-4 h-100">
          <p class="text-secondary mb-3">"Booking with SwiftBus was so easy! The bus was clean and arrived on time. Highly recommend!"</p>
          <h6 class="fw-semibold mb-0">Rahul Sharma</h6>
          <small class="text-secondary-light">Frequent Traveler</small>
        </div>
      </div>
      <!-- Testimonial 2 -->
      <div class="p-4">
        <div class="card radius-16 shadow-sm p-4 h-100">
          <p class="text-secondary mb-3">"I love the comfort of the seats and the friendly service. Makes my daily commute stress-free."</p>
          <h6 class="fw-semibold mb-0">Priya Mehta</h6>
          <small class="text-secondary-light">Working Professional</small>
        </div>
      </div>
      <!-- Testimonial 3 -->
      <div class="p-4">
        <div class="card radius-16 shadow-sm p-4 h-100">
          <p class="text-secondary mb-3">"Affordable and reliable. I never worry about missing my bus with SwiftBus notifications."</p>
          <h6 class="fw-semibold mb-0">Amit Verma</h6>
          <small class="text-secondary-light">Student</small>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- ================= CTA ================= -->
<section class="py-80 bg-primary text-white text-center">
  <div class="container">
    <h2 class="fw-bold mb-4">Ready to Ride?</h2>
    <p class="lead mb-4">Book your tickets now and enjoy a hassle-free journey with SwiftBus.</p>
    <a href="<c:url value='/booking' />" class="btn btn-light btn-lg radius-12">Get Started</a>
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
  // Slick slider init
  $('.testimonial-slider').slick({
    slidesToShow: 2,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 4000,
    arrows: false,
    dots: true,
    responsive: [
      {
        breakpoint: 768,
        settings: { slidesToShow: 1 }
      }
    ]
  });
</script>

</body>
</html>
