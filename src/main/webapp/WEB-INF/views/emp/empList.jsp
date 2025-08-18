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
    .list-item-row:hover { background-color: #e2f3ff; }
    .data-list-wrapper a:nth-of-type(odd) .list-item-row { background-color: #f8f9fa; }
    .data-list-wrapper a:nth-of-type(even) .list-item-row { background-color: #e9ecef; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문과 푸터를 같은 컬럼에 배치 -->
        <div class="d-flex flex-column flex-grow-1">
     <main class="flex-grow-1 p-4">
    <div class="container p-0">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div class="form-section-title m-0">사원 목록</div>
            <!-- 기존 버튼 유지 -->
            <a href="${pageContext.request.contextPath}/emp/empInForm" class="btn btn-success">사원 등록</a>
        </div>

        <table class="table table-bordered table-hover text-center">
            <thead class="table-secondary">
                <tr>
                    <th>사원 번호</th>
                    <th>이름</th>
                    <th>소속 부서</th>
                    <th>직급</th>
                    <th>등록일</th>
                    <th>입사일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="empDto" items="${empDtoList}">
                    <tr class="clickable-row"
                        style="cursor:pointer"
                        onclick="location.href='${pageContext.request.contextPath}/emp/empDetail?emp_code=${empDto.emp_code}'">
                        <td>${empDto.emp_code}</td>
                        <td>${empDto.emp_name}</td>
                        <td>${empDto.dept_code}</td>
                        <td>${empDto.emp_grade_detail}</td>
                        <td>${empDto.empRegDateFormatted}</td>
                        <td>${empDto.emp_ipsa_date}</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty empDtoList}">
                    <tr>
                        <td colspan="6" class="text-muted">등록된 사원이 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <!-- Pagination -->
        <nav class="mt-4" aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${page.startPage > page.pageBlock}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage - page.pageBlock}">이전</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                    <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/emp/empList?currentPage=${i}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${page.endPage < page.totalPage}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage + page.pageBlock}">다음</a>
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
