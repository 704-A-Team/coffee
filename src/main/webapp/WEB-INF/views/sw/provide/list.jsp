<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 공급 리스트</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
        }

        body {
            background-color: #f9f5f1;
        }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }

        .btn-brown {
            background-color: white !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
            transition: 0.2s ease-in-out;
        }

        .btn-brown:hover {
            background: #ccc !important;
            color: #333 !important;
        }

        .search-form input.form-control {
            border: 1px solid #ced4da !important;
            background-color: #fff;
        }

        .pagination .page-link {
            color: var(--main-brown);
        }

        .pagination .page-item.active .page-link {
            background-color: var(--main-brown);
            border-color: var(--main-brown);
            color: white;
        }

        .table th, .table td {
            vertical-align: middle !important;
        }
        
        .btn-brown-outline {
		    background-color: transparent !important;
		    color: var(--main-brown) !important;
		    border: 1px solid var(--main-brown) !important; /* ← !important 추가 */
		}
		
		.btn-brown-outline:hover {
		    background: #ccc !important;
		    color: #333 !important;
		    border: 1px solid #ccc !important; /* ← !important 추가 */
		}
		        
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <!-- 리스트 제목 + 등록 버튼 -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="form-section-title mb-0">원재료 공급 리스트</div>
                    <a href="${pageContext.request.contextPath}/provide/provideInForm" class="btn btn-brown">+ 등록</a>
                </div>

                <!-- 검색 -->
                <form action="${pageContext.request.contextPath}/provide/provideList" method="get" class="row g-2 mb-4 search-form">
                    <div class="col-md-3">
                        <select name="searchType" class="form-select">
                            <option value="" ${empty param.searchType ? 'selected' : ''}>전체</option>
                            <option value="productName" ${param.searchType == 'productName' ? 'selected' : ''}>제품명</option>
                            <option value="clientName" ${param.searchType == 'clientName' ? 'selected' : ''}>거래처명</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="검색어를 입력하세요" />
                    </div>

                    <div class="col-md-3">
                        <button type="submit" class="btn btn-brown w-100">검색</button>
                    </div>
                </form>

                <c:if test="${empty provideList}">
                    <div class="col-12">
                        <div class="alert alert-warning text-center" role="alert">
                            <strong>해당 검색어에 부합하는 리스트가 없습니다.</strong>
                        </div>
                    </div>
                </c:if>

                <!-- 리스트 테이블 -->
                <table class="table table-bordered align-middle text-center bg-white shadow-sm">
                    <thead class="table-light">
                        <tr>
                            <th>제품명</th>
                            <th>거래처</th>
                            <th>공급 단위</th>
                            <th>상태</th>
                            <th>등록일</th>
                            <th>상세</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="provide" items="${provideList}">
                            
                            <tr>
                                <td>${provide.productName}</td>
                                <td>${provide.clientName}</td>
                                <td>
                                    <fmt:formatNumber value="${provide.provide_amount}" type="number" groupingUsed="true" />
                                    ${provide.unitName}
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${provide.provide_isdel == 0}">발주 가능</c:when>
                                        <c:otherwise>발주 불가</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatDate value="${provide.provide_reg_date}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/provide/provideDetail" method="get" class="m-0">
                                        <input type="hidden" name="provide_code" value="${provide.provide_code}" />
                                        <button type="submit" class="btn btn-sm btn-brown-outline">보기</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/provide/provideList?currentPage=${page.startPage - page.pageBlock}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">
                                   &laquo; 이전
                                </a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/provide/provideList?currentPage=${i}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">
                                   ${i}
                                </a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/provide/provideList?currentPage=${page.startPage + page.pageBlock}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">
                                   다음 &raquo;
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
