<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    :root {
        --main-brown: #6f4e37;
        --light-brown: #e6d3c1;
        --dark-brown: #4e342e;
    }

    body {
        background-color: #f9f5f1;
        font-family: 'Segoe UI', sans-serif;
    }

    .form-section-title {
        border-left: 5px solid var(--main-brown);
        padding-left: 12px;
        margin-bottom: 24px;
        font-weight: 700;
        font-size: 1.8rem;
        color: var(--dark-brown);
    }

    .card {
        border: 1px solid #ddd;
        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        transition: all 0.3s ease-in-out;
    }

    .card:hover {
        transform: scale(1.01);
        box-shadow: 0 6px 10px rgba(0,0,0,0.1);
    }

    .card-title {
        font-weight: 700;
        color: var(--main-brown);
    }

    .dept-info-list {
        list-style: none;
        padding: 0;
        margin-bottom: 1rem;
        font-size: 0.95rem;
    }

    .dept-info-list li {
        display: flex;
        margin-bottom: 6px;
    }

    .dept-info-list li strong {
        width: 40%;
        color: #555;
    }

    .dept-info-list li span {
        width: 60%;
        color: #333;
    }

    .btn-brown {
        background-color: #ffffff !important;
        color: #333 !important;
        border: 1px solid #ccc !important;
        box-shadow: none !important;
        transition: background-color 0.2s ease-in-out;
    }

    .btn-brown:hover {
        background-color: #e9ecef !important;
        color: #000 !important;
        border: 1px solid #bbb !important;
    }

    .pagination .page-link {
        color: var(--main-brown);
    }

    .pagination .page-item.active .page-link {
        background-color: var(--main-brown);
        border-color: var(--main-brown);
        color: white;
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container">
                <div class="form-section-title d-flex justify-content-between align-items-center">
                    부서 목록
                    <a href="${pageContext.request.contextPath}/dept/deptInForm" class="btn btn-brown">부서 등록</a>
                </div>

                <!-- 카드 리스트 -->
                <div class="row">
                    <c:forEach var="deptDto" items="${deptDtoList}">
                        <div class="col-md-6 mb-4">
                            <a href="${pageContext.request.contextPath}/dept/deptDetail?dept_code=${deptDto.dept_code}" class="text-decoration-none text-reset">
                                <div class="card h-100">
                                    <div class="card-body d-flex flex-column">
                                        <h5 class="card-title">${deptDto.dept_name}</h5>
                                       <ul class="dept-info-list">
							<c:if test="${deptDto.dept_code != 1006}">
							    <li><strong>부서장</strong> <span>${deptDto.dept_emp_name}</span></li>
							</c:if>						    <li><strong>대표번호</strong> <span>${deptDto.dept_tel}</span></li>
						    <li><strong>등록일</strong> <span>${deptDto.deptRegDateFormatted}</span></li>
						</ul>
                                        <div class="mt-auto text-end">
                                            <%-- <span class="text-muted small">코드: ${deptDto.dept_code}</span> --%>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${empty deptDtoList}">
                    <div class="text-center mt-4 text-muted">등록된 부서가 없습니다.</div>
                </c:if>

                <!-- 페이징 -->
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
            </div>
        </main>

        <%@ include file="../footer.jsp" %>
    </div>
</div>

</body>
</html>
