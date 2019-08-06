<%@page import="java.util.List"%>
<%@page import="vo.FruitsVO"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		List<FruitsVO> list1 = (List<FruitsVO>)request.getAttribute("list1");
		List<FruitsVO> list2 = (List<FruitsVO>)request.getAttribute("list2");
		List<FruitsVO> list3 = (List<FruitsVO>)request.getAttribute("list3");
		if(list1 != null) {
			for (FruitsVO vo : list1) {
	%>			
				<%=vo.getYear() %> <%=vo.getName() %> <%=vo.getQty() %> <%=vo.getPrice() %><br>				
	<%
			}
		} else if (list2 != null) {
			for (FruitsVO vo : list2) {
	%>			
				<%=vo.getYear() %> <%=vo.getQty() %> <br>				
	<%
			}
		} else if(list3 != null) {
			for (FruitsVO vo : list3) {
	%>			
				<%=vo.getYear() %> <%=vo.getName() %> <%=vo.getPrice() %><br>				
	<%
			}
		} else {
	%>
			${msg}
	<%
		}
	%>
</body>
</html>