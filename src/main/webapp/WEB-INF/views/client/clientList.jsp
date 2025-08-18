<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 목록</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* 사원 목록 페이지와 동일한 마크업 사용 */
        .clickable-row { cursor: pointer; }
        /* 필요시 전역에 이미 정의되어 있으면 이 스타일은 무시됨 */
        .form-section-title { font-weight: 600; font-size: 1.5rem; }
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
                        <div class="form-section-title m-0">거래처 목록</div>
                        <a href="${pageContext.request.contextPath}/client/clientInForm" class="btn btn-success">거래처 등록</a>
                    </div>

                    <table class="table table-bordered table-hover text-center">
                        <thead class="table-secondary">
                            <tr>
                                <!-- <th>코드</th> -->
                                <th>유형</th>
                                <th>거래처명</th>
                                <th>상태</th>
                                <th>대표자명</th>
                                <!-- <th>담당 사원</th> -->
                                <th>전화번호</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="clientDto" items="${clientDtoList}">
                                <tr class="clickable-row"
                                    onclick="location.href='${pageContext.request.contextPath}/client/clientDetail?client_code=${clientDto.client_code}'">
                                    <%-- <td>${clientDto.client_code}</td> --%>
                                    <td>${clientDto.client_type_br}</td>
                                    <td>${clientDto.client_name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${clientDto.client_status == 0}">영업중</c:when>
                                            <c:when test="${clientDto.client_status == 1}">휴업중</c:when>
                                            <c:when test="${clientDto.client_status == 2}">폐업</c:when>
                                            <c:otherwise>기타</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${clientDto.boss_name}</td>
                                    <td>${clientDto.client_tel}</td>
                                    <td>${clientDto.clientRegDateFormatted}</td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty clientDtoList}">
                                <tr>
                                    <td colspan="6" class="text-muted">등록된 거래처가 없습니다.</td>
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
                                       href="${pageContext.request.contextPath}/client/clientList?currentPage=${page.startPage - page.pageBlock}">이전</a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/client/clientList?currentPage=${i}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/client/clientList?currentPage=${page.startPage + page.pageBlock}">다음</a>
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
