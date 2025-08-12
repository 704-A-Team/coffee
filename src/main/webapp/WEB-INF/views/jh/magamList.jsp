<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>월 마감 목록</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 영역 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container-fluid">
                    <h1 class="mb-4">월 마감 목록</h1>
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>년/월</th>
                                <th>구분(기초/기말)</th>
                                <th>제품코드</th>
                                <th>수량</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="magam" items="${magamList}">
                                <tr>
                                    <td>${magam.month_yrmo}</td>
                                    <td>${magam.month_magam_status}</td>
                                    <td>${magam.product_code}</td>
                                    <td>${magam.month_magam_amount}</td>
                                    <td>${magam.month_magam_reg_date}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty magamList}">
                                <tr>
                                    <td colspan="5" class="text-center text-muted py-4">등록된 마감 내역이 없습니다.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>
