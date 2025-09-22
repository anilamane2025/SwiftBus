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
    display: block !important;
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
			<h6 class="fw-semibold mb-0">View Agent</h6>
			<ul class="d-flex align-items-center gap-2">
				<li class="fw-medium"><sec:authorize access="hasRole('ADMIN')">
						<a href="<c:url value='/admin/home' />"
							class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
								icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
							Dashboard
						</a>
					</sec:authorize>
				</li>
				<li>-</li>
				<li class="fw-medium">View Agent</li>
			</ul>
		</div>

		<div class="row gy-4">
			<!-- Left Profile Card -->
			<div class="col-lg-4">
				<div class="user-grid-card position-relative border radius-16 overflow-hidden bg-base h-100">
					<img src="<c:url value='/assets/images/user-grid/user-grid-bg1.png' />"
						alt="" class="w-100 object-fit-cover">
					<div class="pb-24 ms-16 mb-24 me-16 mt--100">
						<div class="text-center border border-top-0 border-start-0 border-end-0">
							<img src="<c:url value='/assets/images/user-grid/user-grid-img14.png' />"
								alt=""
								class="border br-white border-width-2-px w-200-px h-200-px rounded-circle object-fit-cover">
							<h6 class="mb-0 mt-16">${agent.firstName} ${agent.lastName}</h6>
							<span class="text-secondary-light mb-16">${agent.username}</span>
						</div>
						<div class="mt-24">
							<h6 class="text-xl mb-16">Personal Info</h6>
							<ul>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Full Name</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.firstName} ${agent.lastName}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Email</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.username}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Phone</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.phoneNumber}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Gender</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.gender == 'M' ? 'Male' : 'Female'}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">City</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.cityName}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">State</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.cityState}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Role</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.roleName}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Commission</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.commissionType} - ${agent.commissionValue}</span>
								</li>
								<li class="d-flex align-items-center gap-1 mb-12"><span
									class="w-30 text-md fw-semibold text-primary-light">Created At</span>
									<span class="w-70 text-secondary-light fw-medium">: ${agent.createdAtFormatted}</span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<!-- Right Edit Form -->
			<div class="col-lg-8">
				<div class="card h-100">
					<div class="card-body p-24">
						<ul class="nav border-gradient-tab nav-pills mb-20 d-inline-flex"
							id="pills-tab" role="tablist">

							<li class="nav-item" role="presentation">
								<button class="nav-link d-flex align-items-center px-24 active"
									id="pills-edit-profile-tab" data-bs-toggle="pill"
									data-bs-target="#pills-edit-profile" type="button" role="tab"
									aria-controls="pills-edit-profile" aria-selected="true">
									Edit Agent</button>
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

							<!-- Edit Profile Form -->
							<div class="tab-pane fade show active" id="pills-edit-profile"
								role="tabpanel" aria-labelledby="pills-edit-profile-tab"
								tabindex="0">
								<div id="profileFormWrapper">
									<form:form id="profileForm" cssClass="needs-validation" modelAttribute="agent"
										method="post" action="/admin/agent/profile/${agent.id}">
										<div class="row">
											<!-- First Name -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="firstName" class="form-label">First Name</label>
													<form:input path="firstName" id="firstName" cssClass="form-control"/>
													<form:errors path="firstName" cssClass="invalid-feedback"/>
												</div>
											</div>

											<!-- Last Name -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="lastName" class="form-label">Last Name</label>
													<form:input path="lastName" id="lastName" cssClass="form-control"/>
													<form:errors path="lastName" cssClass="invalid-feedback"/>
												</div>
											</div>

											<!-- Email -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="username" class="form-label">Email</label>
													<form:input path="username" type="email" cssClass="form-control"/>
													<form:errors path="username" cssClass="invalid-feedback"/>
												</div>
											</div>

											<!-- Phone -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="phone" class="form-label">Phone</label>
													<form:input path="phoneNumber" id="phone" cssClass="form-control"/>
													<form:errors path="phoneNumber" cssClass="invalid-feedback"/>
												</div>
											</div>

											<!-- Gender -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label class="form-label fw-semibold text-primary-light text-sm mb-8">Gender</label>
													<form:select path="gender" cssClass="form-control radius-8">
														<form:option value="">-- Select Gender --</form:option>
														<form:option value="M">Male</form:option>
														<form:option value="F">Female</form:option>
													</form:select>
													<form:errors path="gender" cssClass="invalid-feedback"/>
												</div>
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

											<!-- Commission Type -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="commissionType" class="form-label">Commission Type</label>
													<form:select path="commissionType" id="commissionType" cssClass="form-control">
														<form:option value="">-- Select Type --</form:option>
														<form:option value="PERCENTAGE">Percentage</form:option>
														<form:option value="FIXED">Fixed</form:option>
													</form:select>
													<form:errors path="commissionType" cssClass="invalid-feedback"/>
												</div>
											</div>

											<!-- Commission Value -->
											<div class="col-sm-6">
												<div class="mb-20">
													<label for="commissionValue" class="form-label">Commission Value</label>
													<form:input path="commissionValue" id="commissionValue" cssClass="form-control"/>
													<form:errors path="commissionValue" cssClass="invalid-feedback"/>
												</div>
											</div>
										</div>

										<div class="d-flex align-items-center justify-content-center gap-3">
											<button type="submit" class="btn btn-primary">Save</button>
										</div>
									</form:form>
								</div>
							</div>

							<!-- Change Password Form -->
							<div class="tab-pane fade" id="pills-change-passwork"
								role="tabpanel" aria-labelledby="pills-change-passwork-tab"
								tabindex="0">
								<form:form modelAttribute="changePasswordDTO" method="post"
									action="/admin/agent/profile/change-password/${agent.id}">
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
    $(document).ready(function () {
        var currentState = "${agent.cityState}";
        var currentCityId = "${agent.cityId}";

        if (currentState) {
            $("#state").val(currentState).trigger("change");
        }

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

        if (currentState) {
            $("#state").trigger("change");
        }
    });
	</script>
</body>
</html>
