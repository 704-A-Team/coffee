<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 공급 리스트</title>
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
                <div class="form-section-title">원재료 공급 리스트</div>
                
                <c:if test="${empty provideList}">
			        <div class="col-12">
			            <div class="alert alert-warning text-center" role="alert">
			                <strong>해당 검색어에 부합하는 리스트가 없습니다.</strong>
			            </div>
			        </div>
			    </c:if>

                <div class="row">
                    <c:forEach var="provide" items="${provideList}">
                        <div class="col-md-6 mb-4">
                            <div class="card h-100 shadow-sm">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">${provide.productName}</h5>
                                    <ul class="supply-info-list">
                                        <li><strong>거래처</strong> <span>${provide.clientName}</span></li>
                                        <li>
										    <strong>공급 단위</strong>
										    <span>
										        <fmt:formatNumber value="${provide.provide_amount}" type="number" groupingUsed="true" />
										    </span>
										</li>
                                        <li><strong>삭제 여부</strong> <span>${provide.provide_isdel}</span></li>
                                        <li><strong>등록일</strong> <span>${provide.provide_reg_date}</span></li>
                                    </ul>

                                    <form action="${pageContext.request.contextPath}/provide/provideDetail" method="get" class="mt-auto">
                                        <input type="hidden" name="provide_code" value="${provide.provide_code}" />
                                        <button type="submit" class="btn btn-outline-primary btn-sm w-100">상세 보기</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
				
				<!-- 검색 -->
				<form action="${pageContext.request.contextPath}/provide/provideList" method="get" class="row g-2 mb-4">
				    <!-- 검색조건 콤보박스 -->
				    <div class="col-md-3">
				        <select name="searchType" class="form-select">
				       		<option value="" ${empty param.searchType ? 'selected' : ''}>전체</option>
				            <option value="productName" ${param.searchType == 'productName' ? 'selected' : ''}>제품명</option>
				            <option value="clientName" ${param.searchType == 'clientName' ? 'selected' : ''}>거래처명</option>
				        </select>
				    </div>
				
				    <!-- 검색어 입력 -->
				    <div class="col-md-6">
				        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="검색어를 입력하세요" />
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
                                <a class="page-link" href="${pageContext.request.contextPath}/provide/provideList?currentPage=${page.startPage - page.pageBlock}">
                                    &laquo; 이전
                                </a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/provide/provideList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/provide/provideList?currentPage=${page.startPage + page.pageBlock}">
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
