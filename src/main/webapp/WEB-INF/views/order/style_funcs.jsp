<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .doc-wrapper {
      aspect-ratio: 1 / 1.414; /* A4 비율 (1:√2) */
      max-width: 100%;
      width: 90%; /* 반응형 기준 너비 */
      background: white;
      margin: auto;
      padding: 2rem;
      box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
      overflow: auto;
    }

/*     @media print {
      body {
        background: none;
        padding: 0;
      }

      .doc-wrapper {
        box-shadow: none;
        width: auto;
        aspect-ratio: auto;
        padding: 0;
      }

      button {
        display: none;
      }
    } */
</style>
<script>

	// 새로운 발주서 작성 시 제품 목록 행 자동 추가
	document.addEventListener("DOMContentLoaded", function () {
		const rows = document.querySelectorAll('#item-list .item-list-item');
		if (rows.length === 0) addItem();
	});

	// 제품 목록 추가 버튼
	function addItem() {
	    const container = document.getElementById("item-list");
	    const row = document.createElement("div");
	    row.className = "row g-2 mb-2 item-list-item";
	
	    row.innerHTML = `
	      <div class="col-3">
	        <input type="number" class="form-control form-control-sm item-code" required placeholder="제품코드">
	      </div>
	      <div class="col-2">
	        <input type="number" class="form-control form-control-sm item-count" required placeholder="수량">
	      </div>
	      <div class="col-4">
	        <input type="date" class="form-control form-control-sm item-ddate" required placeholder="납기일">
	      </div>
	      
	      <div class="col-1 text-end pe-0">
	        <button type="button" class="btn btn-outline-danger btn-sm fw-bold" onclick="this.closest('.row').remove()">삭제</button>
	      </div>
	    `;
	    container.appendChild(row);
	}
	
	// form submit전 제품 목록 리스트 생성
	function setOrderDetails() {
		const rows = document.querySelectorAll('#item-list .item-list-item');
		const detailsContainer = document.getElementById('ordersDetails');
		detailsContainer.innerHTML = '';
		
		if (rows.length === 0) {
			alert("발주할 품목이 없습니다");
			return false;
		}
		
		rows.forEach((row, index) => {
			const itemCode = row.querySelector('.item-code')?.value?.trim();
			const itemCount = row.querySelector('.item-count')?.value?.trim();
			const dueDate = row.querySelector('.item-ddate')?.value?.trim();

			const fields = [
				{ name: 'product_code', value: parseInt(itemCode) },
				{ name: 'order_amount', value: parseInt(itemCount) },
				{ name: 'order_ddate', value: dueDate }
			];

			fields.forEach((field) => {
				const input = document.createElement('input');
				input.type = 'hidden';
				input.name = 'orders_details[' + index + '].' + field.name;
				input.value = field.value;
				detailsContainer.appendChild(input);
			});
		});

		return true;
	}
	
	// form submit
	function submitReq(url) {
		const form = document.getElementById('orderForm');
	    form.action = url;
	}
  
</script>

</head>
</html>