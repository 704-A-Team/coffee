<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 등록</title>
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
                        <label for="purchase_amount" class="form-label">발주 수량 (g, ml)</label>
                        <select id="purchase_amount" name="purchase_amount" class="form-select" required>
                            <option value="">-- 선택 --</option>
                        </select>
                    </div>

                    <!-- 단가 (숨김) -->
                    <input type="hidden" id="purchase_danga" name="purchase_danga" />

                    <!-- 총 금액 -->
                    <div class="mb-4">
                        <label for="total_price" class="form-label">총 금액 (₩)</label>
                        <input type="text" class="form-control" id="total_price" readonly>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-3">
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function numberWithCommas(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    $(document).ready(function () {
        $('#product_won_code').change(function () {
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
                        alert('해당 제품의 원재료를 공급하는 거래처가 없습니다. 원재료 등록부터 해주세요');
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
                        const value = ajax_purchase_amount * i;
                        $select.append('<option value="' + value + '">' + numberWithCommas(value) + '</option>');
                    }
                }
            });
        });

        $('#purchase_amount').change(function () {
            const ajax_amount = Number($('#purchase_amount option:eq(1)').val());
            const ajax_i = Number($(this).val());
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
