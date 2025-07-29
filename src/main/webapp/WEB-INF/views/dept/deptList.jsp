<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 목록</title>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    .list-item-row {
        transition: background-color 0.3s ease;
        cursor: pointer;
    }
    .list-item-row:hover {
        background-color: #e2f3ff;
    }
    .data-list-wrapper a:nth-of-type(odd) .list-item-row {
        background-color: #f8f9fa;
    }
    .data-list-wrapper a:nth-of-type(even) .list-item-row {
        background-color: #e9ecef;
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="d-flex justify-content-between mb-4">
                    <h2>부서 목록</h2>
                    <a href="${pageContext.request.contextPath}/dept/deptInForm" class="btn btn-success">부서 등록</a>
                </div>

                <!-- Header row -->
                <div class="row fw-bold py-2 border-bottom bg-primary text-white">
                    <div class="col-2">부서코드</div>
                    <div class="col-4">부서명</div>
                    <div class="col-3">대표번호</div>
                    <div class="col-3">등록일</div>
                </div>

                <!-- Data list -->
                <div class="data-list-wrapper">
                    <c:forEach var="deptDto" items="${deptDtoList}">
                        <a href="${pageContext.request.contextPath}/dept/deptDetail?dept_code=${deptDto.dept_code}" class="text-decoration-none text-reset">
                            <div class="row py-2 border-bottom list-item-row">
                                <div class="col-2">${deptDto.dept_code}</div>
                                <div class="col-4">${deptDto.dept_name}</div>
                                <div class="col-3">${deptDto.dept_tel}</div>
                                <div class="col-3">${deptDto.dept_reg_date}</div> <%-- 수정됨 --%>
                            </div>
                        </a>
                    </c:forEach>
                </div>

                <c:if test="${empty deptDtoList}">
                    <div class="text-center mt-4 text-muted">등록된 부서가 없습니다.</div>
                </c:if>

                <!-- Paging -->
                <nav class="mt-4" aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/dept/list?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/dept/list?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/dept/list?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
