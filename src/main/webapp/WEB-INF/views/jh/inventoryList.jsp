<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>재고관리</title>

    <!-- 부트스트랩 및 아이콘 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .disabled-button { pointer-events: none; opacity: 0.5; }
        .action-column { width: 270px; }
        .fixed-btn {
            display: inline-flex; align-items: center; justify-content: center;
            height: 40px; padding: 0 .75rem; box-sizing: border-box;
        }
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1"><!-- 좌/우 영역 -->
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 래퍼: 푸터 하단 고정용 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1">
            	<div class="container mt-4 p-4">
                	<div class="form-section-title">수주 목록</div>
                    <!-- 오른쪽: 마감/취소 -->
                    <div class="d-flex justify-content-end mb-3">
                    	<button class="btn btn-danger fixed-btn me-2" ${isClosed ? 'disabled' : ''}>마감</button>
                        <button class="btn btn-secondary fixed-btn"   ${isClosed ? '' : 'disabled'}>마감취소</button>
                 	</div>

	                <!-- 테이블 -->
	                <table class="table table-bordered text-center">
	                    <thead class="table-secondary">
	                        <tr>
	                            <th>제품코드</th>
	                            <th>제품명</th>
	                            <th>단위</th>
	                            <th>제품유형</th>
	                            <th>납품여부</th>
	                            <th>실재고량</th>
	                            <th>가용재고량</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:choose>
	                            <c:when test="${not empty inventoryList}">
	                                <c:forEach var="item" items="${inventoryList}">
	                                    <tr>
	                                        <td>${item.product_code}</td>
	                                        <td>${item.product_name}</td>
	                                        <td>${item.unit_contents}</td>
	                                        <td>${item.type_contents}</td>
	                                        <td>
	                                        	<c:if test="${item.product_isorder }">납품</c:if>
	                                        	<c:if test="${not item.product_isorder }">비납품</c:if>
	                                        </td>
	                                        <td>${item.real_stock}</td>
	                                        <td>${item.usable_stock}</td>
	                                    </tr>
	                                </c:forEach>
	                            </c:when>
	                            <c:otherwise>
	                                <tr>
	                                    <td colspan="7">표시할 데이터가 없습니다.</td>
	                                </tr>
	                            </c:otherwise>
	                        </c:choose>
	                    </tbody>
	                </table>

	                <!-- 페이징 -->
	                <c:if test="${not empty page}">
	                    <nav aria-label="Page navigation" class="mt-4">
	                        <ul class="pagination justify-content-center">
	                            <c:if test="${page.startPage > page.pageBlock}">
	                                <c:url var="prevUrl" value="${pageContext.request.contextPath}/inventory/list">
	                                    <c:param name="page" value="${page.startPage - page.pageBlock}" />
	                                </c:url>
	                                <li class="page-item">
	                                    <a class="page-link" href="${prevUrl}">이전</a>
	                                </li>
	                            </c:if>
	
	                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
	                                <c:url var="pageUrl" value="${pageContext.request.contextPath}/inventory/list">
	                                    <c:param name="page" value="${i}" />
	                                </c:url>
	                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
	                                    <a class="page-link" href="${pageUrl}">${i}</a>
	                                </li>
	                            </c:forEach>
	
	                            <c:if test="${page.endPage < page.totalPage}">
	                                <c:url var="nextUrl" value="${pageContext.request.contextPath}/inventory/list">
	                                    <c:param name="page" value="${page.startPage + page.pageBlock}" />
	                                </c:url>
	                                <li class="page-item">
	                                    <a class="page-link" href="${nextUrl}">다음</a>
	                                </li>
	                            </c:if>
	                        </ul>
	                    </nav>
	                </c:if>
                </div>
			</main>
			<%@ include file="../footer.jsp" %>

        </div><!-- /본문 래퍼 -->
    </div><!-- /좌우 영역 -->
</body>
</html>
