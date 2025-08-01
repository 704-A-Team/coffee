<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 수정</title>
    <style>
        table th {
            width: 150px;
            white-space: nowrap;
        }
        table {
            width: 70%;
            margin: 0 auto;
        }
        .btn-wrapper {
            width: 70%;
            margin: 30px auto 0 auto;
            text-align: center;
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
            <div class="container mt-4">
                <h2 class="text-center mb-4">원재료 수정</h2>
                <form action="${pageContext.request.contextPath}/provide/provideModify" method="post">
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th scope="row">원재료 코드</th>
                                <td>
                                    <input type="text" name="provide_code" class="form-control"
                                           value="${provideDetail.provide_code}" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">제품명</th>
                                <td>
                                    <select name="product_won_code" class="form-select" required>
                                        <c:forEach var="product" items="${productList}">
                                            <option value="${product.product_code}"
                                                <c:if test="${product.product_code == provideDetail.product_won_code}">selected</c:if>>
                                                ${product.product_name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">거래처명</th>
                                <td>
                                    <select name="provide_client_code" class="form-select" required>
                                        <c:forEach var="client" items="${clientList}">
                                            <option value="${client.client_code}"
                                                <c:if test="${client.client_code == provideDetail.provide_client_code}">selected</c:if>>
                                                ${client.client_name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">공급단위</th>
                                <td>
                                    <input type="number" name="provide_amount" class="form-control"
                                           value="${provideDetail.provide_amount}" required>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">단가</th>
                                <td>
                                    <input type="number" name="current_danga" class="form-control"
                                           value="${provideDetail.current_danga}" required>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">삭제구분</th>
                                <td>
                                    <select name="provide_isdel" class="form-select">
                                        <option value="0" <c:if test="${provideDetail.provide_isdel == 0}">selected</c:if>>0</option>
                                        <option value="1" <c:if test="${provideDetail.provide_isdel == 1}">selected</c:if>>1</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="btn-wrapper">
                        <button type="submit" class="btn btn-primary me-3">수정 완료</button>
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
