<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<body>
	<jsp:include page="templates/sidebar.jsp" />
	<jsp:include page="templates/header.jsp" />

	<div class="dashboard-main-body">
		<div
			class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
			<h6 class="fw-semibold mb-0">Permissions</h6>
			<ul class="d-flex align-items-center gap-2">
				<li class="fw-medium">
				
				<sec:authorize access="hasRole('ADMIN')">
       <a href="<c:url value='/admin/home' />"
					class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
							icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
						Dashboard
				</a>
    </sec:authorize>
				</li>
				<li>-</li>
    			<li class="fw-medium">Permission List</li>
			</ul>
		</div>

		<sec:authorize access="hasRole('ADMIN')">
<div class="card basic-data-table">

      <div class="card-body">
        <table class="table bordered-table mb-0" id="dataTable" data-page-length='10'>
          <thead>
            <tr>
              <th scope="col">
                <div class="form-check style-check d-flex align-items-center">
                  <label class="form-check-label">
                    S.L
                  </label>
                </div>
              </th>
              <th scope="col">Invoice</th>
              <th scope="col">Name</th>
              <th scope="col">Issued Date</th>
              <th scope="col">Amount</th>
              <th scope="col">Status</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <div class="form-check style-check d-flex align-items-center">
                  <input class="form-check-input" type="checkbox">
                  <label class="form-check-label">
                    01
                  </label>
                </div>
              </td>
              <td><a href="javascript:void(0)" class="text-primary-600">#526534</a></td>
              <td>
                <div class="d-flex align-items-center">
                  <img src="assets/images/user-list/user-list1.png" alt="" class="flex-shrink-0 me-12 radius-8">
                  <h6 class="text-md mb-0 fw-medium flex-grow-1">Kathryn Murphy</h6>
                </div>
              </td>
              <td>25 Jan 2024</td>
              <td>$200.00</td>
              <td> <span class="bg-success-focus text-success-main px-24 py-4 rounded-pill fw-medium text-sm">Paid</span> </td>
              <td>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-primary-light text-primary-600 rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="iconamoon:eye-light"></iconify-icon>
                </a>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-success-focus text-success-main rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="lucide:edit"></iconify-icon>
                </a>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
                </a>
              </td>
            </tr>
            <tr>
              <td>
                <div class="form-check style-check d-flex align-items-center">
                  <input class="form-check-input" type="checkbox">
                  <label class="form-check-label">
                    02
                  </label>
                </div>
              </td>
              <td><a href="javascript:void(0)" class="text-primary-600">#696589</a></td>
              <td>
                <div class="d-flex align-items-center">
                  <img src="assets/images/user-list/user-list2.png" alt="" class="flex-shrink-0 me-12 radius-8">
                  <h6 class="text-md mb-0 fw-medium flex-grow-1">Annette Black</h6>
                </div>
              </td>
              <td>25 Jan 2024</td>
              <td>$200.00</td>
              <td> <span class="bg-success-focus text-success-main px-24 py-4 rounded-pill fw-medium text-sm">Paid</span> </td>
              <td>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-primary-light text-primary-600 rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="iconamoon:eye-light"></iconify-icon>
                </a>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-success-focus text-success-main rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="lucide:edit"></iconify-icon>
                </a>
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
                </a>
              </td>
            </tr>
            
          </tbody>
        </table>
      </div>
    </div>
			
		</sec:authorize>

		

	</div>
	<jsp:include page="templates/footer.jsp" />
	
	<jsp:include page="templates/scripts.jsp" />
<script>
  let table = new DataTable('#dataTable');
</script>
</body>
</html>