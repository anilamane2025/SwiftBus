<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form:form id="profileForm" cssClass="needs-validation" modelAttribute="user"
           method="post"
           action="${editUrl}">
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
                <form:input path="username"  type="email" cssClass="form-control"/>
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
    </div>

    <div class="d-flex align-items-center justify-content-center gap-3">
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form:form>
