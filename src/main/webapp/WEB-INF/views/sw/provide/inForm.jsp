<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 공급 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Select2 CSS & JS -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        :root {
            --main-brown: #6f4e37;
            --soft-brown: #bfa08e;
            --dark-brown: #4e342e;
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
            color: var(--dark-brown);
        }

        .card-form {
            background-color: #fff;
            padding: 30px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
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

        /* Select2 드롭다운 높이 & hover 색상 커스터마이징 */
        .select2-results__options {
            max-height: 400px !important;
        }

        .select2-container--default .select2-results__option--highlighted[aria-selected] {
            background-color: var(--soft-brown) !important;
            color: white !important;
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
                <div class="form-section-title">원재료 공급 등록</div>

                <div class="card-form">
                    <form id="provideForm" action="${pageContext.request.contextPath}/provide/provideSave" method="post">

                        <!-- 제품 선택 -->
                        <div class="mb-3">
                            <label for="product_won_code" class="form-label">제품</label>
                            <select id="product_won_code" name="product_won_code" class="form-select" required style="width: 100%;">
                                <option value="">-- 제품을 선택하세요 --</option>
                                <c:forEach var="product" items="${productList}">
                                    <option value="${product.product_code}">${product.product_name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 거래처 선택 -->
                        <div class="mb-3">
                            <label for="provide_client_code" class="form-label">거래처</label>
                            <select id="provide_client_code" name="provide_client_code" class="form-select" required style="width: 100%;">
                                <option value="">-- 거래처를 선택하세요 --</option>
                                <c:forEach var="client" items="${clientList}">
                                    <option value="${client.client_code}">${client.client_name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 공급단위 -->
                        <div class="mb-3">
                            <label for="provide_amount" class="form-label">공급 단위(ea, g, ml)</label>
                            <input type="number" class="form-control" id="provide_amount" name="provide_amount" placeholder="숫자만 입력하세요" required>
                        </div>

                        <!-- 단가 -->
                        <div class="mb-4">
                            <label for="current_danga" class="form-label">단가 (₩)</label>
                            <input type="number" class="form-control" id="current_danga" name="current_danga" placeholder="단가를 입력하세요" required>
                        </div>
                    </form>
                </div>

                <!-- 버튼 -->
                <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                    <button type="submit" form="provideForm" class="btn btn-brown">등록</button>
                    <button type="reset" class="btn btn-brown-outline">초기화</button>
                    <a href="${pageContext.request.contextPath}/provide/provideList" class="btn btn-brown-outline">목록</a>
                </div>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<script>
$(document).ready(function() {
    // 제품 Select2 적용
    $('#product_won_code').select2({
        placeholder: '제품명을 선택하거나 검색하세요',
        minimumInputLength: 0,
        language: {
            noResults: function() {
                return "일치하는 제품이 없습니다";
            }
        }
    });

    // 거래처 Select2 적용
    $('#provide_client_code').select2({
        placeholder: '거래처명을 선택하거나 검색하세요',
        minimumInputLength: 0,
        language: {
            noResults: function() {
                return "일치하는 거래처가 없습니다";
            }
        }
    });

    // reset 버튼 클릭 시 Select2도 초기화
    $('button[type="reset"]').on('click', function() {
        $('#product_won_code').val('').trigger('change');
        $('#provide_client_code').val('').trigger('change');
    });
});
</script>

</body>
</html>