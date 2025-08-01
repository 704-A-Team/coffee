<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 수정</title>
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
                    <h2 class="text-center mb-4">제품 수정</h2>
                    <form action="${pageContext.request.contextPath}/sw/wonProductModify" method="post">
                        <table class="table table-bordered">
                            <tbody>
                                <tr>
                                    <th scope="row">제품코드</th>
                                    <td>
                                        <input type="text" name="product_code" class="form-control" value="${wonProductDetail.product_code}" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">제품명</th>
                                    <td>
                                        <input type="text" name="product_name" class="form-control" value="${wonProductDetail.product_name}">
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">단위</th>
                                    <td>
                                        <select name="product_unit" class="form-select">
										    <option value="0" <c:if test="${wonProductDetail.product_unit == 0}">selected</c:if>>ea</option>
										    <option value="1" <c:if test="${wonProductDetail.product_unit == 1}">selected</c:if>>g</option>
										    <option value="2" <c:if test="${wonProductDetail.product_unit == 2}">selected</c:if>>ml</option>
										</select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">제품유형</th>
                                    <td>
                                        <select name="product_type" class="form-select">
										    <option value="0" <c:if test="${wonProductDetail.product_type == 0}">selected</c:if>>원재료</option>
										    <option value="1" <c:if test="${wonProductDetail.product_type == 1}">selected</c:if>>완제품</option>
										</select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">기본 중량(g)</th>
                                    <td>
                                        <input type="number" name="product_weight" class="form-control" value="${wonProductDetail.product_weight}">
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">납품여부</th>
                                    <td>
                                        <select name="product_isorder" class="form-select">
										    <option value="0" <c:if test="${wonProductDetail.product_isorder == 0}">selected</c:if>>납품</option>
										    <option value="1" <c:if test="${wonProductDetail.product_isorder == 1}">selected</c:if>>비납품</option>
										</select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">삭제구분</th>
                                    <td>
                                        <select name="product_isdel" class="form-select">
										    <option value="0" <c:if test="${wonProductDetail.product_isdel == 0}">selected</c:if>>0</option>
										    <option value="1" <c:if test="${wonProductDetail.product_isdel == 1}">selected</c:if>>1</option>
										</select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="btn-wrapper">
                            <button type="submit" class="btn btn-primary me-3">저장</button>
                            <a href="${pageContext.request.contextPath}/sw/wonProductList" class="btn btn-secondary">취소</a>
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
