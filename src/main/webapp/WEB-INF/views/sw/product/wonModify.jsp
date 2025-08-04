<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
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
                <div class="form-section-title">제품(원재료) 수정</div>

                <form action="${pageContext.request.contextPath}/sw/wonProductModify" method="post">
                    <input type="hidden" name="product_code" value="${wonProductDetail.product_code}" />

                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name"
                               value="${wonProductDetail.product_name}" required>
                    </div>

                    <!-- 단위 / 유형 / 납품 여부 -->
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">단위</label>
                            <select name="product_unit" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_unit == 0}">selected</c:if>>ea</option>
                                <option value="1" <c:if test="${wonProductDetail.product_unit == 1}">selected</c:if>>g</option>
                                <option value="2" <c:if test="${wonProductDetail.product_unit == 2}">selected</c:if>>ml</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">제품유형</label>
                            <select name="product_type" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_type == 0}">selected</c:if>>원재료</option>
                                <option value="1" <c:if test="${wonProductDetail.product_type == 1}">selected</c:if>>완제품</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">납품 여부</label>
                            <select name="product_isorder" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_isorder == 0}">selected</c:if>>납품</option>
                                <option value="1" <c:if test="${wonProductDetail.product_isorder == 1}">selected</c:if>>비납품</option>
                            </select>
                        </div>
                    </div>

                    <!-- 기본 중량 -->
                    <div class="mb-3">
                        <label class="form-label">기본 중량 (g)</label>
                        <input type="number" class="form-control" name="product_weight"
                               value="${wonProductDetail.product_weight}" required>
                    </div>

                    <!-- 삭제 구분 -->
                    <div class="mb-4">
                        <label class="form-label">삭제 구분</label>
                        <select name="product_isdel" class="form-select">
                            <option value="0" <c:if test="${wonProductDetail.product_isdel == 0}">selected</c:if>>0</option>
                            <option value="1" <c:if test="${wonProductDetail.product_isdel == 1}">selected</c:if>>1</option>
                        </select>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/sw/wonProductDetail?product_code=${wonProductDetail.product_code}"
                           class="btn btn-secondary">취소</a>
                    </div>
                </form>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
