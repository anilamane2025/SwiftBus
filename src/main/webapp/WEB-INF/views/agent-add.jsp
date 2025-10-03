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
            <h6 class="fw-semibold mb-0">Agent</h6>
            <ul class="d-flex align-items-center gap-2">
                <li class="fw-medium">
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value='/admin/home' />"
                            class="d-flex align-items-center gap-1 hover-text-primary"> 
                            <iconify-icon icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
                            Dashboard
                        </a>
                    </sec:authorize>
                </li>
                <li>-</li>
                <li class="fw-medium">Add New Agent</li>
            </ul>
        </div>

        <sec:authorize access="hasAuthority('AGENT_ADD')">
            <c:if test="${not empty success}">
                <div class="alert alert-success bg-success-100 text-success-600 border-success-600 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                    <div class="d-flex align-items-center gap-2">
                        <iconify-icon icon="akar-icons:double-check" class="icon text-xl"></iconify-icon>
                        ${success}
                    </div>
                    <button class="remove-button text-success-600 text-xxl line-height-1"> 
                        <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                    </button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger bg-danger-100 text-danger-600 border-danger-600 px-24 py-13 mb-0 fw-semibold text-lg radius-4 d-flex align-items-center justify-content-between" role="alert">
                    <div class="d-flex align-items-center gap-2">
                        <iconify-icon icon="mingcute:delete-2-line" class="icon text-xl"></iconify-icon>
                        ${error}
                    </div>
                    <button class="remove-button text-danger-600 text-xxl line-height-1"> 
                        <iconify-icon icon="iconamoon:sign-times-light" class="icon"></iconify-icon>
                    </button>
                </div>
            </c:if>

            <div class="card basic-data-table">
                <div class="card-body">
                    <form:form method="post" modelAttribute="agentDTO"
                        action="/admin/agent/register">
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
                                    <form:errors path="phoneNumber" cssClass="invalid-feedback" />
                                </div>
                            </div>

                            <!-- Gender -->
                            <div class="col-sm-6">
                                <div class="mb-20">
                                    <label class="form-label">Gender</label>
                                    <form:select path="gender" cssClass="form-control radius-8">
                                        <form:option value="">-- Select Gender --</form:option>
                                        <form:option value="M">Male</form:option>
                                        <form:option value="F">Female</form:option>
                                    </form:select>
                                    <form:errors path="gender" cssClass="invalid-feedback" />
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
                                    <label class="form-label">Commission Type</label>
                                    <form:select path="commissionType" cssClass="form-control radius-8">
                                        <form:option value="">-- Select Type --</form:option>
                                        <form:option value="PERCENTAGE">Percentage</form:option>
                                        <form:option value="FIXED">Fixed</form:option>
                                    </form:select>
                                    <form:errors path="commissionType" cssClass="invalid-feedback" />
                                </div>
                            </div>

                            <!-- Commission Value -->
                            <div class="col-sm-6">
                                <div class="mb-20">
                                    <label class="form-label">Commission Value</label>
                                    <form:input path="commissionValue" cssClass="form-control radius-8" />
                                    <form:errors path="commissionValue" cssClass="invalid-feedback" />
                                </div>
                            </div>

                            <!-- Password -->
                            <div class="col-sm-6">
                                <div class="mb-20">
                                    <div class="position-relative ">
                                        <label class="form-label">Password</label>
                                        <form:password path="password" cssClass="form-control h-56-px bg-neutral-50 radius-8" />
                                        <span class="toggle-password ri-eye-line cursor-pointer position-absolute end-0  translate-middle-y me-16 text-secondary-light"
                                            data-toggle="#password"></span>
                                        <form:errors path="password" cssClass="invalid-feedback" />
                                    </div>
                                </div>
                            </div>

                            <!-- Confirm Password -->
                            <div class="col-sm-6">
                                <div class="mb-20">
                                    <div class="position-relative ">
                                        <label class="form-label">Confirm Password</label>
                                        <form:password path="confirmPassword" cssClass="form-control h-56-px bg-neutral-50 radius-8" />
                                        <span class="toggle-password ri-eye-line cursor-pointer position-absolute end-0  translate-middle-y me-16 text-secondary-light"
                                            data-toggle="#confirmPassword"></span>
                                        <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                                    </div>
                                </div>
                            </div>

                            <c:if test="${not empty passwordError}">
                                <div class="invalid-feedback">${passwordError}</div>
                            </c:if>

                        </div>

                        <button type="submit" class="btn btn-primary radius-12 mt-32 w-100">
                            Save
                        </button>
                    </form:form>
                </div>
            </div>
        </sec:authorize>
    </div>

    <jsp:include page="templates/footer.jsp" />
    <jsp:include page="templates/scripts.jsp" />

    <script>
        // Password toggle
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
        initializePasswordToggle('.toggle-password');
    </script>

    <script type="text/javascript">
        $(document).ready(function () {
            var currentState = "${agentDTO.cityState}";
            var currentCityId = "${agentDTO.cityId}";

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
