<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>

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
                    <h2>사원 목록</h2>
                    <a href="${pageContext.request.contextPath}/emp/empInForm" class="btn btn-success">사원 등록</a>
                </div>

                <!-- Header -->
                <div class="row fw-bold py-2 border-bottom bg-primary text-white">
                    <div class="col-1">사원 번호</div>
                    <div class="col-2">이름</div>
                    <div class="col-2">전화번호</div>
                    <div class="col-1">소속 부서</div>
                    <div class="col-1">직급</div>
                    <div class="col-1">급여</div>
                    <div class="col-2">이메일</div>
                    <div class="col-1">등록일</div>
                    <div class="col-1">입사일</div>
                </div>

                <!-- Data List -->
                <div class="data-list-wrapper">
                    <c:forEach var="empDto" items="${empDtoList}">
                        <a href="${pageContext.request.contextPath}/emp/empDetail?emp_code=${empDto.emp_code}" class="text-decoration-none text-reset">
                            <div class="row py-2 border-bottom list-item-row">
                                <div class="col-1">${empDto.emp_code}</div>
                                <div class="col-2">${empDto.emp_name}</div>
                                <div class="col-2">${empDto.emp_tel}</div>
                                <div class="col-1">${empDto.dept_code}</div>
                                <div class="col-1">${empDto.emp_grade_detail}</div>
                                <div class="col-1">${empDto.emp_sal}</div>
                                <div class="col-2">${empDto.emp_email}</div>
                                <div class="col-1">${empDto.empRegDateFormatted}</div>
                                <div class="col-1">${empDto.emp_ipsa_date}</div>
                            </div>
                        </a>
                    </c:forEach>
                </div>

                <c:if test="${empty empDtoList}">
                    <div class="text-center mt-4 text-muted">등록된 사원이 없습니다.</div>
                </c:if>

                <!-- Paging -->
                <nav class="mt-4" aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/emp/empList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
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
