<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="container">
<h2 class="mb-4 text-center">수주 목록</h2>

<table class="table table-bordered">
    <thead class="table-light">
        <tr>
            <th>등록코드</th>
            <th>상호명</th>
            <th>담당사원</th>
            <th>총액</th>
            <th>상태</th>
            <th>승인/반려일</th>
            <th>등록일</th>
        </tr>
    </thead>
    <%-- <tbody class="table-hover">
        <c:forEach var="order" items="${ordersList}">
            <tr class="clickable-row" onclick="location.href='/order/${order.orderCode}'">
                <td>${order.orderCode}</td>
                <td>${order.storeName}</td>
                <td>${order.employeeName}</td>
                <td>
                    <fmt:formatNumber value="${order.totalAmount}" type="currency" />
                </td>
                <td>${order.status}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty order.approvedOrRejectedDate}">
                            <fmt:formatDate value="${order.approvedOrRejectedDate}" pattern="yyyy-MM-dd" />
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <fmt:formatDate value="${order.createdDate}" pattern="yyyy-MM-dd" />
                </td>
            </tr>
        </c:forEach>
    </tbody> --%>
</table>

</body>
</html>