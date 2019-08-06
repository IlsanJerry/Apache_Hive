<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table{
	border: 3px solid black;
}
td{
	border: 1px solid gray ;
}
</style>
</head>
<body>
    <h2> 1인 가구 정보 -ㅅ- </h2><hr>
		
		<br> 
		<input type="button" value="1인 가구가 많은 순으로 전체 출력" onclick="displayContentForm('1')"><br>
		
		<input type="button" value="구별 1인 가구 수 출력" onclick="displayContentForm('2')"><br>
		<br>

		<input type="button" value="1인 가구가 제일 많은 동의 구 이름과, 동 이름 출력" onclick="displayContentForm('3')"><br>
		<br>

		<input type="button" value="1인 가구수가 제일 많은 구 이름 출력" onclick="displayContentForm('4')"><br>
		<br>

		<form action="/springedu/detailkeyword" method="get">
		점검하려는 구이름 입력 : 
		<input type="text" name="search"><input type="submit">
		</form>
		<br>
		
<output id="out" style="display:'block'">결과물 출력 영역   
	<table>
			<tr id=a>
				<td>구</td>
				<td>동</td>
				<td>1인 가구</td>
			</tr>
			<c:choose>
		<c:when test="${ list1 != null }">  
				<c:forEach var="data"  items="${list1}">
		 		<tr>
		 		<td>${ data.gu }</td>
		 		<td>${ data.dong }</td>
		 		<td>${ data.onePerson }</td>
		 		</tr>		
				</c:forEach>
		</c:when>
		<c:otherwise>  
				${msg}
		</c:otherwise>
		</c:choose>
	</table>	
</output> 
<script>
		function displayContentForm(type) {
			if (type == 1) {
				location.href= 'http://70.12.113.165:8000/springedu/detailone?fid=1';
				document.getElementById("out").style.display = "none";
			}
			else if (type == 2) {
				location.href= 'http://70.12.113.165:8000/springedu/detailone?fid=2';
				document.getElementById("out").style.display = "none";
			}
			else if (type == 3) {
				location.href= 'http://70.12.113.165:8000/springedu/detailone?fid=3';
				document.getElementById("out").style.display = "none";
			}
			else if (type == 4) {
				location.href= 'http://70.12.113.165:8000/springedu/detailone?fid=4';
				document.getElementById("out").style.display = "none";
			}
		}
</script>
</body>
</html>