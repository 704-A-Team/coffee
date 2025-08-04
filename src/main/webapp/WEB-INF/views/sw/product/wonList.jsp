<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 리스트</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        .card-title {
            font-weight: 700;
        }
        .product-info-list {
            list-style: none;
            padding: 0;
            margin-bottom: 1rem;
        }
        .product-info-list li {
            display: flex;
            margin-bottom: 6px;
        }
        .product-info-list li strong {
            width: 40%;
        }
        .product-info-list li span {
            width: 60%;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- HEADER -->
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <!-- SIDEBAR -->
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <!-- 본문 -->
        <main class="flex-grow-1 p-4">
            <div class="container">
                <div class="form-section-title">제품(원재료) 리스트</div>

                <div class="row">
                    <c:forEach var="product" items="${wonProductDtoList}">
                        <div class="col-md-6 mb-4">
                            <div class="card h-100">
                                <div class="card-body d-flex flex-column h-100">
                                    <h5 class="card-title">${product.product_name}</h5>
                                    <ul class="product-info-list">
                                        <li><strong>유형</strong> <span>${product.typeName}</span></li>
                                        <li><strong>납품 여부</strong> <span>${product.isorderName}</span></li>
                                        <li><strong>삭제 구분</strong> <span>${product.product_isdel}</span></li>
                                        <li><strong>등록일</strong> <span>${product.product_reg_date}</span></li>
                                    </ul>

                                    <form action="${pageContext.request.contextPath}/sw/wonProductDetail" method="get" class="mt-auto">
                                        <input type="hidden" name="product_code" value="${product.product_code}" />
                                        <button type="submit" class="btn btn-outline-primary btn-sm w-100">상세 보기</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- 검색 폼 (제품명만) -->
				<form action="${pageContext.request.contextPath}/sw/wonProductList" method="get" class="row g-2 mb-4">
				    <div class="col-md-9">
				        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="제품명을 입력하세요" />
				    </div>
				    <div class="col-md-3">
				        <button type="submit" class="btn btn-primary w-100">검색</button>
				    </div>
				</form>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage - page.pageBlock}">
                                    &laquo; 이전
                                </a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage + page.pageBlock}">
                                    다음 &raquo;
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
