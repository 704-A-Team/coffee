<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 등록</title>
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
                <div class="form-section-title">제품(원재료) 등록</div>
                <form action="${pageContext.request.contextPath}/sw/wonProductSave" method="post">
                    
                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" placeholder="제품명을 입력해주세요" required>
                    </div>

                    <!-- 단위 / 유형 / 납품 여부 -->
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label for="product_unit" class="form-label">단위</label>
                            <select id="product_unit" name="product_unit" class="form-select" required>
                                <option value="">-- 선택 --</option>
                                <option value="0">ea</option>
                                <option value="1">g</option>
                                <option value="2">ml</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="product_type" class="form-label">제품유형</label>
                            <select id="product_type" name="product_type" class="form-select" required>
                                <option value="">-- 선택 --</option>
                                <option value="0">원재료</option>
                                <option value="1">완제품</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="product_isorder" class="form-label">납품 여부</label>
                            <select id="product_isorder" name="product_isorder" class="form-select" required>
                                <option value="">-- 선택 --</option>
                                <option value="0">납품</option>
                                <option value="1">비납품</option>
                            </select>
                        </div>
                    </div>

                    <!-- 기본중량 -->
                    <div class="mb-3">
                        <label for="product_weight" class="form-label">기본중량 (g)</label>
                        <input type="number" class="form-control" id="product_weight" name="product_weight" placeholder="숫자만 입력해주세요" required>
                    </div>

                    <!-- 등록자 (required 없음) -->
                    <div class="mb-4">
                        <label for="product_reg_code" class="form-label">등록자 (사원코드)</label>
                        <input type="text" class="form-control" id="product_reg_code" name="product_reg_code" placeholder="사원코드를 입력해주세요">
                    </div>
                    
                    

                    <!-- 버튼 -->
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary btn-lg me-2">등록</button>
                        <button type="reset" class="btn btn-secondary btn-lg">취소</button>
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
