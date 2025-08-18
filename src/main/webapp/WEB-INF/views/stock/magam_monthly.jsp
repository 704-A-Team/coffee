<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마감 상세 내역</title>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 영역 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
				<div class="container mt-4 p-4">
                	<div class="form-section-title">마감 상세</div>
                	
                	<table class="table table-bordered text-center">
	                    <thead class="table-secondary">
	                        <tr>
	                            <th>년도</th>
	                            <th>월</th>
	                            <th>구분</th>
	                            <th>제품코드</th>
	                            <th>제품명</th>
	                            <th>수량</th>
	                            <th>등록일</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:forEach var="prd" items="${magamProducts}">
	                            <tr>
	                            	<td>${fn:substring(prd.month_yrmo, 0, 4)}</td>
	                            	<td>${fn:substring(prd.month_yrmo, 4, 6)}</td>
	                            	<td>
	                            		<c:if test="${prd.month_magam_status == 0 }">기초</c:if>
							  			<c:if test="${prd.month_magam_status == 1 }">기말</c:if>
	                            	</td>
	                                <td>${prd.product_code}</td>
	                                <td>${prd.product_name}</td>
	                                <td>${prd.month_magam_amount}</td>
	                                <td>${prd.month_magam_reg_date}</td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
                    
                    <!-- 페이징 -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
	                            <li class="page-item">
									<a class="page-link"
									href="/inventory/magam/${yrmo}/${status}?page=${page.startPage - page.pageBlock}&size=${page.rowPage}">이전</a>
								</li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
									<a class="page-link"
									href="/inventory/magam/${yrmo}/${status}?page=${i}&size=${page.rowPage}">${i}</a>
								</li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
									<a class="page-link"
									href="/inventory/magam/${yrmo}/${status}?page=${page.startPage + page.pageBlock}&size=${page.rowPage}">다음</a>
								</li>
                            </c:if>
                        </ul>
                    </nav>
                    
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>
