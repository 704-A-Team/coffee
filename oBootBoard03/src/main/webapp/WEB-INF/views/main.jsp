<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="header.jsp" %>
	</div>
	
	<div id="contents">
		<h3> main Page </h3>
		<!-- static이 기본 -->
		<img src="img/bg.jpg" alt="꿀벌" width="800" height="400">
	</div>

	<div id="footer">
		<%@ include file="foot.jsp" %>
	</div>
<!-- 부트스트랩 JS CDN 링크 (<body> 태그 닫기 직전에!) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>



