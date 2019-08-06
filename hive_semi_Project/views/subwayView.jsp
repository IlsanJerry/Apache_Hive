<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

</style>
</head>
<body>
    <h2> 지하철 노선 정보  </h2>
    <hr>
<table>
		<tr id=a>
			<td>Line</td>
			<td>Time</td>
			<td>Ride</td>
			<td>TakeOff</td>
		</tr>
	 	<c:choose>
	<c:when test="${ list1 != null }">  
			<c:forEach var="data"  items="${list1}">
	 		<tr>
	 		<td>${ data.line }</td>
	 		<td>${ data.time }</td>
	 		<td>${ data.ride }</td>
	 		<td>${data.takeoff}</td>
	 		</tr>		
			</c:forEach>
	</c:when>
	<c:otherwise>  
			${msg}
	</c:otherwise>
	</c:choose> 
</table>	
</body>
</html>