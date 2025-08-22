<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<main class="dashboard-main">
  <div class="navbar-header">
  <div class="row align-items-center justify-content-between">
    <div class="col-auto">
      <div class="d-flex flex-wrap align-items-center gap-4">
        <button type="button" class="sidebar-toggle">
          <iconify-icon icon="heroicons:bars-3-solid" class="icon text-2xl non-active"></iconify-icon>
          <iconify-icon icon="iconoir:arrow-right" class="icon text-2xl active"></iconify-icon>
        </button>
        <button type="button" class="sidebar-mobile-toggle">
          <iconify-icon icon="heroicons:bars-3-solid" class="icon"></iconify-icon>
        </button>
        
      </div>
    </div>
    
    <div class="col-auto">
      <div class="d-flex flex-wrap align-items-center gap-3">
        
        <div class="dropdown">
          <button class="d-flex justify-content-center align-items-center rounded-circle" type="button" data-bs-toggle="dropdown">
            <img src="<c:url value='/assets/images/user.png'/>"  alt="image" class="w-40-px h-40-px object-fit-cover rounded-circle">
          </button>
          <div class="dropdown-menu to-top dropdown-menu-sm">
            <div class="py-12 px-16 radius-8 bg-primary-50 mb-16 d-flex align-items-center justify-content-between gap-2">
              
              <div>
    <h6 class="text-lg text-primary-light fw-semibold mb-2">
        <sec:authentication property="principal.fullName"/>
    </h6>

    <sec:authorize access="hasRole('ADMIN')">
        <span class="text-secondary-light fw-medium text-sm">Admin</span>
    </sec:authorize>

    <sec:authorize access="hasRole('AGENT')">
        <span class="text-secondary-light fw-medium text-sm">Agent</span>
    </sec:authorize>

    <sec:authorize access="hasRole('USER')">
        <span class="text-secondary-light fw-medium text-sm">User</span>
    </sec:authorize>
</div>
              <button type="button" class="hover-text-danger">
                <iconify-icon icon="radix-icons:cross-1" class="icon text-xl"></iconify-icon> 
              </button>
            </div>
            <ul class="to-top-list">
              <li>
                <a class="dropdown-item text-black px-0 py-8 hover-bg-transparent hover-text-primary d-flex align-items-center gap-3" href="view-profile.html"> 
                <iconify-icon icon="solar:user-linear" class="icon text-xl"></iconify-icon>  My Profile</a>
              </li>
              
              <li>
                <form action="<c:url value='/logout' />" method="post" class="dropdown-item text-black px-0 py-8 hover-bg-transparent hover-text-danger d-flex align-items-center gap-3">
                <iconify-icon icon="lucide:power" class="icon text-xl"></iconify-icon>
        <input  type="submit" value="Log Out"/>
    </form>
              </li>
            </ul>
          </div>
        </div><!-- Profile dropdown end -->
      </div>
    </div>
  </div>
</div> 