<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body class="d-flex flex-column min-vh-100">
	<!-- main.jsp의 컨텐츠 영역 예시 -->
		
	<!-- HEADER -->
	<%@ include file="header.jsp" %>
	
	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="sidebar.jsp" %>
		
		<div class="d-flex flex-column flex-grow-1">
			
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
				<div class="d-flex justify-content-center mt-5"><h1>내용을 작성해주세요</h1></div>	
			</main>
			
			<!-- FOOTER -->
			<%@ include file="footer.jsp" %>
		</div>
		
		
	</div>
	
</body>
</html>