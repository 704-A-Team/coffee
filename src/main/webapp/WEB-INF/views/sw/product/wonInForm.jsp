<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 등록</title>
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
                <div class="d-flex justify-content-center mt-5"><h1>제품 등록</h1></div>
                <form action="${pageContext.request.contextPath}/sw/wonProductSave" method="post" class="container mt-4">

                    <!-- 제품명 -->
                    <div class="form-group row mb-3">
                        <label for="product_name" class="col-sm-2 col-form-label">제품명</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="product_name" name="product_name" placeholder="제품명을 입력해주세요" required>
                        </div>
                    </div>

                    <!-- 단위 -->
                    <div class="form-group row mb-3">
                        <label for="product_unit" class="col-sm-2 col-form-label">단위</label>
                        <div class="col-sm-6">
                            <select id="product_unit" name="product_unit" class="form-select">
                                <option value="0">ea</option>
                                <option value="1">g</option>
                                <option value="2">ml</option>
                            </select>
                        </div>
                    </div>

                    <!-- 제품유형 -->
                    <div class="form-group row mb-3">
                        <label for="product_type" class="col-sm-2 col-form-label">제품유형</label>
                        <div class="col-sm-6">
                            <select id="product_type" name="product_type" class="form-select">
                                <option value="0">원재료</option>
                                <option value="1">완제품</option>
                            </select>
                        </div>
                    </div>

                    <!-- 기본 중량 -->
                    <div class="form-group row mb-3">
                        <label for="product_weight" class="col-sm-2 col-form-label">기본중량(g)</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="product_weight" name="product_weight" placeholder="기본중량(g)을 입력해주세요" required>
                        </div>
                    </div>

                    <!-- 납품 여부 -->
                    <div class="form-group row mb-3">
                        <label for="product_isorder" class="col-sm-2 col-form-label">제품 납품 여부</label>
                        <div class="col-sm-6">
                            <select id="product_isorder" name="product_isorder" class="form-select">
                                <option value="0">납품</option>
                                <option value="1">비납품</option>
                            </select>
                        </div>
                    </div>

                    <!-- 등록자 -->
                    <div class="form-group row mb-4">
                        <label for="product_reg_code" class="col-sm-2 col-form-label">등록자</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="product_reg_code" name="product_reg_code" placeholder="사원코드(ID)를 입력해주세요" required>
                        </div>
                    </div>

                    <!-- 버튼 -->
                    <div class="form-group row">
                        <div class="col-sm-8 text-center">
                            <button type="submit" class="btn btn-primary btn-lg me-2">등록</button>
                            <button type="reset" class="btn btn-secondary btn-lg">취소</button>
                        </div>
                    </div>
                </form>
            </main>

            <!-- FOOTER -->
            <%@ include file="../../footer.jsp" %>
        </div>
    </div>

</body>
</html>
