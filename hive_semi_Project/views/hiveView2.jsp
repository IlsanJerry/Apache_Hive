<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h2> 결과 출력 </h2>
    <hr>
    <c:choose>
	<c:when test="${ list1 != null }">  
			<c:forEach var="data"  items="${list1}">
	 		${ data.year } ${ data.name }  ${ data.qty } ${data.price}<br>			
			</c:forEach>
	</c:when>
	<c:when test="${ list2 != null }">  
			<c:forEach var="data"  items="${list2}">
	 		${ data.year } ${ data.name }  ${ data.qty } ${data.price}<br>			
			</c:forEach>	
	</c:when>
	<c:when test="${ list3 != null }">  
			<c:forEach var="data"  items="${list3}">
	 		${ data.year } ${ data.name }  ${ data.qty } ${data.price}<br>			
			</c:forEach>
	</c:when>
	<c:otherwise>  
			${msg}
	</c:otherwise>
	</c:choose>
</body>
</html>