<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>가격 내역</title>
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
            background: #ccc !important;   /* 발주 등록 초기화 버튼과 동일 */
            color: #333 !important;        /* 글자색 유지 */
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

        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: white !important;
        }

        .btn-brown-outline:hover {
            background-color: #ccc !important; /* 회색 배경 */
            color: #333 !important;            /* 진회색 글자 */
            border-color: #ccc !important;     /* 회색 테두리 */
        }

        /* 발주 현황과 동일: 셀 세로정렬 고정 */
        .table th, .table td {
            vertical-align: middle !important;
        }
        
        .table th, 
		.table td {
		    vertical-align: middle !important;
		    padding-top: 0.75rem;   /* 위쪽 여백 */
		    padding-bottom: 0.75rem;/* 아래쪽 여백 */
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
                <!-- 리스트 제목 -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="form-section-title mb-0">가격 내역</div>
                </div>

                <!-- 리스트 테이블 -->
                <div class="table-responsive mt-5">
                    <table class="table table-bordered align-middle text-center bg-white shadow-sm">
                        <thead class="table-light">
                            <tr>
                                <th>제품코드</th>
                                <th>제품명</th>
                                <th>가격변동시작일자</th>
                                <th>단위</th>
                                <th>가격</th>
                                <th>가격변동종료일자</th>
                                <th>등록자</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="wonProductPrice" items="${wonProductPriceList}">
                                <tr>
                                    <td>${wonProductPrice.product_code}</td>
                                    <td>${wonProductPrice.productName}</td>
                                    <td>${empty wonProductPrice.start_date ? '-' : fn:replace(fn:substring(wonProductPrice.start_date,0,16),'T',' ')}</td>
                                    <td>1${wonProductPrice.unitName}</td>
                                    <td>${wonProductPrice.price}</td>
                                    <td>${empty wonProductPrice.end_date ? '-' : fn:replace(fn:substring(wonProductPrice.end_date,0,16),'T',' ')}</td>
                                    <td>${wonProductPrice.regName}</td>
                                    <td>
                                        <fmt:formatDate value="${wonProductPrice.price_reg_date}" pattern="yyyy-MM-dd HH:mm" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-end gap-2 mt-4">
                    <a href="javascript:history.back()" class="btn btn-brown-outline">목록</a>
                </div>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-3">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/sw/wonProductPriceList?product_code=${param.product_code}&currentPage=${page.startPage - page.pageBlock}">
                                   &laquo; 이전
                                </a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/sw/wonProductPriceList?product_code=${param.product_code}&currentPage=${i}">
                                   ${i}
                                </a>
                            </li>
                        </c:forEach>

                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/sw/wonProductPriceList?product_code=${param.product_code}&currentPage=${page.startPage + page.pageBlock}">
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
