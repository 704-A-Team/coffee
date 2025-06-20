<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>11: 45 다양한 Ajax Test</h1>
	<h3>EmpRestController</h3>
	<a href="/helloText">helloText</a><p>
	<a href="/sample/sendVO2?deptno=123">sample/sendVO2(객체)</a><p>
	<a href="/sendVO3">sendVO3</a><p>
	
	<h3>AjaxController</h3>
	<a href="/getDeptName?deptno=10">getDeptName (EmpController)</a><p>
	여기서 부터 진짜 / 서버랑 클라이언트 어떻게 연동되는지 배우기 2:05 <p>
	<a href="/listEmpAjaxForm">listEmpAjaxForm(aJax JSP 연동)</a><p>
	<a href="/listEmpAjaxForm2">listEmpAjaxForm2(aJax JSP 객체리스트 Get multRow)</a><p>
	
</body>
</html>