<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container p-4">
<h2 class="mb-4 text-center fw-bold">수주 목록</h2>

<table class="table table-bordered table-hover text-center">
    <thead class="table-secondary">
        <tr>
            <th>등록코드</th>
            <th>상호명</th>
            <th>담당사원</th>
            <th>총액</th>
            <th>상태</th>
            <th>요청일</th>
            <th>승인/반려일</th>
            <th>등록일</th>
        </tr>
    </thead>
    <tbody class="table-hover">
        <c:forEach var="order" items="${orders}">
            <tr class="clickable-row" onclick="location.href='/order/${order.order_code}'">
                <td>${order.order_code}</td>
                <td>${order.client_name}</td>
                <td>${order.emp_name}</td>
                <td>
                    <fmt:formatNumber value="${order.order_final_price}" pattern="#,###" />
                </td>
                <td><c:choose>
                	<c:when test="${order.order_status == 0}">
                		<span class="badge bg-secondary fs-6">${order.cd_contents}</span>
                	</c:when>
                	<c:when test="${order.order_status == 1}">
                		<span class="badge bg-primary-subtle text-dark fs-6">${order.cd_contents}</span>
                	</c:when>
                	<c:when test="${order.order_status == 2}">
                		<span class="badge bg-danger fs-6">${order.cd_contents}</span>
                	</c:when>
                	<c:when test="${order.order_status == 3}">
                		<span class="badge bg-warning text-dark fs-6">${order.cd_contents}</span>
                	</c:when>
                	<c:when test="${order.order_status == 4}">
                		<span class="badge bg-success fs-6">${order.cd_contents}</span>
                	</c:when>
                	<c:otherwise>
                		<span class="badge bg-success fs-6">${order.cd_contents}</span>
                	</c:otherwise>
                </c:choose></td>
                <td>
                    <%-- <c:choose>
                        <c:when test="${not empty order.approvedOrRejectedDate}">
                            <fmt:formatDate value="${order.approvedOrRejectedDate}" pattern="yyyy-MM-dd" />
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose> --%>
                    ${order.order_req_date }
                </td>
                <td>${order.order_perm_date }</td>
                <td>${order.order_reg_date }</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- Pagination -->
<nav class="mt-4">
	<ul class="pagination justify-content-center">
		<c:if test="${page.startPage > page.pageBlock}">
			<li class="page-item">
				<a class="page-link" href="/order/list?page=${page.startPage - page.pageBlock}&size=${page.rowPage}">이전</a>
			</li>
		</c:if>

		<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
			<li class="page-item ${i == page.currentPage ? 'active' : ''}">
				<a class="page-link bg-secondary border-secondary" href="/order/list?page=${i}&size=${page.rowPage}">${i}</a>
			</li>
		</c:forEach>

		<c:if test="${page.endPage < page.totalPage}">
			<li class="page-item">
				<a class="page-link" href="/order/list?page=${page.startPage + page.pageBlock}&size=${page.rowPage}">다음</a>
			</li>
		</c:if>
	</ul>
</nav>
</div>

</body>
</html>
