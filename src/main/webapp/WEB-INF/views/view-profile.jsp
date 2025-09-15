<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<style>
.invalid-feedback {
    display: block !important;   /* override Bootstrap hidden style */
    width: 100%;
    margin-top: .25rem;
    font-size: .875em;
    
    color: var(--bs-form-invalid-color);
}
</style>
<body>
	<jsp:include page="templates/sidebar.jsp" />
	<jsp:include page="templates/header.jsp" />

	<div class="dashboard-main-body">
		<div
			class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
			<h6 class="fw-semibold mb-0">View Profile</h6>
			<ul class="d-flex align-items-center gap-2">
				<li class="fw-medium"><sec:authorize access="hasRole('ADMIN')">
						<a href="<c:url value='/admin/home' />"
							class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
								icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
							Dashboard
						</a>
					</sec:authorize> <sec:authorize access="hasRole('AGENT')">
						<a href="<c:url value='/agent/home' />"
							class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
								icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
							Dashboard
						</a>
					</sec:authorize> <sec:authorize access="hasRole('PASSENGER')">
						<a href="<c:url value='/passenger/home' />"
							class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
								icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
							Dashboard
						</a>
					</sec:authorize></li>
				<li>-</li>
				<li class="fw-medium">View Profile</li>
			</ul>
		</div>

		<div class="row gy-4">
			<div class="col-lg-4">
				<div
					class="user-grid-card position-relative border radius-16 overflow-hidden bg-base h-100">
					<img
						src="<c:url value='/assets/images/user-grid/user-grid-bg1.png' />"
						alt="" class="w-100 object-fit-cover">
					<div class="pb-24 ms-16 mb-24 me-16 mt--100">
						<div
							class="text-center border border-top-0 border-start-0 border-end-0">
							<img
								src="<c:url value='/assets/images/user-grid/user-grid-img14.png' />"
								alt=""
								class="border br-white border-width-2-px w-200-px h-200-px rounded-circle object-fit-cover">
							<h6 class="mb-0 mt-16">${userdetail.firstName}
								${userdetail.lastName}</h6>
							<span class="text-secondary-light mb-16">${userdetail.username}</span>
						</div>
						<div class="mt-24">
							<h6 class="text-xl mb-16">Personal Info</h6>
							<ul>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Full
										Name</span> <span class="w-70 text-secondary-light fw-medium">:
										${userdetail.firstName} ${userdetail.lastName}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Email</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.username}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Phone</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.phoneNumber}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Gender</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.gender == 'M' ? 'Male' : 'Female'}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">City</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.cityName}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">State</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.cityState}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Role</span>
									<span class="w-70 text-secondary-light fw-medium">:
										${userdetail.roleName}</span></li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Created
										At</span> <span class="w-70 text-secondary-light fw-medium">:
										${userdetail.createdAtFormatted}</span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-8">
				<!-- Edit Profile Form -->
				<div class="card h-100">
					<div class="card-body p-24">
						<ul class="nav border-gradient-tab nav-pills mb-20 d-inline-flex"
							id="pills-tab" role="tablist">
							
							<li class="nav-item" role="presentation">
								<button class="nav-link d-flex align-items-center px-24 active"
									id="pills-edit-profile-tab" data-bs-toggle="pill"
									data-bs-target="#pills-edit-profile" type="button" role="tab"
									aria-controls="pills-edit-profile" aria-selected="true">
									Edit Profile</button>
							</li>
							<li class="nav-item" role="presentation">
								<button class="nav-link d-flex align-items-center px-24"
									id="pills-change-passwork-tab" data-bs-toggle="pill"
									data-bs-target="#pills-change-passwork" type="button"
									role="tab" aria-controls="pills-change-passwork"
									aria-selected="false" tabindex="-1">Change Password</button>
							</li>
						</ul>

						<div class="tab-content" id="pills-tabContent">
							
							<div class="tab-pane fade show active" id="pills-edit-profile"
								role="tabpanel" aria-labelledby="pills-edit-profile-tab"
								tabindex="0">
								<div id="profileFormWrapper">
									<c:url var="editUrl" value="/profile/${userdetail.id}" />
									
									<jsp:include page="/WEB-INF/views/profile-form.jsp" />
								</div>


							</div>
							

							<!-- Change Password Form -->
							<div class="tab-pane fade" id="pills-change-passwork"
								role="tabpanel" aria-labelledby="pills-change-passwork-tab"
								tabindex="0">
                                	<c:url var="editPasswordUrl" value='/profile/change-password/${userdetail.id}'/>
								<form:form modelAttribute="changePasswordDTO" method="post"
           action="${editPasswordUrl}">

    <form:hidden path="id" />

    <div class="mb-20">
        <label class="form-label">New Password</label>
        <form:password path="newPassword" cssClass="form-control radius-8"/>
        <form:errors path="newPassword" cssClass="invalid-feedback"/>
    </div>

    <div class="mb-20">
        <label class="form-label">Confirm Password</label>
        <form:password path="confirmPassword" cssClass="form-control radius-8"/>
        <form:errors path="confirmPassword" cssClass="invalid-feedback"/>
    </div>

    <c:if test="${not empty passwordError}">
        <div class="invalid-feedback">${passwordError}</div>
    </c:if>

    <button type="submit" class="btn btn-primary">Update Password</button>
</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="templates/footer.jsp" />
	</main>
	<jsp:include page="templates/scripts.jsp" />
	<script type="text/javascript">
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
        var currentState = "${user.cityState}";
        var currentCityId = "${user.cityId}";

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