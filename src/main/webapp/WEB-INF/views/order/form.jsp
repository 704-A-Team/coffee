<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.doc-bg {
      background-color: #f0f0f0;
      padding: 2rem;
    }
</style>    
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
			
				<!-- 신규 및 요청 완료 상태 : 내용 변경 가능 -->
				<!-- 승인 및 취소(반려), 지연 상태 : 내용 변경 불가능 -->
				<c:choose>
    				<c:when test="${empty order or order.order_status <= 1}">
        				<%@ include file="_form.jsp" %>
    				</c:when>
    				<c:otherwise>
        				아직 없음
    				</c:otherwise>
				</c:choose>
			
			
				
			</main>
			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>