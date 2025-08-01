<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주 등록</title>
</head>
<body class="d-flex flex-column min-vh-100">

	<!-- HEADER -->
	<%@ include file="../../header.jsp"%>

	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="../../sidebar.jsp"%>

		<div class="d-flex flex-column flex-grow-1">
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
				<div class="d-flex justify-content-center mt-5">
					<h1>발주 등록</h1>
				</div>
				<form action="${pageContext.request.contextPath}/sw/purchaseSave" method="post" class="container mt-4">
					
					<!-- 제품 선택 -->
					<div class="form-group row mb-3">
						<label for="product_won_code" class="col-sm-2 col-form-label">제품</label>
						<div class="col-sm-6">
							<select id="product_won_code" name="product_won_code"
								class="form-select" required>
								<option value="">-- 제품을 선택하세요 --</option>
								<c:forEach var="product" items="${productIsList}">
									<option value="${product.product_code}">${product.product_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<!-- 거래처 선택 -->
					<div class="form-group row mb-3">
						<label for="purchase_client_code" class="col-sm-2 col-form-label">거래처명</label>
						<div class="col-sm-6">
							<select id="purchase_client_code" name="purchase_client_code" class="form-select" required>
								<option value="">-- 거래처를 선택하세요 --</option>
								<!-- ajax -->
							</select>
						</div>
					</div>

					<!-- 수량 입력 -->
					<div class="form-group row mb-4">
					  <label for="provide_amount_input" class="col-sm-2 col-form-label">발주 수량(g, ml)</label>
					  <div class="col-sm-6">
					    <select id="provide_amount_input" name="provide_amount_input" class="form-select" required>
					      <option value="">-- 선택 --</option>
					      <!-- ajax -->
					    </select>
					  </div>
					</div>
					
					<!-- 단가 -->
					<input type="hidden" id="current_danga" name="current_danga" />

					<!-- 총 가격 -->
					<div class="form-group row mb-3">
					  <label for="total_price" class="col-sm-2 col-form-label">총 금액(₩)</label>
					  <div class="col-sm-6">
					    <input type="text" class="form-control" id="total_price" readonly>
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
			<%@ include file="../../footer.jsp"%>
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
        							},
      });
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
	      const ajax_provide_amount = data.provide_amount; 
	      const ajax_current_danga = data.current_danga;

	      $('#current_danga').val(ajax_current_danga);

	      const $select = $('#provide_amount_input');
	      $select.empty().append('<option value="">-- 선택 --</option>');
	      for (let i = 1; i <= 50; i++) {
	    	  const value = ajax_provide_amount * i;
	    	  $select.append('<option value="' + value + '">' + numberWithCommas(value) + '</option>');
	    	}
	    }
	  });
	});

  $('#provide_amount_input').change(function() {
	  const ajax_amount = Number($('#provide_amount_input option:eq(1)').val());
	  const ajax_i = Number($(this).val());
	  const ajax_danga = Number($('#current_danga').val());
	  if (!ajax_i || !ajax_danga) {
	    $('#total_price').val('');
	    return;
	  }
	  const total = ajax_i * ajax_danga / ajax_amount;
	  $('#total_price').val(numberWithCommas(total));
	});

  
  
</script>

</body>
</html>
