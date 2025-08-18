<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function () {
		$('#silsa-list').on("change", '.prd-code', function() {
			const $row = $(this).closest('.row');
			// prd 중복 선택 방지
			var existedCodes = $('#silsa-list .prd-code').map(function() {
									return $(this).val();
							   }).get();
			const currentCode = $(this).val();
			if(currentCode && existedCodes.filter(v => v === currentCode).length > 1) {
				alert("중복된 제품이 선택되었습니다");
				return;
			}
			
		    // 전산재고 조정
			const sysCount = $(this).find("option:selected").attr("count");
		    $row.find(".sys-count").text(sysCount);
		    // 수량, 이유 리셋
		    $row.find(".real-count").val(0);
		    $row.find(".silsa-reason").val('');
		    $row.find(".count-gap").text();
		});
		
		$('#silsa-list').on("change", '.real-count', function() {
			const $row = $(this).closest('.row');
			const sysCount = parseInt($row.find("option:selected").attr("count"));
		   	const realCount = parseInt($(this).val());
			$row.find(".count-gap").text(realCount - sysCount);
		});
	});
	
	function addSilaItem() {
	    const container = document.getElementById("silsa-list");
	    const row = document.createElement("div");
	    row.className = "row g-2 mb-2 item-list-item";
	    
	    row.innerHTML = `
	      <div class="col-3">
	    	<select class="form-select prd-code" required>
	    		<option value=""></option>
	    			<c:forEach items="${products }" var="prd">
				<option value="${prd.product_code}" count="${prd.real_stock}">[${prd.product_code}] ${prd.product_name}</option>
			</c:forEach>
		    </select>
	      </div>
	      <div class="col-2">
	        <div class="form-control form-control-sm bg-light sys-count"></div>
	      </div>
	      <div class="col-2">
	        <input type="number" class="form-control form-control-sm real-count" min="0" required></div>
	      </div>
	      <div class="col-1">
	        <div class="form-control form-control-sm bg-warning-subtle count-gap"></div>
	      </div>
	      <div class="col-3">
	        <input type="text" class="form-control form-control-sm silsa-reason">
	      </div>
	      
	      <div class="col-1 text-end pe-0">
	        <button type="button" class="btn btn-outline-danger btn-sm fw-bold" onclick="$(this).closest('.row').remove()">삭제</button>
	      </div>
		`;
	    container.appendChild(row);
	 	
		// 제품명 select
	    $(row).find('.prd-code').select2({
	    	placeholder: "검색",
	        allowClear: true
	    });
	}
	
	function saveSilsaData() {
		// json으로 변경하기
		const rows = document.querySelectorAll('#silsa-list .item-list-item');
		const silsaList = [];
		
		if (rows.length === 0) {
			alert("조정할 품목이 없습니다");
			return false;
		}
		
		if (!confirm("재고를 조정하시겠습니까?")) return false;
		
		for (const [index, row] of rows.entries()) {
			const prdCode = row.querySelector('.prd-code')?.value?.trim();
			const silsaCount = row.querySelector('.count-gap')?.textContent?.trim();
			const silsaReason = row.querySelector('.silsa-reason')?.value?.trim();

			silsaList.push({
				silsa_product_code: parseInt(prdCode),
				silsa_amount:  parseInt(silsaCount),
				silsa_reason: silsaReason
			});
		}
		
		fetch('/inventory/silsa', {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    body: JSON.stringify(silsaList)
		})
		.then(res => res.json())
		.then(data => {
		    location.href = "/";
			//console.log('응답', data);
		})
		.catch(err => {
		    console.error('에러', err);
		});
	}
	
</script>
</head>
<body>
<div class="container p-4">

	<div class="form-section-title">재고 조정 (실사)</div>
	<c:if test="${not isClosedToday }">
		<div class="card border-0 pe-0">
			<div class="alert alert-danger d-flex align-items-center" role="alert">
		  		<i class="bi bi-exclamation-octagon-fill me-2"></i>
		  		<div>
		    		<strong>일마감이 진행된 후 </strong>재고 조정이 가능합니다.<br>
		    		<u>일마감을 먼저 진행해주세요.</u>
		  		</div>
			</div>
		</div>
	</c:if>
	<div class="col-12 border p-3 mb-3">
	  	<div id="silsa-list" class="mb-3">
	  		<div class="row fw-bold text-center border-bottom pb-2 mb-2">
			    <div class="col-3">제품</div>
			    <div class="col-2">전산재고</div>
			    <div class="col-2">실사재고</div>
			    <div class="col-1">차이량</div>
			    <div class="col-3">사유</div>
			    <div class="col-1 text-end pe-0"></div>
			</div>
		</div>
		<!-- 추가 버튼 -->
	<br>
	<button type="button" class="btn btn-sm btn-outline-primary fw-bold" onclick="addSilaItem()">+ 추가</button>
	</div>
	<div class="silsa-data"></div>
	<div class="d-flex justify-content-end gap-2 pe-0">
		<button type="button" class="btn btn-md btn-primary fw-bold" ${isClosedToday ? '' : 'disabled'} onclick="return saveSilsaData()">조정</button>
		<button type="button" class="btn btn-md btn-danger fw-bold" onclick="location.href='/inventory/silsa'">취소</button>
	</div>

</div>
</body>
</html>