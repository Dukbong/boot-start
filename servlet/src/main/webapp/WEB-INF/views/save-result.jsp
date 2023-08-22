<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="hello.servlet.domain.member.Member" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	성공
	<ul>	
		<%--
			<li>id=<%=((Member)request.getAttribute("member")).getId() %></li>
			<li>username=<%=((Member)request.getAttribute("member")).getUsername() %></li>
			<li>age=<%=((Member)request.getAttribute("member")).getAge() %></li>
		--%>
		
		<!-- 아래  문법은 getter로 불러오는거다. -->
		<li>id=${member.id }</li>
		<li>username=${member.username }</li>
		<li>age=${member.age }</li>
	</ul>
	
	<a href="/index.html">메인</a>
</body>
</html>