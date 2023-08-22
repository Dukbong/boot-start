<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="save" method="post">
	<!-- 
		절대경로  : /save
		>> 현재 url ex) localhost:8888/servlet-mvc/members/new-form
		>>>>>>>>>>>>>>localhost:8888/save 로 된다.
		
		상대경로 : save
		>> 현재 url ex) localhost:8888/servlet-mvc/members/new-form
		>>>>>>>>>>>>>>localhost:8888/servlet-mvc/members/save 로 된다.
	 -->
		username : <input type="text" name="username"/>
		age      : <input type="text" name="age"/>
		<button type="submit">전송</button>
	</form>
</body>
</html>