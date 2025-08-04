<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 상세 정보</title>
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
                <div class="form-section-title">발주 상세 정보</div>

                <div class="card shadow-sm border-0">
                    <div class="card-header">
                        원재료명 : ${purchaseDetail.productName}
                    </div>
                    <div class="card-body text-black">
                        <table class="table table-borderless info-table">
                            <tbody>
                                <tr>
                                    <th>발주 코드</th>
                                    <td>${purchaseDetail.purchase_code}</td>
                                </tr>
                                <tr>
                                    <th>원재료명</th>
                                    <td>${purchaseDetail.productName}</td>
                                </tr>
                                <tr>
                                    <th>거래처명</th>
                                    <td>${purchaseDetail.clientName}</td>
                                </tr>
                                <tr>
								    <th>공급단위</th>
								    <td>
								    	<fmt:formatNumber value="${purchaseDetail.provideAmount}" type="number" groupingUsed="true"/>
								    </td>
								</tr>
                                <tr>
                                    <th>단위</th>
                                    <td>${purchaseDetail.unitName}</td>
                                </tr>
                                <tr>
								    <th>단가</th>
								    <td>
								    	<fmt:formatNumber value="${purchaseDetail.purchase_danga}" type="number" groupingUsed="true"/>
								    </td>
								</tr>
                                <tr>
								    <th>발주수량</th>
								    <td>
								    	<fmt:formatNumber value="${purchaseDetail.purchase_amount}" type="number" groupingUsed="true"/>
								    </td>
								</tr>
                                <tr>
							    	<th>총금액</th>
								    <td>
								        <fmt:formatNumber value="${purchaseDetail.purchase_danga * purchaseDetail.purchase_amount / purchaseDetail.provideAmount}" type="number" groupingUsed="true"/>
								    </td>
								</tr>
                                <tr>
                                    <th>상태</th>
                                    <td>${purchaseDetail.statusName}</td>
                                </tr>
                                <tr>
                                    <th>승인자</th>
                                    <td>${purchaseDetail.empPermName}</td>
                                </tr>
                                <tr>
                                    <th>거부사유</th>
                                    <td>${purchaseDetail.purchase_refuse}</td>
                                </tr>
                                <tr>
                                    <th>등록자</th>
                                    <td>${purchaseDetail.empRegName}</td>
                                </tr>
                                <tr>
                                    <th>등록일</th>
                                    <td>${purchaseDetail.purchase_reg_date}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- 목록 버튼 -->
				<div class="mt-4 text-end">
				    <a href="javascript:history.back()" class="btn btn-secondary">목록보기</a>
				</div>
				<!-- 추가로 조건걸어서 본부장급이상이면 승인버튼 활성화 -->
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
