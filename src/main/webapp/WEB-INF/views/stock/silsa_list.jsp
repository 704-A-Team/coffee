<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>실사내역</title>
</head>

<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1"><!-- 좌/우 영역 -->
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 래퍼: 푸터 하단 고정용 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1">
            	<div class="container mt-4 p-4">
                	<div class="form-section-title">실사 내역</div>

	                <!-- 테이블 -->
	                <table class="table table-bordered text-center">
	                    <thead class="table-secondary">
	                        <tr>
	                        	<th>일자</th>
	                            <th>제품코드</th>
	                            <th>제품명</th>
	                            <th>조정수량</th>
	                            <th class="w-25">사유</th>
	                            <th>등록자</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:forEach var="silsa" items="${silsas}">
	                            <tr>
	                            	<td>${fn:substring(silsa.silsa_yrmo, 0, 4)}-${fn:substring(silsa.silsa_yrmo, 4, 6)}-${fn:substring(silsa.silsa_yrmo, 6, 8)}</td>
	                                <td>${silsa.silsa_product_code}</td>
	                                <td>${silsa.product_name}</td>
	                                <td>
	                                	${silsa.silsa_after_amount - silsa.silsa_amount} ➡ ${silsa.silsa_after_amount}
	                                	<c:if test="${silsa.silsa_amount > 0}">
	                                		<span class="text-primary fw-bold">(+${silsa.silsa_amount})</span>
	                                	</c:if>
	                                	<c:if test="${silsa.silsa_amount <= 0}">
	                                		<span class="text-danger fw-bold">(${silsa.silsa_amount})</span>
	                                	</c:if>
	                                </td>
	                                <td>${silsa.silsa_reason}</td>
	                                <td>${silsa.emp_name}</td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>

	                <!-- 페이징 -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
	                            <li class="page-item">
									<a class="page-link" href="/inventory/silsa/list?page=${page.startPage - page.pageBlock}&size=${page.rowPage}">이전</a>
								</li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
									<a class="page-link" href="/inventory/silsa/list?page=${i}&size=${page.rowPage}">${i}</a>
								</li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
									<a class="page-link" href="/inventory/silsa/list?page=${page.startPage + page.pageBlock}&size=${page.rowPage}">다음</a>
								</li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
			</main>
			
			<%@ include file="../footer.jsp" %>
        </div><!-- /본문 래퍼 -->
    </div><!-- /좌우 영역 -->
</body>
</html>

