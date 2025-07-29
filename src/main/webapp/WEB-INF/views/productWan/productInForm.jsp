<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
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
			<main class="flex-grow-1 p-4">
				<div class="container mt-5">
				  <div class="form-section-title">완제품 등록</div>
				  <form action="${pageContext.request.contextPath}/km/wanRegister" method="post">
				    
				    <!-- 제품명 -->
				    <div class="mb-3">
				      <label for="product_name" class="form-label">제품명</label>
				      <input type="text" class="form-control" id="product_name" name="product_name" required>
				    </div>
				
				    <!-- 제품설명 -->
				    <div class="mb-3">
				      <label for="product_contents" class="form-label">제품 설명</label>
				      <textarea class="form-control" id="product_contents" name="product_contents" rows="3" required></textarea>
				    </div>
				
				    <!-- 예상 수율 -->
				    <div class="mb-3">
				      <label for="product_yield" class="form-label">예상 수율 (%)</label>
				      <input type="number" class="form-control" id="product_yield" name="product_yield" min="0" max="100" required>
				    </div>
				
					<!-- 기본 중량
				    <div class="mb-3">
				      <label for="product_weight" class="form-label">기본 중량 (g)</label>
				      <input type="number" class="form-control" id="product_weight" name="product_weight" required>
				    </div> 
				    -->
				
				    <!-- 생산 단위 -->
				    <div class="mb-3">
				      <label for="product_pack" class="form-label">완제품 생산 단위 (EA)</label>
				      <input type="number" class="form-control" id="product_pack" name="product_pack" required>
				    </div>
				    
				    <!-- 가격변동시작일은 서버에서 sysdate 기준으로 자동 처리 -->
					<!-- 		
				    가격변동 시작일자
				    <div class="mb-3">
				      <label for="to_date" class="form-label">가격변동 시작일자</label>
				      <input type="date" class="form-control" id="to_date" name="to_date" required>
				    </div>
				 	-->
				 	
				    <!-- 가격 -->
				    <div class="mb-3">
				      <label for="price" class="form-label">가격 (원)</label>
				      <input type="number" class="form-control" id="price" name="price" required>
				    </div>
				
				    <button type="submit" class="btn btn-primary">등록하기</button>
				  </form>
				</div>
			</main>
			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>