	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 승인 내역</title>
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

                <div class="form-section-title">생산 승인 내역</div>

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

				<!-- 📋 승인 완료 보고 목록 테이블 -->
				<table class="table table-bordered table-hover mt-4">
				    <thead class="table-light">
				        <tr class="text-center align-middle">
				            <th>생산신청코드</th>
				            <th>제품명</th>
				            <th>요청 수량</th>
				            <th>생산 수량</th> 
				            <th>완료 예정일</th>
				            <th>상태</th>
				            <th>비고</th>
				            <th>상세보기</th>
				            <th>보고</th>
				        </tr>
				    </thead>
				    <tbody class="text-center align-middle">
				        <c:forEach var="mfg" items="${mfgReportList}">
				            <tr>
				                <td>${mfg.mfg_code}</td>
				                <td>${mfg.product_name}</td>
				                <td>${mfg.mfg_amount}</td>
		                     	<td>
					                <c:choose>
								        <c:when test="${mfg.mfg_qty == 0}">-</c:when>
								        <c:otherwise>${mfg.mfg_qty}</c:otherwise>
					                </c:choose>
					            </td>
				                <td>
				                    <c:choose>
				                        <c:when test="${not empty mfg.mfg_due_date}">${mfg.mfg_due_date}</c:when>
				                        <c:otherwise>-</c:otherwise>
				                    </c:choose>
				                </td>
				               <td>
								    <c:choose>
								        <c:when test="${mfg.mfg_status == 1}">요청</c:when>
								        <c:when test="${mfg.mfg_status == 3}">거부</c:when>
								        <c:when test="${mfg.mfg_status == 4}">승인 완료</c:when>
								        <c:when test="${mfg.mfg_status == 5}">생산 완료</c:when>
								        <c:when test="${mfg.mfg_status == 6}">마감</c:when>
								        <c:otherwise>-</c:otherwise>
								    </c:choose>
								</td>
				                <td>
				                    <c:choose>
				                        <c:when test="${not empty mfg.mfg_contents}">${mfg.mfg_contents}</c:when>
				                        <c:otherwise>-</c:otherwise>
				                    </c:choose>
				                </td>
				                <td>
					                <c:choose>
										<c:when test="${mfg.mfg_status == 5 or mfg.mfg_status == 6}">
						                    <form action="${pageContext.request.contextPath}/km/mfgReportDetail" method="get" class="m-0">
						                        <input type="hidden" name="mfg_code" value="${mfg.mfg_code}">
						                        <input type="hidden" name="product_code" value="${mfg.product_code}">
						                        <button type="submit" class="btn btn-sm btn-outline-secondary">상세보기</button>
						                    </form>
						                </c:when>
					                    <c:otherwise>
							            <button type="button" class="btn btn-sm btn-outline-secondary" disabled>상세보기</button>
								        </c:otherwise>
					                </c:choose>
				                </td>
								<td>
								    <c:choose>
								        <c:when test="${mfg.mfg_status == 4}">
								            <form action="${pageContext.request.contextPath}/km/mfgReportForm" method="get" class="m-0">
								                <input type="hidden" name="mfg_code" value="${mfg.mfg_code}">
								                <input type="hidden" name="product_code" value="${mfg.product_code}">
								                <button type="submit" class="btn btn-sm btn-outline-primary">보고</button>
								            </form>
								        </c:when>
								        <c:otherwise>
								            <button type="button" class="btn btn-sm btn-outline-secondary" disabled>보고</button>
								        </c:otherwise>
								    </c:choose>
								</td>
				            </tr>
				        </c:forEach>
				
				        <c:if test="${empty mfgReportList}">
				            <tr>
				                <td colspan="8" class="text-center text-muted">승인 완료된 생산 신청이 없습니다.</td>
				            </tr>
				        </c:if>
				    </tbody>
				</table>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgReportList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgReportList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/km/mfgReportList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
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