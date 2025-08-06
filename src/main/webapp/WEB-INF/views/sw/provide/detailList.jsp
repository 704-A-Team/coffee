<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 상세 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        .card-header {
            background-color: #6ca0dc !important;
            color: white !important;
            font-weight: bold;
        }
        .text-black {
            color: black;
        }
        .info-table th {
            width: 180px;
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
                <div class="form-section-title">원재료 상세 정보</div>

                <div class="card shadow-sm border-0">
                    <div class="card-header">
                        ${provideDetail.productName} 공급 정보
                    </div>
                    <div class="card-body text-black">
                        <table class="table table-borderless info-table">
                            <tbody>
                                <tr>
                                    <th>공급 코드</th>
                                    <td>${provideDetail.provide_code}</td>
                                </tr>
                                <tr>
                                    <th>제품명</th>
                                    <td>${provideDetail.productName}</td>
                                </tr>
                                <tr>
                                    <th>거래처명</th>
                                    <td>${provideDetail.clientName}</td>
                                </tr>
                                <tr>
								    <th>공급 단위</th>
								    <td>
								        <fmt:formatNumber value="${provideDetail.provide_amount}" type="number" groupingUsed="true" />
								    </td>
								</tr>
                                <tr>
                                    <th>단위</th>
                                    <td>${provideDetail.unitName}</td>
                                </tr>
                                <tr>
                                    <th>단가</th>
                                    <td>
								        <fmt:formatNumber value="${provideDetail.current_danga}" type="number" groupingUsed="true" />
								    </td>
                                </tr>
                                <tr>
                                    <th>삭제 구분</th>
                                    <td>${provideDetail.provide_isdel}</td>
                                </tr>
                                <tr>
                                    <th>등록일</th>
                                    <td>${provideDetail.provide_reg_date}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- 버튼 영역 -->
                <div class="d-flex gap-3 justify-content-end mt-4">
                    <a href="${pageContext.request.contextPath}/provide/provideModifyForm?provide_code=${provideDetail.provide_code}" class="btn btn-primary">수정</a>
                    <form action="${pageContext.request.contextPath}/provide/provideDelete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                        <input type="hidden" name="provide_code" value="${provideDetail.provide_code}">
                        <button type="submit" class="btn btn-danger">삭제</button>
                    </form>
                </div>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
