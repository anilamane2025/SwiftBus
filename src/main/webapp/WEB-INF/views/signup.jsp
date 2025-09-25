<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- meta tags and other links -->
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Signup - Swift Bus</title>
<link rel="icon" type="image/png"
	href="<c:url value='/assets/images/favicon.png' />" sizes="16x16">
<!-- remix icon font css  -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/remixicon.css' />">
<!-- BootStrap css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/bootstrap.min.css' />">
<!-- Apex Chart css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/apexcharts.css' />">
<!-- Data Table css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/dataTables.min.css' />">
<!-- Text Editor css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/editor-katex.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/editor.atom-one-dark.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/editor.quill.snow.css' />">
<!-- Date picker css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/flatpickr.min.css' />">
<!-- Calendar css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/full-calendar.css' />">
<!-- Vector Map css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/jquery-jvectormap-2.0.5.css' />">
<!-- Popup css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/magnific-popup.css' />">
<!-- Slick Slider css -->
<link rel="stylesheet"
	href="<c:url value='/assets/css/lib/slick.css' />">
<!-- main css -->
<link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
<style>
.invalid-feedback {
    display: block !important;   /* override Bootstrap hidden style */
    width: 100%;
    margin-top: .25rem;
    font-size: .875em;
    
    color: var(--bs-form-invalid-color);
}
</style>
</head>
<body>

	<section class="auth bg-base d-flex flex-wrap">
		<div class="auth-left d-lg-block d-none">
			<div
				class="d-flex align-items-center flex-column h-100 justify-content-center">
				<img src="<c:url value='/assets/images/auth/auth-img.png' /> "
					alt="">
			</div>
		</div>
		<div
			class="auth-right py-32 px-24 d-flex flex-column justify-content-center">
			<div class="max-w-464-px mx-auto w-100">
				<div>
					<a href="index.html" class="mb-40 max-w-290-px"> <img
						src="<c:url value='/assets/images/logo.png' />" alt="">
					</a>
					<h4 class="mb-12">Sign Up to your Account</h4>
					<p class="mb-32 text-secondary-light text-lg">Welcome back!
						please enter your detail</p>
				</div>
				
				<form:form method="post" modelAttribute="registrationDTO"
					action="/register">
					<div class="row">
						<!-- First Name -->
						<div class="col-sm-6">
							<div class="mb-20">
								<label class="form-label">First Name</label>
								<form:input path="firstName" cssClass="form-control radius-8" />
								<form:errors path="firstName" cssClass="invalid-feedback" />
							</div>
						</div>

						<!-- Last Name -->
						<div class="col-sm-6">
							<div class="mb-20">
								<label class="form-label">Last Name</label>
								<form:input path="lastName" cssClass="form-control radius-8" />
								<form:errors path="lastName" cssClass="invalid-feedback" />
							</div>
						</div>

						<!-- Username -->
						<div class="col-sm-6">
							<div class="mb-20">
								<label class="form-label">Username</label>
								<form:input path="username" cssClass="form-control radius-8" />
								<form:errors path="username" cssClass="invalid-feedback" />
							</div>
						</div>
						<!-- Phone -->
						<div class="col-sm-6">
							<div class="mb-20">
								<label class="form-label">Phone Number</label>
								<form:input path="phoneNumber" cssClass="form-control radius-8" />
								<%-- <form:input path="phoneNumber" cssClass="form-control radius-8 ${phoneInvalid ? 'is-invalid' : ''}"/> --%>
								<form:errors path="phoneNumber" cssClass="invalid-feedback" />
							</div>
						</div>
						
						<!-- Gender -->
						<div class="mb-20">
							<label class="form-label">Gender</label>
							<form:select path="gender" cssClass="form-control radius-8">
								<form:option value="">-- Select Gender --</form:option>
								<form:option value="M">Male</form:option>
								<form:option value="F">Female</form:option>
							</form:select>
							<form:errors path="gender" cssClass="invalid-feedback" />
						</div>
						
						<!-- State -->
				        <div class="col-sm-6">
				            <div class="mb-20">
				                <label for="state" class="form-label">State</label>
				                <form:select path="cityState" id="state" cssClass="form-control">
				                    <form:option value="">-- Select State --</form:option>
				                    <c:forEach var="state" items="${states}">
				                        <form:option value="${state}">${state}</form:option>
				                    </c:forEach>
				                </form:select>
				                <form:errors path="cityState" cssClass="invalid-feedback"/>
				            </div>
				        </div>
				
				        <!-- City -->
				        <div class="col-sm-6">
				            <div class="mb-20">
				                <label for="city" class="form-label">City</label>
				                <form:select path="cityId" id="city" cssClass="form-control">
				                    <form:option value="">-- Select City --</form:option>
				                </form:select>
				                <form:errors path="cityId" cssClass="invalid-feedback"/>
				            </div>
				        </div>
						
						<!-- Password -->
						<div class="col-sm-6">
							<div class="mb-20">
								<div class="position-relative ">
								
									<label class="form-label">Password</label>
								<form:password path="password"  cssClass="form-control h-56-px bg-neutral-50 radius-8" />
								
								<span
									class="toggle-password ri-eye-line cursor-pointer position-absolute end-0  translate-middle-y me-16 text-secondary-light"
									data-toggle="#password"></span>
									<form:errors path="password" cssClass="invalid-feedback" />
								
								</div>
							</div>
						</div>
						
						<!-- Confirm Password -->
						<div class="col-sm-6">
							<div class="mb-20">
							<div class="position-relative ">
							<div>
								<label class="form-label">Confirm Password</label>
								<form:password path="confirmPassword"
									cssClass="form-control h-56-px bg-neutral-50 radius-8" />
									</div>
								<span
									class="toggle-password ri-eye-line cursor-pointer position-absolute end-0  translate-middle-y me-16 text-secondary-light"
									data-toggle="#confirmPassword"></span>
								<form:errors path="confirmPassword" cssClass="invalid-feedback" />
							</div>
							</div>
						</div>

						<c:if test="${not empty passwordError}">
							<div class="invalid-feedback">${passwordError}</div>
						</c:if>
						
					</div>
					
					<div class="">
						<div class="d-flex justify-content-between gap-2">
							<div class="form-check style-check d-flex align-items-start">
								<label class="form-check-label text-sm" for="condition">
									By creating an account means you agree to the <a
									href="javascript:void(0)" class="text-primary-600 fw-semibold">Terms
										and Conditions</a> and our <a href="javascript:void(0)"
									class="text-primary-600 fw-semibold">Privacy Policy</a>
								</label>
							</div>

						</div>
					</div>
					
					<button type="submit" class="btn btn-primary radius-12 mt-32 w-100">
						Sign Up</button>


					<div class="mt-32 text-center text-sm">
						<p class="mb-0">
							Already have an account? <a href="<c:url value='/login' />"
								class="text-primary-600 fw-semibold">Sign In</a>
						</p>
					</div>

				</form:form>
				
				</div>
		</div>
	</section>

	<!-- jQuery library js -->
	<script src="<c:url value='/assets/js/lib/jquery-3.7.1.min.js' />"></script>
	<!-- Bootstrap js -->
	<script src="<c:url value='/assets/js/lib/bootstrap.bundle.min.js' />"></script>
	<!-- Apex Chart js -->
	<script src="<c:url value='/assets/js/lib/apexcharts.min.js' />"></script>
	<!-- Data Table js -->
	<script src="<c:url value='/assets/js/lib/dataTables.min.js' />"></script>
	<!-- Iconify Font js -->
	<script src="<c:url value='/assets/js/lib/iconify-icon.min.js' />"></script>
	<!-- jQuery UI js -->
	<script src="<c:url value='/assets/js/lib/jquery-ui.min.js' />"></script>
	<!-- Vector Map js -->
	<script
		src="<c:url value='/assets/js/lib/jquery-jvectormap-2.0.5.min.js' />"></script>
	<script
		src="<c:url value='/assets/js/lib/jquery-jvectormap-world-mill-en.js ' />"></script>
	<!-- Popup js -->
	<script src="<c:url value='/assets/js/lib/magnifc-popup.min.js' />"></script>
	<!-- Slick Slider js -->
	<script src="<c:url value='/assets/js/lib/slick.min.js' />"></script>
	<!-- main js -->
	<script src="<c:url value='/assets/js/app.js' />"></script>

	<script>
		// ================== Password Show Hide Js Start ==========
		function initializePasswordToggle(toggleSelector) {
			$(toggleSelector).on('click', function() {
				$(this).toggleClass("ri-eye-off-line");
				var input = $($(this).attr("data-toggle"));
				if (input.attr("type") === "password") {
					input.attr("type", "text");
				} else {
					input.attr("type", "password");
				}
			});
		}
		// Call the function
		initializePasswordToggle('.toggle-password');
		// ========================= Password Show Hide Js End ===========================
	</script>
<script type="text/javascript">
    $(document).ready(function () {
        var currentState = "${registrationDTO.cityState}";
        var currentCityId = "${registrationDTO.cityId}";

        // Pre-select current state
        if (currentState) {
            $("#state").val(currentState).trigger("change");
        }

        // When state changes, load cities
        $("#state").change(function () {
            var state = $(this).val();
            if (state) {
                $.ajax({
                    url: "<c:url value='/cities/cities-by-state'/>",
                    type: "GET",
                    data: { state: state },
                    success: function (cities) {
                        var $city = $("#city");
                        $city.empty();
                        $city.append('<option value="">-- Select City --</option>');
                        $.each(cities, function (index, city) {
                            var selected = (city.cityId == currentCityId) ? "selected" : "";
                            $city.append('<option value="' + city.cityId + '" ' + selected + '>'
                                + city.cityName + '</option>');
                        });
                    }
                });
            } else {
                $("#city").empty().append('<option value="">-- Select City --</option>');
            }
        });

        // trigger initial load if user has city
        if (currentState) {
            $("#state").trigger("change");
        }
    });
</script>
</body>
</html>