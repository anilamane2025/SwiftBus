<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    			<li class="fw-medium">Permission</li>
			</ul>
		</div>

		
		<sec:authorize access="hasAuthority('PERMISSION_VIEW')">
		<c:if test="${not empty success}">
   <div class="alert alert-success bg-success-100 text-success-600 border-success-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                        <div class="d-flex align-items-center gap-2">
                            <iconify-icon icon="akar-icons:double-check" class="icon text-xl"></iconify-icon>
                            ${success}
                        </div>
                        <button class="remove-button text-success-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                    </div>
</c:if>
<c:if test="${not empty error}">
   <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 border-start-width-4-px border-top-0 border-end-0 border-bottom-0 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                        <div class="d-flex align-items-center gap-2">
                            <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                            ${error}
                        </div>
                        <button class="remove-button text-danger-600 text-xxl line-height-1"> <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon></button>
                    </div>
</c:if>
<div class="card basic-data-table">
			<div class="card-header border-bottom bg-base d-flex align-items-center flex-wrap  justify-content-between">
                <div></div>
                <sec:authorize access="hasAuthority('PERMISSION_ADD')">
                <button type="button" class="btn btn-primary text-sm btn-sm d-flex align-items-center " data-bs-toggle="modal" data-bs-target="#exampleModal">
                    <iconify-icon icon="ic:baseline-plus" class="icon text-xl line-height-1"><template shadowrootmode="open"><style data-style="data-style">:host{display:inline-block;vertical-align:0}span,svg{display:block}</style><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="M19 12.998h-6v6h-2v-6H5v-2h6v-6h2v6h6z"></path></svg></template></iconify-icon>
                    Add New Permission
                </button>
                </sec:authorize>
            </div>
      <div class="card-body">
        <table class="table bordered-table mb-0" id="dataTable" data-page-length='10'>
          <thead>
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="p" items="${permissions}">
            <tr>
              <td>
                <div class="d-flex align-items-center">
                  <h6 class="text-md mb-0 fw-medium flex-grow-1">${p.name}</h6>
                </div>
              </td>
              <td>
              	<sec:authorize access="hasAuthority('PERMISSION_EDIT')">
                <a href="javascript:void(0)" class="w-32-px h-32-px bg-success-focus text-success-main rounded-circle d-inline-flex align-items-center justify-content-center"
			           data-bs-toggle="modal" data-bs-target="#editModal"
			           data-id="${p.id}" data-name="${p.name}">
			           <iconify-icon icon="lucide:edit"></iconify-icon>
			        </a>
                </sec:authorize>	
                <sec:authorize access="hasAuthority('PERMISSION_DELETE')">
                <a href="<c:url value='/permissions/delete/${p.id}'/>"  onclick="return confirm('Are you sure you want to delete this permission?');" class="w-32-px h-32-px bg-danger-focus text-danger-main rounded-circle d-inline-flex align-items-center justify-content-center">
                  <iconify-icon icon="mingcute:delete-2-line"></iconify-icon>
                </a>
                </sec:authorize>
              </td>
            </tr>
           
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
			
		</sec:authorize>

		

	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" style="display: none;" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog modal-dialog-centered">
            <div class="modal-content radius-16 bg-base">
                <div class="modal-header py-16 px-24 border border-top-0 border-start-0 border-end-0">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Add New Permission</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-24">
                <c:url var="addPermissionUrl" value='/permissions/add'/>
                <form:form method="post" action="${addPermissionUrl}" modelAttribute="permission" >
                     
                        <div class="row">   
                            <div class="col-12 mb-20">
                                <label class="form-label fw-semibold text-primary-light text-sm mb-8">Permission  Name</label>
                                <form:input path="name" cssClass="form-control radius-8" placeholder="Enter Permission Name" />
            					<form:errors path="name" cssClass="text-danger"/>
                            </div>
                            
                            <div class="d-flex align-items-center justify-content-center gap-3 mt-24">
                                <button type="reset" data-bs-dismiss="modal" class="border border-danger-600 bg-hover-danger-200 text-danger-600 text-md px-40 py-11 radius-8"> 
                                    Cancel
                                </button>
                                <button type="submit" class="btn btn-primary border border-primary-600 text-md px-48 py-12 radius-8"> 
                                    Save
                                </button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content radius-16 bg-base">
            <div class="modal-header py-16 px-24 border border-top-0 border-start-0 border-end-0">
                <h1 class="modal-title fs-5" id="editModalLabel">Edit Permission</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body p-24">

                <c:url var="updatePermissionUrl" value='/permissions/update'/>
                <form:form method="post" action="${updatePermissionUrl}" modelAttribute="permission">
                    
                    <form:hidden path="id" id="edit-id"/>

                    <div class="row">   
                        <div class="col-12 mb-20">
                            <label class="form-label fw-semibold text-primary-light text-sm mb-8">Permission Name</label>
                            <form:input path="name" id="edit-name" cssClass="form-control radius-8" placeholder="Enter Permission Name"/>
                            <form:errors path="name" cssClass="text-danger"/>
                        </div>
                        
                        <div class="d-flex align-items-center justify-content-center gap-3 mt-24">
                            <button type="reset" data-bs-dismiss="modal"
                                class="border border-danger-600 bg-hover-danger-200 text-danger-600 text-md px-40 py-11 radius-8"> 
                                Cancel
                            </button>
                            <button type="submit" class="btn btn-primary border border-primary-600 text-md px-48 py-12 radius-8"> 
                                Save
                            </button>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>
    </div>
</div>

	<jsp:include page="templates/footer.jsp" />
	
	<jsp:include page="templates/scripts.jsp" />
	<c:if test="${openModal}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
            myModal.show();
        });
    </script>
</c:if>
<c:if test="${openModalEdit}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var myModal = new bootstrap.Modal(document.getElementById('editModal'));
            myModal.show();
        });
    </script>
</c:if>
<script>
  const editModal = document.getElementById('editModal');
  editModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const id = button.getAttribute('data-id');
    const name = button.getAttribute('data-name');

    document.getElementById('edit-id').value = id;
    document.getElementById('edit-name').value = name;
  });
</script>
<script>    
    $('.remove-button').on('click', function() {
        $(this).closest('.alert').addClass('d-none')
    }); 
</script>
<script>
  let table = new DataTable('#dataTable');
</script>
</body>
</html>