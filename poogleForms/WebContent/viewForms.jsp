<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

	<div id="header">
		<c:import url="header.jsp"></c:import>
	</div>
	<div id="userDisplay">
		<c:import url="userHeader.jsp"></c:import>
	</div>
	<div id="allPageFunctionalities">
		<c:import url="allPageFunctionalities.jsp"></c:import>
	</div>
	<div id="displayForms">
		<c:import url="FormHandler.jsp"></c:import>
	</div>

</body>
</html>