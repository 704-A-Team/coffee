<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 현황</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        .card-title {
            font-weight: bold;
        }
        .supply-info-list {
            list-style: none;
            padding: 0;
            margin-bottom: 1rem;
        }
        .supply-info-list li {
            display: flex;
            margin-bottom: 6px;
        }
        .supply-info-list li strong {
            width: 40%;
        }
        .supply-info-list li span {
            width: 60%;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- HEADER -->
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <!-- SIDEBAR -->
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <!-- 본문 -->
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">발주 현황</div>
                
                <c:if test="${empty purchaseList}">
			        <div class="col-12">
			            <div class="alert alert-warning text-center" role="alert">
			                <strong>해당 검색어에 부합하는 리스트가 없습니다.</strong>
			            </div>
			        </div>
			    </c:if>

                <div class="row">
                    <!-- 목록 표시 -->
					<table class="table table-bordered align-middle text-center">
					    <thead class="table-primary">
					        <tr>
					            <th style="width: 30%;">제품명</th>
					            <th style="width: 20%;">거래처</th>
					            <th style="width: 20%;">상태</th>
					            <th style="width: 20%;">등록일</th>
					            <th style="width: 10%;">상세</th>
					        </tr>
					    </thead>
					    <tbody>
					        <c:forEach var="purchase" items="${purchaseList}">
					            <tr>
					                <td>${purchase.productName}</td>
					                <td>${purchase.clientName}</td>
					                <td>${purchase.statusName}</td>
					                <td>${purchase.purchase_reg_date}</td>
					                <td>
					                    <form action="${pageContext.request.contextPath}/sw/purchaseDetail" method="get">
					                        <input type="hidden" name="purchase_code" value="${purchase.purchase_code}" />
					                        <button type="submit" class="btn btn-sm btn-outline-primary">보기</button>
					                    </form>
					                </td>
					            </tr>
					        </c:forEach>
					    </tbody>
					</table>
                </div>
				
				<!-- 검색 -->
				<form action="${pageContext.request.contextPath}/sw/purchaseList" method="get" class="row g-2 mb-4">
				    <!-- searchType 고정 -->
				    <input type="hidden" name="searchType" value="productName" />
				    
				    <!-- 검색어 입력 -->
				    <div class="col-md-9">
				        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="제품명을 입력하세요" />
				    </div>
				    
				    <div class="col-md-3">
				        <button type="submit" class="btn btn-primary w-100">검색</button>
				    </div>
				</form>
				
                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/purchaseList?currentPage=${page.startPage - page.pageBlock}">
                                    &laquo; 이전
                                </a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/purchaseList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/sw/purchaseList?currentPage=${page.startPage + page.pageBlock}">
                                    다음 &raquo;
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
