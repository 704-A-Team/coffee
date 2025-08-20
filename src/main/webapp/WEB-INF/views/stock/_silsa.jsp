<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function () {
		const silsas = [
			<c:forEach items="${silsas }" var="silsa">
				{
					prd_code: "${silsa.silsa_product_code}",
					real_count: "${silsa.silsa_after_amount}",
					silsa_reason: "${silsa.silsa_reason}",
			    },
			</c:forEach>
	    ];

		if (silsas.length === 0) {
			addSilaItem();
		} else {
			silsas.forEach(data => addSilaItem(data));
		}
		
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
	
	function addSilaItem(data = null) {
	    const container = document.getElementById("silsa-list");
	    const row = document.createElement("div");
	    row.className = "row g-0 g-2 mb-2 item-list-item";
	    
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
	        <input type="number" class="form-control form-control-sm bg-primary-subtle real-count" min="0" required></div>
	      </div>
	      <div class="col-1">
	        <div class="form-control form-control-sm bg-warning-subtle count-gap"></div>
	      </div>
	      <div class="col-3">
	        <input type="text" class="form-control form-control-sm silsa-reason">
	      </div>
	      
	      <div class="col-1 text-center pe-0">
	        <button type="button" class="btn btn-outline-danger btn-sm fw-bold" onclick="$(this).closest('.row').remove()">삭제</button>
	      </div>
		`;
	    container.appendChild(row);
	    
	    // 기존 데이터 셋팅
	    if (data !== null) {
	    	$(row).find('.prd-code').val(data.prd_code);
	    	const sys_count = $(row).find('.prd-code').find('option[value="' + data.prd_code + '"]').attr("count") || 0;
	    	$(row).find('.sys-count').text(sys_count);
	    	$(row).find('.real-count').val(data.real_count);
	    	$(row).find('.count-gap').text(data.real_count - sys_count);
	    	$(row).find('.silsa-reason').val(data.silsa_reason);
	    }
	 	
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
		
		for (const [index, row] of rows.entries()) {
			const prdCode = row.querySelector('.prd-code')?.value?.trim();
			const silsaCount = row.querySelector('.count-gap')?.textContent?.trim();
			const realCount = row.querySelector('.real-count')?.value?.trim();
			const silsaReason = row.querySelector('.silsa-reason')?.value?.trim();

			if (!prdCode || !realCount) {
	            alert("빈칸을 채워주세요");
	            return false;
	        }
			
			silsaList.push({
				silsa_product_code: parseInt(prdCode),
				silsa_amount:  parseInt(silsaCount),
				silsa_after_amount: parseInt(realCount),
				silsa_reason: silsaReason
			});
		}
		
		if (!confirm("재고를 조정하시겠습니까?")) return false;
		
		fetch('/inventory/silsa', {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    body: JSON.stringify(silsaList)
		})
		.then(res => {
			location.href = "/inventory/silsa";
			altert("재고가 조정되었습니다")
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
	<div class="d-flex justify-content-end mb-4 fs-5">${now}</div>
	
	<c:if test="${isClosed }">
		<div class="card border-0 pe-0">
			<div class="alert alert-danger d-flex align-items-center" role="alert">
		  		<i class="bi bi-exclamation-octagon-fill me-2"></i>
		  		<div>
		    		<strong>월마감 완료 후 </strong>재고를 조정할 수 없습니다.<br>
		  		</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not isClosed and isClosedToday}">
		<div class="card border-0 pe-0">
			<div class="alert alert-info d-flex align-items-center" role="alert">
		  		<i class="bi bi-info-circle-fill me-2"></i>
		  		<div>
		    		<strong>일마감이 완료</strong>되었습니다.<br>
		    		<u>일마감을 취소한 뒤 진행해주세요.</u>
		  		</div>
			</div>
		</div>
	</c:if>
	<div class="col-12 border p-3 mb-3">
	  		<div class="row g-0 fw-bold text-center border-bottom pb-2 mb-2">
			    <div class="col-3">제품</div>
			    <div class="col-2">전산재고</div>
			    <div class="col-2">실사재고</div>
			    <div class="col-1">차이량</div>
			    <div class="col-3">사유</div>
			    <div class="col-1 text-end pe-0"></div>
			</div>
			<!-- 스크롤 가능한 영역 -->
			<div style="max-height: 500px; overflow-x: auto; overflow-x: hidden; width: 100%;">
        		<div id="silsa-list" class="mb-3">
		        <!-- addSilaItem으로 추가될 row들이 이 안으로 들어옴 -->
		    	</div>
			</div>
		<!-- 추가 버튼 -->
		<br>
		<button type="button" class="btn btn-sm btn-outline-primary fw-bold" onclick="addSilaItem()">+ 추가</button>
	</div>
	<div class="d-flex justify-content-end gap-2 pe-0">
		<button type="button" class="btn btn-md btn-primary fw-bold" ${not isClosed and not isClosedToday? '' : 'disabled'} onclick="return saveSilsaData()">조정</button>
		<button type="button" class="btn btn-md btn-danger fw-bold" onclick="location.href='/inventory/silsa'">취소</button>
	</div>

</div>
</body>
</html>