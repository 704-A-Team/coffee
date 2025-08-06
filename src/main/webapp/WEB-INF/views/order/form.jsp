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
    .doc-wrapper {
      aspect-ratio: 1 / 1.414; /* A4 비율 (1:√2) */
      max-width: 100%;
      width: 90%; /* 반응형 기준 너비 */
      background: white;
      margin: auto;
      padding: 2rem;
      box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
      overflow: auto;
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
			
				<!-- 1. 신규 및 요청 완료 상태 : 내용 변경 가능 -->
				<!-- 2. 승인 및 취소(반려), 지연 상태 : 내용 변경 불가능 -->
				<!-- 가명점 / 본사 페이지 분리 -->
				<c:choose>
    				<c:when test="${empty order or order.order_status <= 1}">
        				<%@ include file="_form_client.jsp" %>
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