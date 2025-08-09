<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 등록</title>
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
                <div class="form-section-title">발주 등록</div>

                <form action="${pageContext.request.contextPath}/sw/purchaseSave" method="post">
                    <!-- 제품 선택 -->
                    <div class="mb-3">
                        <label for="product_won_code" class="form-label">제품</label>
                        <select id="product_won_code" name="product_won_code" class="form-select" required>
                            <option value="">-- 제품을 선택하세요 --</option>
                            <c:forEach var="product" items="${productIsList}">
                                <option value="${product.product_code}">${product.product_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 거래처 선택 -->
                    <div class="mb-3">
                        <label for="purchase_client_code" class="form-label">거래처명</label>
                        <select id="purchase_client_code" name="purchase_client_code" class="form-select" required>
                            <option value="">-- 거래처를 선택하세요 --</option>
                        </select>
                    </div>

                    <!-- 수량 입력 -->
                    <div class="mb-3">
                        <label for="purchase_amount" class="form-label">발주 수량 (ea, g, ml)</label>
                        <select id="purchase_amount" name="purchase_amount" class="form-select" required>
                            <option value="">-- 선택 --</option>
                        </select>
                    </div>

                    <input type="hidden" id="purchase_danga" name="purchase_danga" />

                    <!-- 총 금액 -->
                    <div class="mb-4">
                        <label for="total_price" class="form-label">총 금액 (₩)</label>
                        <input type="text" class="form-control" id="total_price" readonly>
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
        console.log("product_won_code 존재 여부:", $('#product_won_code').length);

        $('#product_won_code').select2({
            placeholder: '제품명을 선택하거나 검색하세요',
            minimumInputLength: 0,
            width: '100%',
            language: {
                noResults: function () {
                    return "일치하는 제품이 없습니다";
                }
            }
        });

        $('#product_won_code').on('select2:select', function () {
            var ajax_product_code = $(this).val();
            var $ajax_clientSelect = $('#purchase_client_code');

            if (!ajax_product_code) {
                $ajax_clientSelect.html('<option value="">-- 거래처를 선택하세요 --</option>');
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/sw/getClientsByProduct',
                type: 'GET',
                data: { product_code: ajax_product_code },
                success: function (clients) {
                    $ajax_clientSelect.empty().append('<option value="">-- 거래처를 선택하세요 --</option>');
                    if (!clients || clients.length === 0) {
                        alert('해당 제품의 원재료를 공급하는 거래처가 없습니다. 원재료 공급 등록부터 해주세요');
                        return;
                    }
                    $.each(clients, function (i, client) {
                        $ajax_clientSelect.append('<option value="' + client.client_code + '">' + client.client_name + '</option>');
                    });
                }
            });
        });

        $('#purchase_client_code').change(function () {
            const ajax_client_code = $(this).val();
            const ajax_product_code = $('#product_won_code').val();

            if (!ajax_client_code || !ajax_product_code) return;

            $.ajax({
                url: '${pageContext.request.contextPath}/sw/getProvideInfo',
                type: 'GET',
                data: {
                    product_won_code: ajax_product_code,
                    provide_client_code: ajax_client_code
                },
                success: function (data) {
                    const ajax_purchase_amount = data.provide_amount;
                    const ajax_purchase_danga = data.current_danga;

                    $('#purchase_danga').val(ajax_purchase_danga);

                    const $select = $('#purchase_amount');
                    $select.empty().append('<option value="">-- 선택 --</option>');
                    for (let i = 1; i <= 50; i++) {
                        const value = ajax_purchase_amount * i * 10;
                        $select.append('<option value="' + value + '">' + numberWithCommas(value) + '</option>');
                    }
                }
            });
        });

        $('#purchase_amount').change(function () {
            const ajax_amount = Number($('#purchase_amount option:eq(1)').val());
            const ajax_i = 10 * Number($(this).val());
            const ajax_danga = Number($('#purchase_danga').val());
            if (!ajax_i || !ajax_danga) {
                $('#total_price').val('');
                return;
            }
            const total = ajax_i * ajax_danga / ajax_amount;
            $('#total_price').val(numberWithCommas(total));
        });
    });
</script>

</body>
</html>
