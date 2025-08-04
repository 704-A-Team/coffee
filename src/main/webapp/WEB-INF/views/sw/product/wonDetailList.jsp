<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }

        .card-product-info {
            background-color: #ffffff;
        }
        .card-product-header {
            background-color: #6ca0dc !important;
            color: white !important;
            font-weight: bold !important;
        }
        .product-name {
            font-weight: bold;
            color: black;
        }
        .text-black {
            color: black;
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
                <div class="form-section-title">제품(원재료) 상세보기</div>

                <div class="row">
                    <div class="col-md-10">
                        <div class="card shadow-sm border-0 card-product-info">
                            <div class="card-header card-product-header">
                                <h5 class="mb-0">${wonProductDetail.product_name} 제품 정보</h5>
                            </div>
                            <div class="card-body text-black">
                                <table class="table table-borderless mb-0">
                                    <tbody>
                                        <tr>
                                            <th style="width: 150px;" class="text-black">제품 코드</th>
                                            <td class="text-black">${wonProductDetail.product_code}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">제품명</th>
                                            <td class="product-name">${wonProductDetail.product_name}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">단위</th>
                                            <td class="text-black">${wonProductDetail.unitName}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">제품유형</th>
                                            <td class="text-black">${wonProductDetail.typeName}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">기본 중량 (g)</th>
                                            <td class="text-black">${wonProductDetail.product_weight}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">납품 여부</th>
                                            <td class="text-black">${wonProductDetail.isorderName}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">삭제 구분</th>
                                            <td class="text-black">${wonProductDetail.product_isdel}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">등록자</th>
                                            <td class="text-black">${wonProductDetail.regName}</td>
                                        </tr>
                                        <tr>
                                            <th class="text-black">등록일</th>
                                            <td class="text-black">${wonProductDetail.product_reg_date}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- 버튼 영역 -->
                        <div class="d-flex gap-3 mt-4 mb-5">
                            <a href="${pageContext.request.contextPath}/sw/wonProductList" class="btn btn-outline-primary">목록 보기</a>
                            <a href="${pageContext.request.contextPath}/sw/wonProductModifyForm?product_code=${wonProductDetail.product_code}" class="btn btn-primary">제품 수정</a>
                            <form action="${pageContext.request.contextPath}/sw/wonProductDelete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <input type="hidden" name="product_code" value="${wonProductDetail.product_code}">
                                <button type="submit" class="btn btn-danger">삭제</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
