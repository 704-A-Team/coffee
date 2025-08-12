<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 상세 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --main-brown: #6f4e37;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
        }

        body {
            background-color: #f9f5f1;
            font-family: 'Segoe UI', sans-serif;
        }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }

        .card-detail {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            padding: 24px;
        }

        .info-table th {
            width: 160px;
            color: #555;
        }

        .info-table td {
            color: #333;
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

        /* 수정 버튼 (파란색) */
		.btn-brown {
		    background-color: #0d6efd !important; /* 부트스트랩 기본 파랑 */
		    color: white !important;
		    border: 1.5px solid #0d6efd !important;
		}
		
		.btn-brown:hover {
		    background-color: #0b5ed7 !important; /* hover 시 더 진한 파랑 */
		    border-color: #0b5ed7 !important;
		    color: white !important;
		}
		
		/* 삭제 버튼 (빨간색) */
		.btn-soft-danger {
		    background-color: #dc3545 !important; /* 부트스트랩 기본 빨강 */
		    color: white !important;
		    border: 1.5px solid #dc3545 !important;
		}
		
		.btn-soft-danger:hover {
		    background-color: #bb2d3b !important; /* hover 시 더 진한 빨강 */
		    border-color: #bb2d3b !important;
		}
		
		.btn-secondary-custom {
		    background:#eee!important;      /* 밝은 회색 배경 */
		    color:#333!important;            /* 진회색 글자 */
		    border:1px solid #ccc!important; /* 연한 회색 테두리 */
		}
		.btn-secondary-custom:hover {
		    background:#ccc!important;       /* hover 시 더 진한 회색 */
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
                <div class="form-section-title">원재료 상세 정보</div>

                <!-- 카드 -->
                <div class="card-detail">
                    <table class="table table-borderless info-table mb-0">
                        <tbody>
                            <tr><th>공급 코드</th><td>${provideDetail.provide_code}</td></tr>
                            <tr><th>제품명</th><td>${provideDetail.productName}</td></tr>
                            <tr><th>거래처명</th><td>${provideDetail.clientName}</td></tr>
                            <tr>
                                <th>공급 단위</th>
                                <td><fmt:formatNumber value="${provideDetail.provide_amount}" type="number" groupingUsed="true"/></td>
                            </tr>
                            <tr><th>단위</th><td>${provideDetail.unitName}</td></tr>
                            <tr>
                                <th>단가</th>
                                <td><fmt:formatNumber value="${provideDetail.current_danga}" type="number" groupingUsed="true"/></td>
                            </tr>
                            <tr>
                                <th>삭제 구분</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${provideDetail.provide_isdel == 0}">발주 가능</c:when>
                                        <c:otherwise>발주 불가</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td>
                                    <fmt:formatDate value="${provideDetail.provide_reg_date}" pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- 버튼들: 카드 밖, 우측 하단 정렬 -->
                <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                    <a href="${pageContext.request.contextPath}/provide/provideModifyForm?provide_code=${provideDetail.provide_code}" class="btn btn-brown">수정</a>
                    <form action="${pageContext.request.contextPath}/provide/provideDelete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                        <input type="hidden" name="provide_code" value="${provideDetail.provide_code}">
                        <button type="submit" class="btn btn-soft-danger">삭제</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/provide/provideList" class="btn btn-brown-outline">목록</a>
                </div>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>
</body>
</html>
