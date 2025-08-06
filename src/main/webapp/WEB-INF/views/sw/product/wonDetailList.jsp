<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
        }

        body {
            background-color: #f9f5f1;
            font-family: 'Segoe UI', sans-serif;
        }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }

        .card-product-info {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            padding: 24px;
            position: relative;
        }

        .product-name {
            font-weight: bold;
            color: var(--main-brown);
        }

        .table th {
            color: #555;
            width: 150px;
            font-weight: 600;
        }

        .table td {
            color: #333;
        }

        .product-img-wrapper {
		    position: absolute;
		    top: 24px;
		    right: 24px;
		    display: flex;
		    gap: 16px;
		}
		
		.product-img {
		    width: 200px;
		    height: 200px;
		    object-fit: contain;
		    border: 1px solid #ccc;
		    border-radius: 6px;
		}

        .btn-brown-outline {
		    border: 1px solid var(--main-brown) !important;
		    color: var(--main-brown) !important;
		    background-color: #fff !important;
		}
		
		.btn-brown-outline:hover {
		    background-color: var(--main-brown) !important;
		    color: white !important;
		    border-color: var(--main-brown) !important;
		}
		
		.btn-brown {
		    background-color: var(--soft-brown) !important;
		    color: white !important;
		    border: 1px solid var(--soft-brown) !important;
		}
		
		.btn-brown:hover {
		    background-color: var(--main-brown) !important;
		    border-color: var(--main-brown) !important;
		    color: white !important;
		}
		
		.btn-soft-danger {
		    background-color: var(--danger-red) !important;
		    color: white !important;
		    border: 1px solid var(--danger-red) !important;
		}
		
		.btn-soft-danger:hover {
		    background-color: #922d2b !important;
		    border-color: #922d2b !important;
		}

        @media (max-width: 768px) {
            .product-img {
                position: static;
                display: block;
                margin: 0 auto 16px;
            }
        }
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">제품(원재료) 상세보기</div>

                <!-- 카드 -->
                <div class="card card-product-info position-relative">
                    <!-- 이미지 출력 -->
					<div class="product-img-wrapper">
					  <c:forEach var="img" items="${wonProductDetail.wonImgList}">
					    <img src="${pageContext.request.contextPath}/upload/${img.file_name}"
					         alt="제품 이미지"
					         class="product-img" />
					  </c:forEach>
					</div>

                    <!-- 제품 정보 테이블 -->
                    <table class="table table-borderless mb-0">
                        <tbody>
                            <tr><th>제품 코드</th><td>${wonProductDetail.product_code}</td></tr>
                            <tr><th>제품명</th><td class="product-name">${wonProductDetail.product_name}</td></tr>
                            <tr><th>단위</th><td>${wonProductDetail.unitName}</td></tr>
                            <tr><th>제품유형</th><td>${wonProductDetail.typeName}</td></tr>
                            <tr><th>기본 중량 (g)</th><td>${wonProductDetail.product_weight}</td></tr>
                            <tr><th>납품 여부</th><td>${wonProductDetail.isorderName}</td></tr>
                            <tr><th>삭제 구분</th><td>${wonProductDetail.product_isdel}</td></tr>
                            <tr><th>등록자</th><td>${wonProductDetail.regName}</td></tr>
                            <tr><th>등록일</th><td>${wonProductDetail.product_reg_date}</td></tr>
                        </tbody>
                    </table>
                </div>

                <!-- 버튼들: 카드 밖, 오른쪽 정렬 -->
                <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                    <a href="${pageContext.request.contextPath}/sw/wonProductList" class="btn btn-brown-outline">목록</a>
                    <a href="${pageContext.request.contextPath}/sw/wonProductModifyForm?product_code=${wonProductDetail.product_code}" class="btn btn-brown">수정</a>
                    <form action="${pageContext.request.contextPath}/sw/wonProductDelete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                        <input type="hidden" name="product_code" value="${wonProductDetail.product_code}">
                        <button type="submit" class="btn btn-soft-danger">삭제</button>
                    </form>
                </div>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>
</body>
</html>
