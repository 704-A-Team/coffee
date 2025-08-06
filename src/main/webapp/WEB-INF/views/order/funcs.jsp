<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- jQuery & Select2 CDN -->
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<script>
	
	function getDetails() {
		return [
			<c:forEach items="${order.orders_details }" var="detail">
				{
					product_code: "${detail.product_code}",
					order_amount: "${detail.order_amount}",
					order_ddate: "${detail.order_ddate}", // yyyy-MM-dd
					
					product_name: "${detail.product_name} (${detail.product_code})",
					product_price: "${detail.price}",
					
					can_order: ${detail.can_order},
			    },
			</c:forEach>
		];
	}
	
    const isOrderFixedPage = ${isFixedPage};
    if (!isOrderFixedPage) {
    	const details = getDetails();
    	$(function () {
    		// 새 발주서: 빈 제품 행 추가 / 조회: 기존 제품 행 추가
    		if (details.length === 0) {
    		    addItem();
    		} else {
    			details.forEach(detail => addItem(detail));
    		}
    		
    		// 선택한 제품에 따라 price 자동 추가
    		$('#item-list').on("change", '.prd-code', function () {
    			const $row = $(this).closest('.row');
    		    const price = $(this).find("option:selected").attr("price");
    		    $row.find(".prd-price").text(price);
    	    });
    		
    		// row 별 총액 계산
    	    $('#item-list').on("change", '.prd-count', function () {
    	    	const $row = $(this).closest('.row');
    	    	const count = $(this).val();
    	    	const price = $row.find(".prd-price").text() ?? 0;
    	    	$row.find(".prd-total-price").text(count * price);
    	    });
    		
    	    $('#item-list').on("change", '.prd-code', function () {
    	    	const $row = $(this).closest('.row');
    	    	const price = $(this).find("option:selected").attr("price");
    	    	const count = $row.find(".prd-count").val() ?? 0;
    	    	$row.find(".prd-total-price").text(count * price);
    	    });
    	    
    	    // 전체 총액 계산
    	    $('#item-list').on("change", ".prd-code, .prd-count", function () {
    	    	let total = 0;
    	    	$('.prd-total-price').each(function () {
    	    		total += parseFloat($(this).text()) || 0;
    	    	});
    	    	$('.total-price').text(total);
    	    });
    	    
    	    // row 삭제 + 전체 총액 계산
    	    $('#item-list').on("click", '.prd-del-btn', function () {
    	    	this.closest('.row').remove();
    	    	
    	    	let total = 0;
    	    	$('.prd-total-price').each(function () {
    	    		total += parseFloat($(this).text()) || 0;
    	    	});
    	    	$('.total-price').text(total);
    	    });
    	});	
    }

	// 제품 목록 추가
	function addItem(detail = null) {
	    const container = document.getElementById("item-list");
	    const row = document.createElement("div");
	    row.className = "row g-2 mb-2 item-list-item";
	    
	    row.innerHTML = `
	      <div class="col-3">
	    	<select class="form-select prd-code" required>
	    		<option value=""></option>
	    			<c:forEach items="${products }" var="prd">
    			<option value="${prd.product_code}" price="${prd.price}">${prd.product_name} (${prd.product_code})</option>
			</c:forEach>
		    </select>
	      </div>
	      <div class="col-2">
	        <div class="form-control form-control-sm bg-light prd-price" placeholder="단가">0</div>
	      </div>
	      <div class="col-2">
	        <input type="number" min="1" class="form-control form-control-sm prd-count" required placeholder="수량">
	      </div>
	      <div class="col-2">
	        <div class="form-control form-control-sm bg-light prd-total-price" placeholder="총액">0</div>
	      </div>
	      <div class="col-2">
	        <input type="date" class="form-control form-control-sm prd-ddate" required placeholder="납기일">
	      </div>
	      
	      <div class="col-1 text-end pe-0">
	        <button type="button" class="btn btn-outline-danger btn-sm fw-bold prd-del-btn">삭제</button>
	      </div>
		`;
	    container.appendChild(row);
	 	
		const nameInput = row.querySelector('.prd-code');
        const countInput = row.querySelector('.prd-count');
        const priceInput = row.querySelector('.prd-price');
        const rowPriceInput = row.querySelector('.prd-total-price');
        const dateInput = row.querySelector('.prd-ddate');

	    // 기존 orders_detail 정보 작성
	    if (detail !== null) {
 	        if (nameInput) {
 	        	// 현재 가능한 products 목록(select태그의 value들)에 없으면
 	        	if (!detail.can_order) {
				// if (!$(nameInput).find('option[value="' + detail.product_code + '"]').length) {
	 	        	const deletedOption = new Option('[❌불가] ' + detail.product_name, 0, true, true);
	  				$(deletedOption).prop("disabled", true);
	  				nameInput.append(deletedOption)
				} else $(nameInput).val(detail.product_code);
	        }
	        if (countInput) $(countInput).val(detail.order_amount ?? 0);
	        if (priceInput) $(priceInput).text(detail.product_price ?? 0);
	        const rowPrice = detail.order_amount * detail.product_price;
	        if (rowPriceInput) $(rowPriceInput).text(rowPrice);
	        if (dateInput) $(dateInput).val(detail.order_ddate ?? '');
	        
	        const beforeTotal = $('.total-price').text() ?? 0;
	        $('.total-price').text(parseInt(rowPrice) + parseInt(beforeTotal));
    	}
	 	
		// 제품명 select
	    $(row).find('.prd-code').select2({
	    	placeholder: "검색",
	        allowClear: true
	    });
	}
	
	// form submit전 제품 목록 리스트 생성 (payload)
	function setOrderDetails(orderCode) {
		console.log(orderCode)
		const rows = document.querySelectorAll('#item-list .item-list-item');
		const detailsContainer = document.getElementById('ordersDetails');
		detailsContainer.innerHTML = '';
		
		if (rows.length === 0) {
			alert("발주할 품목이 없습니다");
			return false;
		}
		
		let isDisabled = false;
		for (const [index, row] of rows.entries()) {
			const prdSelect = row.querySelector('.prd-code');
			const selectedPrd = $(prdSelect).find('option:selected');
			if (selectedPrd.prop('disabled')) {
				alert("발주 불가 품목이 존재합니다\n수정이 필요합니다");
				isDisabled = true;
				break;
			}

			const prdCode = prdSelect?.value?.trim();
			const prdCount = row.querySelector('.prd-count')?.value?.trim();
			const prdDdate = row.querySelector('.prd-ddate')?.value?.trim();

			let fields = [
				{ name: 'product_code', value: parseInt(prdCode) },
				{ name: 'order_amount', value: parseInt(prdCount) },
				{ name: 'order_ddate', value: prdDdate }
			];
			if (orderCode != 0) {
				fields.push( { name: 'order_code', value: parseInt(orderCode) })
			}

			fields.forEach((field) => {
				const input = document.createElement('input');
				input.type = 'hidden';
				input.name = 'orders_details[' + index + '].' + field.name;
				input.value = field.value;
				detailsContainer.appendChild(input);
			});
		}

		if (isDisabled) return false;
		return true;
	}
	
	function reqOrder(orderCode) {
		const details = getDetails();
		for(detail of details) {
			if (!detail.can_order) {
				alert("발주 불가 품목이 존재합니다\n내용을 수정해주세요");
				return false;
			}
		}
		location.href = "/order/request/" + orderCode;
	}

	function cancelOrder(orderCode, isRefused) {
		// 반려인경우 모달창
		let reason = null;
		
		if (isRefused) {
			reason = $('#orderRefuseReason').val();
			console.log(reason)
			
			if (!reason?.trim()) {
				alert("반려사유를 작성해주세요");
				return false;
			}
			
		}
		
		// post 요청
		fetch('/order/cancel', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			credentials: 'include', // 쿠키(세션ID) 포함
			body: JSON.stringify({
				order_code: orderCode,
				reason: reason
			})
		})
		.then(response => {
			if (!response.ok) {
				throw new Error('요청 실패');
			}
			location.href = "/order/" + orderCode;
		})
	}
  
</script>

</head>
</html>