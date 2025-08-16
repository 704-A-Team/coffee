<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고조정</title>   
</head>
<body class="d-flex flex-column min-vh-100">
	
	<!-- HEADER -->
	<%@ include file="../header.jsp" %>
	
	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="../sidebar.jsp" %>
		
		<div class="d-flex flex-column flex-grow-1">
			
			<!-- 본문 -->
			<main class="flex-grow-1 p-4 doc-bg">
				<%@ include file="_silsa.jsp" %>
			</main>
			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>