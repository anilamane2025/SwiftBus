<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SwiftBus – Book Your Ride Swiftly</title>

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
</head>
<body>


	<div class="custom-bg">
		<div class="container container--xl">
			<div class="d-flex align-items-center justify-content-between py-24">
				<a href="index.html" class=""> <img
					src="<c:url value='/assets/images/logo.png' />" alt="">
				</a> <a href="<c:url value='/' />"
					class="btn btn-outline-primary-600 text-sm"> Go To Home </a>
			</div>

			
				<div class="pt-48 pb-35 text-center">
					<div class="mx-auto" style="max-width: 500px;">
						<img src="<c:url value='/assets/images/forbidden-img.png' />"
							alt="">
					</div>
					<div class="max-w-700-px mx-auto mt-40">
						<h3 class="mb-22 max-w-1000-px">Access Denied</h3>
						<p class="text-neutral-500 max-w-700-px text-lg">You don't
							have authorization to get to this page. If it's not too much
							trouble, contact your site executive to demand access.</p>
						<a href="<c:url value='/' />"
							class="btn btn-primary-600 px-32 py-16 flex-shrink-0 d-inline-flex align-items-center justify-content-center gap-8 mt-28">
							<i class="ri-home-4-line"></i> Go Back To Home
						</a>
					</div>
				</div>
			</div>
		</div>

		<!-- JS assets -->
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
</body>
</html>
