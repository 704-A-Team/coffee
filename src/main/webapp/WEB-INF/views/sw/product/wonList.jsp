<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 조회</title>
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
                    
                    <!-- 제목 -->
                    <div class="d-flex justify-content-center mt-5">
                        <h1>제품 조회</h1>
                    </div>

                    <!-- 헤더 라벨 -->
                    <div class="row fw-bold py-2 border-bottom bg-light mt-4">
                        <div class="col-2">제품명</div>
                        <div class="col-2">제품유형</div>
                        <div class="col-2">납품여부</div>
                        <div class="col-2">삭제구분</div>
                        <div class="col-4">등록일</div>
                    </div>

                    <!-- 데이터 목록 -->
                    <div class="data-list-wrapper">
                        <c:forEach var="wonProduct" items="${wonProductDtoList}" varStatus="status">
                            <a href="${pageContext.request.contextPath}/sw/wonProductDetail?product_code=${wonProduct.product_code}"
                               class="text-decoration-none text-reset">
                                <div class="row py-2 border-bottom list-item-row">
                                    <div class="col-2">${wonProduct.product_name}</div>
                                    <div class="col-2">${wonProduct.typeName}</div>
                                    <div class="col-2">${wonProduct.isorderName}</div>
                                    <div class="col-2">${wonProduct.product_isdel}</div>
                                    <div class="col-4">${wonProduct.product_reg_date}</div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>

                    <!-- 페이징 -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage - page.pageBlock}" aria-label="Previous">
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
                                    <a class="page-link" href="${pageContext.request.contextPath}/sw/wonProductList?currentPage=${page.startPage + page.pageBlock}" aria-label="Next">
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
