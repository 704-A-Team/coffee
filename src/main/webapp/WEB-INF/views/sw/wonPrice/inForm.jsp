<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>가격(원재료) 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery 먼저 로드 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Select2 CSS/JS (jQuery 다음에 위치해야 함) -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
        }

        body {
            background-color: #f9f5f1;
        }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }

        .btn-brown {
            background-color: white !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
            transition: 0.2s;
        }

        .btn-brown:hover {
            background-color: var(--main-brown) !important;
            color: white !important;
        }

        .btn-brown-outline {
            background-color: transparent !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
            transition: 0.2s;
        }

        .btn-brown-outline:hover {
            background-color: var(--main-brown) !important;
            color: white !important;
        }

        .btn-secondary-custom {
            background-color: #eee !important;
            color: #333 !important;
            border: 1px solid #ccc !important;
        }

        .btn-secondary-custom:hover {
            background-color: #ccc !important;
        }

        .select2-results__options {
            max-height: 400px !important;
        }

        .select2-container--default .select2-results__option--highlighted[aria-selected] {
            background-color: var(--soft-brown) !important;
            color: white !important;
        }

        .input-price-warning {
            border: 2px solid red !important;
            background-color: #ffe5e5 !important;
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
                <div class="form-section-title">가격(원재료) 등록</div>

                <form action="${pageContext.request.contextPath}/sw/wonProductPriceSave" method="post">
                    <!-- 제품 선택 -->
                    <div class="mb-3">
                        <label for="product_code" class="form-label">제품</label>
                        <select id="product_code" name="product_code" class="form-select" required>
                            <option value="">-- 제품을 선택하세요 --</option>
                            <c:forEach var="product" items="${wonProductAllList}">
                                <option value="${product.product_code}">${product.product_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 가격 입력 -->
                    <div class="mb-3">
                        <label for="price" class="form-label">가격(1ea, 1g, 1ml 기준)</label>
                        <input type="number" id="price" name="price" class="form-control" required step="0.001" min="0">
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                        <button type="submit" class="btn btn-brown">등록</button>
                        <button type="reset" class="btn btn-secondary-custom">초기화</button>
                        <a href="${pageContext.request.contextPath}/sw/purchaseList" class="btn btn-brown-outline">목록</a>
                    </div>
                </form>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<script>
    function numberWithCommas(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    $(document).ready(function () {
        $('#product_code').select2({
            placeholder: '제품명을 선택하거나 검색하세요',
            minimumInputLength: 0,
            width: '100%',
            language: {
                noResults: function () {
                    return "일치하는 제품이 없습니다";
                }
            }
        });

        $('#product_code').on('change', function () {
            const ajax_product_code = $(this).val();

            if (!ajax_product_code) {
                $('#price').val('');
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/sw/getProvideByProduct',
                type: 'GET',
                data: { product_won_code: ajax_product_code },
                cache: false,
                success: function (ajax_provideList) {
                    if (!Array.isArray(ajax_provideList) || ajax_provideList.length === 0) {
                        $('#price').val('');
                        return;
                    }

                    const ajax_unitPrices = ajax_provideList.map(ajax_p => {
                        const ajax_danga = parseFloat(ajax_p.current_danga);
                        const ajax_amount = parseFloat(ajax_p.provide_amount);

                        if (isNaN(ajax_danga) || isNaN(ajax_amount) || ajax_amount <= 0) {
                            return 0;
                        }

                        return ajax_danga / ajax_amount;
                    });

                    const ajax_maxUnitPrice = Math.max(...ajax_unitPrices);

                    if (isNaN(ajax_maxUnitPrice) || ajax_maxUnitPrice <= 0) {
                        $('#price').val('');
                        return;
                    }

                    const ajax_increased = ajax_maxUnitPrice * 1.1;
                    const ajax_rounded = Math.round(ajax_increased * 1000) / 1000;
                    const ajax_displayValue = ajax_rounded.toFixed(2);

                    $('#price').val(ajax_displayValue).data('original-price', ajax_rounded);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX 실패:", status, error);
                    alert("공급 단가 조회 실패");
                }
            });

            $('#price').on('input', function () {
                const currentVal = parseFloat($(this).val());
                const originalVal = parseFloat($(this).data('original-price'));

                if (!isNaN(originalVal) && currentVal < originalVal) {
                    $(this).addClass('input-price-warning');
                } else {
                    $(this).removeClass('input-price-warning');
                }
            });
        });

        const errorMsg = '${errorMsg}';
        if (errorMsg && errorMsg !== 'null' && errorMsg.trim() !== '') {
            alert(errorMsg);
        }
    });
</script>

</body>
</html>
