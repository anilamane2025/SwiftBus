<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
<h2>Register</h2>
<form:form method="post" modelAttribute="user">
    <form:label path="username">Email:</form:label>
    <form:input path="username"/>
    <br/>

    <form:label path="password">Password:</form:label>
    <form:password path="password"/>
    <br/>

    <form:label path="firstName">First Name:</form:label>
    <form:input path="firstName"/>
    <br/>

    <form:label path="lastName">Last Name:</form:label>
    <form:input path="lastName"/>
    <br/>

    <form:label path="phoneNumber">Phone Number:</form:label>
    <form:input path="phoneNumber"/>
    <br/>

    <form:label path="gender">Gender:</form:label>
    <form:input path="gender"/>
    <br/>

    <form:label path="city">City:</form:label>
    <form:input path="city"/>
    <br/>

    <form:label path="state">State:</form:label>
    <form:input path="state"/>
    <br/>

    <input type="submit" value="Register"/>
</form:form>

<c:if test="${not empty registrationError}">
    <p style="color:red">${registrationError}</p>
</c:if>
</body>
</html>
