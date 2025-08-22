	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 신청 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">

                <div class="form-section-title">생산 신청 목록</div>
<%-- 
                <!-- 🔍 검색 -->
                <form action="${pageContext.request.contextPath}/km/mfgList" method="get" class="row g-2 mb-4">
                    <input type="hidden" name="searchType" value="mfg_code" />
                    <div class="col-md-9">
                        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="신청코드를 입력하세요" />
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-primary w-100">검색</button>
                    </div>
                </form>

                <!-- 검색 결과 없을 때 -->
                <c:if test="${not empty param.searchKeyword and fn:length(fn:trim(param.searchKeyword)) > 0 and empty mfgList}">
                    <div class="col-12">
                        <div class="alert alert-warning text-center" role="alert">
                            <strong>해당 검색어에 부합하는 신청이 없습니다.</strong>
                        </div>
                    </div>
                </c:if>
 --%>
                <!-- 📋 목록 테이블 -->
				<table class="table table-bordered table-hover mt-4">
				    <thead class="table-light">
				        <tr class="text-center align-middle">
				            <th>신청코드</th>
				            <th>신청자</th>
				            <th>신청부서</th>
				            <th>신청일</th>
				            <th>상세보기</th>
				            <th>승인</th>
				        </tr>
				    </thead>
				    <tbody class="text-center align-middle">
				        <c:forEach var="mfg" items="${mfgList}">
				            <tr>
				                <td>${mfg.mfg_code}</td>
				                <td>${mfg.emp_name}</td>
				                <td>${mfg.dept_name}</td>
				                <!-- mfg_reg_date가 LocalDateTime이라면 그냥 출력(서버에서 문자열로 변환하면 더 예쁨) -->
				                <td>${mfg.mfg_reg_date}</td>
				
				                <td>
				                    <form action="${pageContext.request.contextPath}/km/mfgDetail" method="get" class="m-0">
				                        <input type="hidden" name="mfg_code" value="${mfg.mfg_code}">
				                        <button type="submit" class="btn btn-sm btn-outline-primary">보기</button>
				                    </form>
				                </td>
				                <td>
				                    <form action="${pageContext.request.contextPath}/km/mfgApproveDetail" method="get" class="m-0">
				                        <input type="hidden" name="mfg_code" value="${mfg.mfg_code}">
				                        <button type="submit" class="btn btn-sm btn-success">승인</button>
				                    </form>
				                </td>
				            </tr>
				        </c:forEach>
				
				        <c:if test="${empty mfgList}">
				            <tr>
				                <td colspan="6" class="text-center text-muted">생산 신청이 없습니다.</td>
				            </tr>
				        </c:if>
				    </tbody>
				</table>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
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
