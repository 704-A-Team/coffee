<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        
          .card-product-info {
        background-color: #ffffff; /* 깔끔한 흰색 배경 */
    }
    .card-product-header {
        background-color: #6ca0dc !important;
        color: white !important;
        font-weight: bold !important;
    }
  
    /* 제품명 굵게 */
    .product-name {
        font-weight: bold;
        color: black;
    }
    /* 일반 글씨 검정 */
    .text-black {
        color: black;
    }
    
    .card-product-header h5 {
    	font-weight: bold;
    }
        
    </style>

</head>
<body class="d-flex flex-column min-vh-100">

	<%@ include file="../header.jsp" %>
	<div class="d-flex flex-grow-1">
		<%@ include file="../sidebar.jsp" %>
		<div class="d-flex flex-column flex-grow-1">
			<main class="flex-grow-1 p-4">
<div class="container mt-3">
    <div class="form-section-title">완제품 상세보기</div>

    <div class="row g-4">
        <!-- 제품 기본 정보 카드 -->
        <div class="col-md-7">
            <div class="card shadow-sm border-0 card-product-info">
                <div class="card-header card-product-header">
                    <h5 class="mb-0">${wanAndRcpDetailDTO.product_name} 제품 정보</h5>
                </div>
                <div class="card-body text-black">
                    <table class="table table-borderless mb-0">
                        <tbody>
                            <tr>
                                <th scope="row" style="width: 150px;" class="text-black">제품 코드</th>
                                <td class="text-black">${wanAndRcpDetailDTO.product_code}</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">제품명</th>
                                <td class="product-name">${wanAndRcpDetailDTO.product_name}</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">제품 설명</th>
                                <td class="text-black">${wanAndRcpDetailDTO.product_contents}</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">예상 수율 (%)</th>
                                <td class="text-black">${wanAndRcpDetailDTO.product_yield} %</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">최근 가격 (원)</th>
                                <td class="text-black">${wanAndRcpDetailDTO.wan_price} 원</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">생산 단위 (EA)</th>
                                <td class="text-black">${wanAndRcpDetailDTO.product_pack} 개</td>
                            </tr>
                            <tr>
                                <th scope="row" class="text-black">등록일</th>
                                <td class="text-black">${wan_reg_date}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- 이미지 카드 -->
        <div class="col-md-5">
            <div class="card shadow-sm border-0 card-image">
                 <div class="card-header card-product-header">
                    <h5 class="mb-0">이미지</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex gap-3 flex-wrap justify-content-start">
                        <c:forEach var="img" items="${wanAndRcpDetailDTO.wanImgList}">
                            <div class="border rounded" style="width: 120px; overflow: hidden;">
                                <img src="/upload/${img.file_name}" alt="제품 이미지" class="img-fluid" style="height: 100px; object-fit: cover;">
                            </div>
                        </c:forEach>
                        <c:if test="${empty wanAndRcpDetailDTO.wanImgList}">
                            <p>이미지가 없습니다.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 레시피 리스트 -->
    <div class="card mt-4 shadow-sm card-product-info">
        <div class="card-header card-product-header">
            <h5 class="mb-0">${wanAndRcpDetailDTO.product_name} 레시피</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-striped mb-0">
                <thead class="table-light">
                    <tr>
                        <th>원재료명</th>
                        <th>사용량</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rcp" items="${wanAndRcpDetailDTO.recipeList}">
                        <tr>
                            <td>${rcp.won_product_name}</td>
                            <td>${rcp.recipe_amount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 버튼 영역 -->
    <div class="d-flex gap-3 mt-4 mb-5">
        <a href="/km/wanList" class="btn btn-outline-primary">목록 보기</a>
        <a href="/km/wanModifyInForm?product_code=${wanAndRcpDetailDTO.product_code}" class="btn btn-primary">제품 수정</a>
        <a href="/km/recipeModifyInForm?product_code=${wanAndRcpDetailDTO.product_code}" class="btn btn-warning text-white">레시피 수정</a>
    </div>
</div>



			</main>
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
</body>
</html>
