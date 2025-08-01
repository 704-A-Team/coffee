<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="container-fluid p-4">
            <h1 class="mb-4">월 마감 목록</h1>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th >년/월</th>
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
                </tbody>
            </table>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>
</body>
</html>
