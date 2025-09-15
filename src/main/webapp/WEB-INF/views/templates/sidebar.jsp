<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>        
<!DOCTYPE html>
<aside class="sidebar">
  <button type="button" class="sidebar-close-btn">
    <iconify-icon icon="radix-icons:cross-2"></iconify-icon>
  </button>
  <div>
    <a href="index.html" class="sidebar-logo">
      <img src="<c:url value='/assets/images/logo.png'/>" alt="site logo" class="light-logo">
      <img src="<c:url value='/assets/images/logo-light.png'/>" alt="site logo" class="dark-logo">
      <img src="<c:url value='/assets/images/logo-icon.png'/>" alt="site logo" class="logo-icon">
    </a>
  </div>
  <div class="sidebar-menu-area">
    <ul class="sidebar-menu" id="sidebar-menu">
      
     
      <sec:authorize access="hasRole('ADMIN')">
      <li>
        <a href="<c:url value='/admin/home' />">
          <iconify-icon icon="solar:home-smile-angle-outline" class="menu-icon"></iconify-icon>
          <span>Dashboard</span>
        </a>
        
      </li> 
      
      <li class="dropdown">
        <a href="javascript:void(0)">
          <iconify-icon icon="flowbite:users-group-outline" class="menu-icon"></iconify-icon>
          <span>Agents</span> 
        </a>
        <ul class="sidebar-submenu">
          <li>
            <a href="users-list.html"><i class="ri-circle-fill circle-icon text-primary-600 w-auto"></i> Agents List</a>
          </li>
          <li>
            <a href="add-user.html"><i class="ri-circle-fill circle-icon text-info-main w-auto"></i> Add Agents</a>
          </li>
          <li>
            <a href="view-profile.html"><i class="ri-circle-fill circle-icon text-danger-main w-auto"></i> View Profile</a>
          </li>
        </ul>
      </li>
      
      <li class="dropdown">
        <a href="javascript:void(0)">
          <iconify-icon icon="flowbite:users-group-outline" class="menu-icon"></iconify-icon>
          <span>Users</span> 
        </a>
        <ul class="sidebar-submenu">
          <li>
            <a href="users-list.html"><i class="ri-circle-fill circle-icon text-primary-600 w-auto"></i> Users List</a>
          </li>
          <li>
            <a href="view-profile.html"><i class="ri-circle-fill circle-icon text-danger-main w-auto"></i> View Profile</a>
          </li>
        </ul>
      </li>
      <li class="dropdown">
        <a href="javascript:void(0)">
          <i class="ri-user-settings-line text-xl me-14 d-flex w-auto"></i>
          <span>Role &amp; Access</span> 
        </a>
        <ul class="sidebar-submenu show">
          <li>
            <a href="role-access.html"><i class="ri-circle-fill circle-icon text-primary-600 w-auto"></i> Role &amp; Access</a>
          </li>
          <li>
            <a href="/permissions"><i class="ri-circle-fill circle-icon text-warning-main w-auto"></i> Permission</a>
          </li>
        </ul>
      </li>

	</sec:authorize>
    

    <sec:authorize access="hasRole('AGENT')">
       <li>
        <a href="<c:url value='/agent/home' />">
          <iconify-icon icon="solar:home-smile-angle-outline" class="menu-icon"></iconify-icon>
          <span>Dashboard</span>
        </a>
        
      </li> 
    </sec:authorize>

    <sec:authorize access="hasRole('PASSENGER')">
		<li>
        <a href="<c:url value='/passenger/home' />">
          <iconify-icon icon="solar:home-smile-angle-outline" class="menu-icon"></iconify-icon>
          <span>Dashboard</span>
        </a>
        
      </li> 
	</sec:authorize>
      <li>
        <a href="gallery.html">
          <iconify-icon icon="solar:gallery-wide-linear" class="menu-icon"></iconify-icon>
          <span>Reports</span> 
        </a>
      </li>
      
      
    </ul>
  </div>
</aside>