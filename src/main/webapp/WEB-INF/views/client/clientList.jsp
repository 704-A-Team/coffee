<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 목록</title>

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
                    <h2>거래처 목록</h2>
                    <a href="${pageContext.request.contextPath}/client/clientInForm" class="btn btn-success">거래처 등록</a>
                </div>

                <!-- Header -->
                <div class="row fw-bold py-2 border-bottom bg-primary text-white text-center">
                    <div class="col-1">코드</div>
                    <div class="col-1">유형</div>
                    <div class="col-3">거래처명</div>
                    <div class="col-1">상태</div>
                    <div class="col-2">대표자명</div>
                    <div class="col-2">담당 사원</div>
                    <div class="col-2">전화번호</div>
                </div>

                <!-- Data List -->
                <div class="data-list-wrapper">
                    <c:forEach var="clientDto" items="${clientDtoList}">
                        <a href="${pageContext.request.contextPath}/client/clientDetail?client_code=${clientDto.client_code}" class="text-decoration-none text-reset">
                            <div class="row py-2 border-bottom list-item-row text-center">
                                <div class="col-1">${clientDto.client_code}</div>
                                <div class="col-1">
                                    <c:choose>
                                        <c:when test="${clientDto.client_type == 1}">공급처</c:when>
                                        <c:when test="${clientDto.client_type == 2}">가맹점</c:when>
                                        <c:otherwise>기타</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-3">${clientDto.client_name}</div>
                                <div class="col-1">
                                    <c:choose>
                                        <c:when test="${clientDto.client_status == 0}">영업중</c:when>
                                        <c:when test="${clientDto.client_status == 1}">휴업</c:when>
                                        <c:when test="${clientDto.client_status == 2}">폐업</c:when>
                                        <c:otherwise>기타</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-2">${clientDto.boss_name}</div>
                                <div class="col-2">${clientDto.client_emp_name}</div>
                                <div class="col-2">${clientDto.client_tel}</div>
                            </div>
                        </a>
                    </c:forEach>
                </div>

                <c:if test="${empty clientDtoList}">
                    <div class="text-center mt-4 text-muted">등록된 거래처가 없습니다.</div>
                </c:if>

                <!-- Paging -->
                <nav class="mt-4" aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/client/clientList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/client/clientList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/client/clientList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
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
