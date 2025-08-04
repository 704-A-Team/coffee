<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 공급 등록</title>
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

<!-- HEADER -->
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <!-- SIDEBAR -->
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <!-- 본문 -->
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">원재료 공급 등록</div>

                <form action="${pageContext.request.contextPath}/provide/provideSave" method="post">
                    <!-- 제품 선택 -->
                    <div class="mb-3">
                        <label for="product_won_code" class="form-label">제품</label>
                        <select id="product_won_code" name="product_won_code" class="form-select" required>
                            <option value="">-- 제품을 선택하세요 --</option>
                            <c:forEach var="product" items="${productList}">
                                <option value="${product.product_code}">${product.product_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 거래처 선택 -->
                    <div class="mb-3">
                        <label for="provide_client_code" class="form-label">거래처</label>
                        <select id="provide_client_code" name="provide_client_code" class="form-select" required>
                            <option value="">-- 거래처를 선택하세요 --</option>
                            <c:forEach var="client" items="${clientList}">
                                <option value="${client.client_code}">${client.client_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 공급단위 -->
                    <div class="mb-3">
                        <label for="provide_amount" class="form-label">공급 단위</label>
                        <input type="number" class="form-control" id="provide_amount" name="provide_amount"
                               placeholder="공급 단위를 입력하세요" required>
                    </div>

                    <!-- 단가 -->
                    <div class="mb-4">
                        <label for="current_danga" class="form-label">단가 (₩)</label>
                        <input type="number" class="form-control" id="current_danga" name="current_danga"
                               placeholder="단가를 입력하세요" required>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">등록</button>
                        <button type="reset" class="btn btn-secondary">취소</button>
                    </div>
                </form>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../../footer.jsp" %>
    </div>
</div>

</body>
</html>
