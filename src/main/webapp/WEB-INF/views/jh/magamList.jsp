<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마감 목록</title>
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
            <h1 class="mb-4">마감 목록</h1>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>년/월</th>
                        <th>구분(기초/기말)</th>
                        <th>등록일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="magam" items="${magamList}">
                        <tr>
                        <td>
  							<c:url var="detailUrl" value="${pageContext.request.contextPath}/jh/magamDetail">
   	 						<c:param name="monthYrmo" value="${magam.month_yrmo}" />
  							</c:url>
  							<a href="${detailUrl}">${magam.month_yrmo}</a>
  							</td>
                            <td>${magam.mlcontents}</td>
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
            <!-- 페이징 영역 -->
            <c:if test="${not empty page}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
                                <c:url var="prevUrl" value="${pageContext.request.contextPath}/jh/magamList">
                                    <c:param name="page" value="${page.startPage - page.pageBlock}" />
                                    <c:param name="isClosed" value="${isClosed}" />
                                </c:url>
                                <li class="page-item">
                                    <a class="page-link" href="${prevUrl}">이전</a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <c:url var="pageUrl" value="${pageContext.request.contextPath}/jh/magamList">
                                    <c:param name="page" value="${i}" />
                                    <c:param name="isClosed" value="${isClosed}" />
                                </c:url>
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageUrl}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <c:url var="nextUrl" value="${pageContext.request.contextPath}/jh/magamList">
                                    <c:param name="page" value="${page.startPage + page.pageBlock}" />
                                    <c:param name="isClosed" value="${isClosed}" />
                                </c:url>
                                <li class="page-item">
                                    <a class="page-link" href="${nextUrl}">다음</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
                <%@ include file="../footer.jsp" %>
        </div>
    </div>
</body>
</html>
