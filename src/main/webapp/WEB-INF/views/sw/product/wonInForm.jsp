<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --main-brown: #6f4e37;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
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
            color: var(--main-brown);
        }

        .btn-brown {
            background-color: var(--soft-brown) !important;
            color: white !important;
            border: none !important;
        }

        .btn-brown:hover {
            background-color: var(--main-brown) !important;
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

        @media (max-width: 768px) {
            .d-flex.justify-content-end {
                justify-content: center !important;
            }
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
                <div class="form-section-title">제품(원재료) 등록</div>

                <form action="${pageContext.request.contextPath}/sw/wonProductSave" method="post" enctype="multipart/form-data">
                    
                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" placeholder="제품명을 입력해주세요" required>
                    </div>

                    <!-- 단위 / 납품 여부 -->
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

					<!-- 판매단위(콤보박스) -->
                    <div class="mb-3">
                        <label for="product_order_pack" class="form-label">판매단위</label>
                        <select class="form-select" id="product_order_pack" name="product_order_pack" required>
                            <option value="">-- 단위를 먼저 선택하세요 --</option>
                        </select>
                    </div>

                    <!-- 이미지 -->
                    <div class="mb-3">
                        <label for="files" class="form-label">제품(원재료) 이미지 (최대 3개)</label>
                        <input type="file" class="form-control" id="files" name="files" multiple accept=".jpg,.jpeg,.png">
                        <div class="form-text text-danger mt-1">※ 최대 3개의 이미지만 업로드할 수 있습니다.</div>
                    </div>

                    <!-- 버튼 영역: 오른쪽 하단 정렬 -->
                    <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                        <button type="submit" class="btn btn-brown">등록</button>
                        <button type="reset" class="btn btn-brown-outline">초기화</button>
                    </div>
                </form>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<!-- 단위 선택 시 판매단위 옵션 변경 -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const unitSelect = document.getElementById("product_unit");
        const orderPackSelect = document.getElementById("product_order_pack");

        unitSelect.addEventListener("change", function () {
            const selected = this.value;
            orderPackSelect.innerHTML = ''; // 기존 옵션 제거

            const defaultOption = document.createElement("option");
            defaultOption.value = '';
            defaultOption.textContent = '-- 선택 --';
            orderPackSelect.appendChild(defaultOption);

            if (selected === "0") { // ea
                for (let i = 10; i <= 100; i += 10) {
                    const opt = document.createElement("option");
                    opt.value = i;
                    opt.textContent = i + ' ea';
                    orderPackSelect.appendChild(opt);
                }
            } else if (selected === "1" || selected === "2") { // g or ml
                for (let i = 100; i <= 1000; i += 100) {
                    const opt = document.createElement("option");
                    opt.value = i;
                    opt.textContent = i + (selected === "1" ? ' g' : ' ml');
                    orderPackSelect.appendChild(opt);
                }
            }
        });
    });
</script>

</body>
</html>
