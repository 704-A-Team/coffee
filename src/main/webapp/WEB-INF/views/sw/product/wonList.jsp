<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품(원재료) 리스트</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
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

        .card {
            border: 1px solid #ddd;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            transition: all 0.3s ease-in-out;
        }

        .card:hover {
            transform: scale(1.01);
            box-shadow: 0 6px 10px rgba(0,0,0,0.1);
        }

        .card-title {
            font-weight: 700;
            color: var(--main-brown);
        }

        .product-info-list {
            list-style: none;
            padding: 0;
            margin-bottom: 1rem;
            font-size: 0.95rem;
        }

        .product-info-list li {
            display: flex;
            margin-bottom: 6px;
        }

        .product-info-list li strong {
            width: 40%;
            color: #555;
        }

        .product-info-list li span {
            width: 60%;
            color: #333;
        }

        .btn-brown {
		    background-color: #ffffff !important;           /* 흰색 배경 */
		    color: #333 !important;                         /* 기본 글자색 */
		    border: 1px solid #ccc !important;              /* 연한 회색 테두리 */
		    box-shadow: none !important;
		    outline: none !important;
		    transition: background-color 0.2s ease-in-out;
		}
		
		.btn-brown:hover {
		    background-color: #e9ecef !important;           /* hover 시 연회색 */
		    color: #000 !important;                         /* hover 시 진한 글자색 */
		    border: 1px solid #bbb !important;              /* hover 시 약간 진한 테두리 */
		}

        .search-form {
            margin-bottom: 2rem;
        }

        .container {
            max-width: 1100px;
        }

        .pagination .page-link {
            color: var(--main-brown);
        }

        .pagination .page-item.active .page-link {
            background-color: var(--main-brown);
            border-color: var(--main-brown);
            color: white;
        }
        .product-img {
		    max-width: 100px;
		    max-height: 100px;
		    object-fit: contain; /* 이미지 왜곡 방지 */
		}
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container">
                <div class="form-section-title">제품(원재료) 리스트</div>

                <!-- 검색 -->
                <form action="${pageContext.request.contextPath}/sw/wonProductList" method="get" class="row search-form g-2">
                    <div class="col-md-9">
                        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="제품명을 입력하세요" />
                    </div>
                    <!-- 검색 버튼 -->
					<div class="col-md-3">
					    <button type="submit" class="btn btn-brown w-100">검색</button>
					</div>
                </form>

                <!-- 카드 리스트 -->
                <div class="row">
                    <c:forEach var="product" items="${wonProductDtoList}">
                        <div class="col-md-6 mb-4">
                            <div class="card h-100">
                                <div class="row g-0">
                                    <div class="col-md-4 d-flex align-items-center justify-content-center">
									    <c:choose>
									        <c:when test="${not empty product.simage}">
									            <img src="${pageContext.request.contextPath}/upload/s_${product.simage}" class="img-fluid rounded-start product-img" alt="${product.product_name}">
									        </c:when>
									        <c:otherwise>
									            <img src="${pageContext.request.contextPath}/upload/default.png" class="img-fluid rounded-start product-img" alt="기본 이미지">
									        </c:otherwise>
									    </c:choose>
									</div>
                                    <div class="col-md-8">
                                        <div class="card-body d-flex flex-column h-100">
                                            <h5 class="card-title">${product.product_name}</h5>
                                            <ul class="product-info-list">
                                                <li><strong>유형</strong> <span>${product.typeName}</span></li>
                                                <li><strong>납품 여부</strong> <span>${product.isorderName}</span></li>
                                                <li><strong>등록일</strong> <span>${product.product_reg_date}</span></li>
                                                <li><strong>삭제 구분</strong>
                                                    <span>
                                                        <c:choose>
                                                            <c:when test="${product.product_isdel == 0}">사용중</c:when>
                                                            <c:otherwise>삭제됨</c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </li>
                                            </ul>
                                            <form action="${pageContext.request.contextPath}/sw/wonProductDetail" method="get" class="mt-auto">
                                                <input type="hidden" name="product_code" value="${product.product_code}" />
                                                <button type="submit" class="btn btn-brown btn-sm w-100">상세 보기</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>
</body>
</html>
