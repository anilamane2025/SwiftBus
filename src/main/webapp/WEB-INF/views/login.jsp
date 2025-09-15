<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!-- meta tags and other links -->
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Signin - Swift Bus</title>
  <link rel="icon" type="image/png" href="<c:url value='/assets/images/favicon.png' />" sizes="16x16">
  <!-- remix icon font css  -->
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
</head>
  <body>

<section class="auth bg-base d-flex flex-wrap">  
    <div class="auth-left d-lg-block d-none">
        <div class="d-flex align-items-center flex-column h-100 justify-content-center">
            <img src="assets/images/auth/auth-img.png" alt="">
        </div>
    </div>
    <div class="auth-right py-32 px-24 d-flex flex-column justify-content-center">
        <div class="max-w-464-px mx-auto w-100">
            <div>
                <a href="index.html" class="mb-40 max-w-290-px">
                    <img src="<c:url value='/assets/images/logo.png' />" alt="">
                </a>
                <h4 class="mb-12">Sign In to your Account</h4>
                <p class="mb-32 text-secondary-light text-lg">Welcome back! please enter your detail</p>
                <c:if test="${not empty param.error}">
				    <div class="mb-5 alert alert-danger bg-danger-100 text-danger-600 border-danger-100 px-24 py-11 mb-0 fw-semibold text-lg radius-8" role="alert">
                        <div class="d-flex align-items-center justify-content-between text-lg">
                            Invalid username or password.
                            <button class="remove-button text-danger-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                        </div>
                    </div>
				</c:if>
				<c:if test="${not empty param.logout}">
				    <div class="mb-5 alert alert-success bg-success-100 text-success-600 border-success-100 px-24 py-11 mb-0 fw-semibold text-lg radius-8" role="alert">
                        <div class="d-flex align-items-center justify-content-between text-lg">
                            You have been logged out.
                            <button class="remove-button text-success-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                        </div>
                    </div>
				</c:if>
				<c:if test="${not empty param.registered}">
				    <div class="mb-5 alert alert-success bg-success-100 text-success-600 border-success-100 px-24 py-11 mb-0 fw-semibold text-lg radius-8" role="alert">
                        <div class="d-flex align-items-center justify-content-between text-lg">
                            You have successfully registered.
                            <button class="remove-button text-success-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                        </div>
                    </div>
				</c:if>
            </div>
            <form action="<c:url value='/login' />" method="post">
                <div class="icon-field mb-16">
                    <span class="icon top-50 translate-middle-y">
                        <iconify-icon icon="mage:email"></iconify-icon>
                    </span>
                    <input type="text" name="username" class="form-control h-56-px bg-neutral-50 radius-12" placeholder="Email & Phone Number" required="required">
                </div>
                <div class="position-relative mb-20">
                    <div class="icon-field">
                        <span class="icon top-50 translate-middle-y">
                            <iconify-icon icon="solar:lock-password-outline"></iconify-icon>
                        </span> 
                        <input type="password" name="password" class="form-control h-56-px bg-neutral-50 radius-12" id="your-password" placeholder="Password" required="required">
                    </div>
                    <span class="toggle-password ri-eye-line cursor-pointer position-absolute end-0 top-50 translate-middle-y me-16 text-secondary-light" data-toggle="#your-password"></span>
                </div>
                <div class="">
                    <div class="d-flex justify-content-between gap-2">
                        <div class="form-check style-check d-flex align-items-center">
                            <input class="form-check-input border border-neutral-300" type="checkbox" value="" id="remeber">
                            <label class="form-check-label" for="remeber">Remember me </label>
                        </div>
                        <a href="javascript:void(0)" class="text-primary-600 fw-medium">Forgot Password?</a>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary text-sm btn-sm px-12 py-16 w-100 radius-12 mt-32"> Sign In</button>

                
                <div class="mt-32 text-center text-sm">
                    <p class="mb-0">Don’t have an account? <a href="<c:url value='/sign-up' />" class="text-primary-600 fw-semibold">Sign Up</a></p>
                </div>
                
            </form>
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
  <script src="<c:url value='/assets/js/lib/jquery-jvectormap-2.0.5.min.js' />"></script>
  <script src="<c:url value='/assets/js/lib/jquery-jvectormap-world-mill-en.js ' />"></script>
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
<script>    
    $('.remove-button').on('click', function() {
        $(this).closest('.alert').addClass('d-none')
    }); 
</script>

</body>
</html>
