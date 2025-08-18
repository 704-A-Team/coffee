<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>입고 현황</title>
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
        }

        .btn-brown:hover {
		    background: #ccc !important;   /* 회색 배경 */
		    color: #333 !important;         /* 진회색 글자 */
		    border-color: #ccc !important;  /* 회색 테두리 */
		}

        .btn-brown-outline {
            background-color: transparent !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
        }

        .btn-brown-outline:hover {
		    background: #ccc !important;   /* 회색 배경 */
		    color: #333 !important;         /* 글자색 진회색 */
		    border-color: #ccc !important;  /* 테두리 회색 */
		}

        .table th, .table td {
            vertical-align: middle !important;
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

<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">입고 현황</div>

                <!-- 검색 상단 배치 -->
                <form action="${pageContext.request.contextPath}/sw/purchaseList" method="get" class="row g-2 mb-4">
                    <input type="hidden" name="searchType" value="productName" />
                    <div class="col-md-9">
                        <input type="text" name="searchKeyword" value="${param.searchKeyword}" class="form-control" placeholder="거래처명을 입력하세요" />
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-brown w-100">검색</button>
                    </div>
                </form>

                <!-- 검색 결과 없음 안내 -->
                <c:if test="${not empty param.searchKeyword and fn:length(fn:trim(param.searchKeyword)) > 0 and empty purchaseList}">
                    <div class="alert alert-warning text-center" role="alert">
                        <strong>해당 검색어에 부합하는 리스트가 없습니다.</strong>
                    </div>
                </c:if>

                <!-- 리스트 테이블 -->
                <table class="table table-bordered align-middle text-center bg-white shadow-sm">
                    <thead class="table-light">
                        <tr>
                            <th style="width: 5%;">등록코드</th>
                            <th style="width: 20%;">거래처명</th>
                            <th style="width: 10%;">총액</th>
                            <th style="width: 10%;">발주유형</th>
                            <th style="width: 10%;">상태</th>
                            <th style="width: 10%;">요청자</th>
                            <th style="width: 15%;">등록일</th>
                            <th style="width: 5%;">상세</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="purchase" items="${purchaseList}">
                            <tr>
                            	<td>${purchase.purchase_code}</td>
                            	<td>${purchase.clientName}</td>
                                <td>
                                	<fmt:formatNumber value="${purchase.totalPrice}" pattern="#,###"/>
                                </td>
                                <td>${purchase.purchase_type == 0 ? '수동발주' : '자동발주'}</td>
                                <td>${purchase.statusName}</td>
                                <td>${purchase.empRegName}</td>
                                <td>
                                    <fmt:formatDate value="${purchase.purchase_reg_date}" pattern="yyyy-MM-dd HH:mm" />
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/sw/purchaseDetail" method="get">
                                        <input type="hidden" name="purchase_code" value="${purchase.purchase_code}" />
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

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
