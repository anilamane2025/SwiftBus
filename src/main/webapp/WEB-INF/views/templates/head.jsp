<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SwiftBus – Book Your Ride Swiftly</title>
  <link rel="icon" type="image/png" href="<c:url value='/assets/images/favicon.png'/>" sizes="16x16">
  <!-- remix icon font css  -->
  <link rel="stylesheet" href="<c:url value='/assets/css/remixicon.css'/>">
  <!-- BootStrap css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/bootstrap.min.css'/>">
  <!-- Data Table css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/dataTables.min.css'/>">
  <!-- Text Editor css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor-katex.min.css'/>">
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor.atom-one-dark.min.css'/>">
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/editor.quill.snow.css'/>">
  <!-- Date picker css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/flatpickr.min.css'/>">
  <!-- Vector Map css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/jquery-jvectormap-2.0.5.css'/>">
  <!-- Popup css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/magnific-popup.css'/>">
  <!-- Slick Slider css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/lib/slick.css'/>">
  <!-- main css -->
  <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
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