<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 상세 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --main-brown: #6f4e37;
            --soft-brown: #bfa08e;
            --dark-brown: #4e342e;
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

        .card-header {
            background-color: var(--main-brown);
            color: white;
            font-weight: bold;
        }

        .btn-brown {
            background-color: var(--soft-brown) !important;
            color: white !important;
            border: 1px solid var(--soft-brown) !important;
        }

        .btn-brown:hover {
            background-color: var(--main-brown) !important;
            border-color: var(--main-brown) !important;
            color: white !important;
        }

        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: white !important;
        }

        .btn-brown-outline:hover {
            background-color: var(--main-brown) !important;
            color: white !important;
        }

        .btn-soft-danger {
            background-color: #a94442 !important;
            color: white !important;
            border: 1px solid #a94442 !important;
        }

        .btn-soft-danger:hover {
            background-color: #922d2b !important;
            border-color: #922d2b !important;
        }

        .info-table th {
            width: 180px;
            color: #555;
        }

        .info-table td {
            color: #222;
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
                <div class="form-section-title">발주 상세 정보</div>

                <div class="card shadow-sm border-0">
                    <div class="card-header">
                        원재료명 : ${purchaseDetail.productName}
                    </div>
                    <div class="card-body">
                        <table class="table table-borderless info-table">
                            <tbody>
                                <tr><th>발주 코드</th><td>${purchaseDetail.purchase_code}</td></tr>
                                <tr><th>원재료명</th><td>${purchaseDetail.productName}</td></tr>
                                <tr><th>거래처명</th><td>${purchaseDetail.clientName}</td></tr>
                                <tr><th>공급단위</th>
                                    <td><fmt:formatNumber value="${purchaseDetail.provideAmount}" type="number" groupingUsed="true"/></td></tr>
                                <tr><th>단위</th><td>${purchaseDetail.unitName}</td></tr>
                                <tr><th>단가</th>
                                    <td><fmt:formatNumber value="${purchaseDetail.purchase_danga}" type="number" groupingUsed="true"/></td></tr>
                                <tr><th>발주수량</th>
                                    <td><fmt:formatNumber value="${purchaseDetail.purchase_amount}" type="number" groupingUsed="true"/></td></tr>
                                <tr><th>총금액</th>
                                    <td>
                                        <fmt:formatNumber value="${purchaseDetail.purchase_danga * purchaseDetail.purchase_amount / purchaseDetail.provideAmount}" type="number" groupingUsed="true"/>
                                    </td>
                                </tr>
                                <tr><th>상태</th><td>${purchaseDetail.statusName}</td></tr>
                                <tr><th>입고일</th>
                                	<td>
                                		<fmt:formatDate value="${purchaseDetail.purchase_ipgo_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                	</td>
                                </tr>
                                <tr><th>승인자</th><td>${purchaseDetail.empPermName}</td></tr>
                                <tr><th>거부사유</th><td>${purchaseDetail.purchase_refuse}</td></tr>
                                <tr><th>등록자</th><td>${purchaseDetail.empRegName}</td></tr>
                                <tr><th>등록일</th>
                                    <td>
                                        <fmt:formatDate value="${purchaseDetail.purchase_reg_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- 오른쪽 하단 버튼 -->
                <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                    <form action="${pageContext.request.contextPath}/sw/purchaseApprove" method="post" class="m-0">
                        <input type="hidden" name="purchase_code" value="${purchaseDetail.purchase_code}" />
                        <button type="submit" class="btn btn-brown">승인</button>
                    </form>

                    <button type="button" class="btn btn-soft-danger" data-bs-toggle="modal" data-bs-target="#refuseModal">
                        거부
                    </button>

                    <a href="javascript:history.back()" class="btn btn-brown-outline">목록</a>
                </div>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<!-- 모달: 거부사유 -->
<div class="modal fade" id="refuseModal" tabindex="-1" aria-labelledby="refuseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/sw/purchaseRefuse" method="post">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="refuseModalLabel">거부 사유 입력</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="purchase_code" value="${purchaseDetail.purchase_code}" />
                    <div class="mb-3">
                        <label for="purchase_refuse" class="form-label">거부 사유</label>
                        <textarea class="form-control" id="purchase_refuse" name="purchase_refuse" rows="4" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="submit" class="btn btn-soft-danger">거부 확정</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
