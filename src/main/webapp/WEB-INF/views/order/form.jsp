<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수주</title>
<!-- jQuery & Select2 CDN -->
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

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
			
				<!-- 1. 신규 및 임시저장 상태 (only client) : modify page (내용 즉시 변경 가능) -->
				<!-- 2. 요청 완료 상태 (client & emp) : 고정 page, 변경버튼(to modify page, 내용 변경 가능), 취소버튼(취소 가능) -->
				<!-- 3. 승인 및 취소(반려), 지연 상태 : 고정 page (내용 변경 불가능) -->
				<c:choose>
    				<c:when test="${isFixedPage == false }">
        				<%@ include file="_form_modify.jsp" %>
    				</c:when>
    				<c:otherwise>
        				<%@ include file="_form_fixed.jsp" %>
    				</c:otherwise>
				</c:choose>
				
			</main>
			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>