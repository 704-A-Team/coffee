<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 수정</title>
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
                <div class="form-section-title">원재료 수정</div>

                <form action="${pageContext.request.contextPath}/provide/provideModify" method="post">
                    <input type="hidden" name="provide_code" value="${provideDetail.provide_code}">

                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_won_code" class="form-label">제품명</label>
                        <select name="product_won_code" id="product_won_code" class="form-select" required>
                            <c:forEach var="product" items="${productList}">
                                <option value="${product.product_code}"
                                    <c:if test="${product.product_code == provideDetail.product_won_code}">selected</c:if>>
                                    ${product.product_name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 거래처명 -->
                    <div class="mb-3">
                        <label for="provide_client_code" class="form-label">거래처명</label>
                        <select name="provide_client_code" id="provide_client_code" class="form-select" required>
                            <c:forEach var="client" items="${clientList}">
                                <option value="${client.client_code}"
                                    <c:if test="${client.client_code == provideDetail.provide_client_code}">selected</c:if>>
                                    ${client.client_name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 공급단위 -->
                    <div class="mb-3">
                        <label for="provide_amount" class="form-label">공급단위</label>
                        <input type="number" class="form-control" id="provide_amount" name="provide_amount"
                               value="${provideDetail.provide_amount}" required>
                    </div>

                    <!-- 단가 -->
                    <div class="mb-3">
                        <label for="current_danga" class="form-label">단가 (₩)</label>
                        <input type="number" class="form-control" id="current_danga" name="current_danga"
                               value="${provideDetail.current_danga}" required>
                    </div>

                    <!-- 삭제구분 -->
                    <div class="mb-4">
                        <label for="provide_isdel" class="form-label">삭제 구분</label>
                        <select name="provide_isdel" id="provide_isdel" class="form-select">
                            <option value="0" <c:if test="${provideDetail.provide_isdel == 0}">selected</c:if>>0</option>
                            <option value="1" <c:if test="${provideDetail.provide_isdel == 1}">selected</c:if>>1</option>
                        </select>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-3">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/provide/provideList" class="btn btn-secondary">취소</a>
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
