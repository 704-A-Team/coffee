<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>발주 등록</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Select2 -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
        }
        body { background-color: #f9f5f1; }
        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 16px;	
            font-weight: 700;
            font-size: 1.6rem;
            color: var(--dark-brown);
        }
        .card-form {
            background: #fff;
            padding: 16px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,.05);
            height: 100%;
        }
        .btn-brown {
            background-color: #fff !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
            transition: .2s;
        }
        .btn-brown:hover { background-color: var(--main-brown) !important; color: #fff !important; }
        .btn-brown-outline {
            background-color: transparent !important;
            color: var(--main-brown) !important;
            border: 1px solid var(--main-brown) !important;
            transition: .2s;
        }
        .btn-brown-outline:hover { background-color: var(--main-brown) !important; color: #fff !important; }
        .btn-secondary-custom { background-color: #eee !important; color: #333 !important; border: 1px solid #ccc !important; }
        .btn-secondary-custom:hover { background-color: #ccc !important; }

        /* Select2 드롭다운 */
        .select2-results__options { max-height: 400px !important; }
        .select2-container--default .select2-results__option--highlighted[aria-selected] {
            background-color: var(--soft-brown) !important; color: #fff !important;
        }

        /* 상단 35% / 하단 65% */
        .pane-top { height: 35vh; overflow: auto; }
        .pane-bottom { height: 65vh; overflow: auto; }
        
        #provider-list {
		    max-height: 170px;   /* 원하는 높이로 조절 */
		    overflow-y: auto;
		}
		.pane-bottom { height: auto; overflow: visible; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-3">
            <div class="container-fluid">

                <form action="${pageContext.request.contextPath}/sw/purchaseSave" method="post" id="purchaseForm">

                    <!-- 상단: 35% -->
                    <div class="pane-top mb-2">
                        <div class="card-form">
                            <div class="form-section-title">제품 검색</div>
                            <select id="product_code" name="product_code" class="form-select" required>
                                <option value="">-- 제품을 선택하세요 --</option>
                                <c:forEach var="product" items="${productIsList}">
                                    <option value="${product.product_code}">${product.product_name}</option>
                                </c:forEach>
                            </select>
                            <div class="mt-2">
                                <div class="small text-muted">이 제품을 공급하는 거래처</div>
                                <ul id="provider-list" class="list-group mt-1" style="max-height:170px; overflow-y:auto;"></ul>
                            </div>
                        </div>
                    </div>

                    <!-- 하단: 65% -->
                    <div class="pane-bottom">
                        <!-- 발주 등록 카드폼 -->
						<div class="card-form d-flex flex-column">
						    <div class="form-section-title">발주 등록</div>
						
						    <!-- 발주 행들이 들어갈 영역 -->
						    <div id="purchase-rows">
						        <div class="row g-3 align-items-end mb-2 purchase-row">
						            <!-- 거래처 선택 -->
						            <div class="col-md-3">
						                <label class="form-label">거래처</label>
						                <select name="purchase_client_code[]" class="form-select" required>
						                    <option value="">-- 선택 --</option>
						                    <c:forEach var="client" items="${clientList}">
						                        <option value="${client.client_code}">${client.client_name}</option>
						                    </c:forEach>
						                </select>
						            </div>
						
						            <!-- 제품 선택 -->
						            <div class="col-md-3">
						                <label class="form-label">제품</label>
						                <select name="product_code[]" class="form-select" required>
						                    <option value="">-- 선택 --</option>
						                    <c:forEach var="product" items="${productIsList}">
						                        <option value="${product.product_code}">${product.product_name}</option>
						                    </c:forEach>
						                </select>
						            </div>
						
						            <!-- 수량 -->
						            <div class="col-md-2">
						                <label class="form-label">수량</label>
						                <select name="purchase_amount[]" class="form-select" required>
						                    <option value="">-- 선택 --</option>
						                </select>
						            </div>
						
						            <!-- 총 금액 -->
						            <div class="col-md-2">
						                <label class="form-label">총 금액 (₩)</label>
						                <input type="text" class="form-control total_price" readonly>
						                <input type="hidden" name="purchase_danga[]" class="purchase_danga" />
						            </div>
						
						            <!-- 삭제 버튼 -->
						            <div class="col-md-2 d-flex gap-2">
						                <button type="button" class="btn btn-danger btn-remove">삭제</button>
						            </div>
						        </div>
						    </div>
						
						    <!-- 카드폼 안 왼쪽 하단 +추가 버튼 -->
						    <div class="mt-2">
						        <button type="button" id="btn-add-row" class="btn btn-success">+ 추가</button>
						    </div>
						</div>
						
						<!-- 카드폼 밖 버튼 -->
						<div class="d-flex justify-content-end gap-2 mt-3">
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
$(function () {
	
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
	
	$('#product_code').change(function () {
	    var ajax_product_code = $(this).val();
	    $('#provider-list').empty(); // 기존 목록 비우기

	    if (!ajax_product_code) return; // 제품 선택 안 했으면 종료

	    $.ajax({
	        url: '${pageContext.request.contextPath}/sw/getClientsByProduct',
	        type: 'GET',
	        data: { product_code: ajax_product_code },
	        dataType: 'json',
	        success: function (list) {
	            if (!list || list.length === 0) {
	                $('#provider-list').append('<li class="list-group-item text-muted">거래처가 없습니다</li>');
	                return;
	            }

	            $.each(list, function (i, client) {
	                $('#provider-list').append(
	                    '<li class="list-group-item">' + client.client_name + '</li>'
	                );
	            });
	        },
	        error: function (xhr) {
	            console.error(xhr.responseText);
	            $('#provider-list').append('<li class="list-group-item text-danger">조회 실패</li>');
	        }
	    });
	});
	
	$('#btn-add-row').click(function () {
	    const $first = $('.purchase-row:first');
	    const $newRow = $first.clone();

	    // 새 줄에서는 라벨 제거
	    $newRow.find('label').remove();

	    // 값 초기화
	    $newRow.find('select').val('');
	    $newRow.find('input').val('');

	    $('#purchase-rows').append($newRow);
	});


    // 삭제 버튼 클릭
    $(document).on('click', '.btn-remove', function () {
        if ($('.purchase-row').length > 1) {
            $(this).closest('.purchase-row').remove();
        } else {
            alert('최소 1개 행은 있어야 합니다.');
        }
    });


    // 거래처 선택 시 공급 정보 조회
    $('#purchase_client_code').change(function () {
        // TODO: 기능 구현 시 AJAX 호출
    });

    // 수량 변경 시 총액 계산
    $('#purchase_amount').change(function () {
        // TODO: 계산 로직 구현
    });
});
</script>

</body>
</html>
