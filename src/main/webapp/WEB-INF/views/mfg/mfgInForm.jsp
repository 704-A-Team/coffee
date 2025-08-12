<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>생산 신청</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
<style>
    .form-section-title {
        border-left: 4px solid #198754;
        padding-left: 10px;
        margin-bottom: 20px;
        font-weight: 600;
        font-size: 2rem;
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>
<div class="d-flex flex-grow-1">
<%@ include file="../sidebar.jsp" %>

<div class="d-flex flex-column flex-grow-1">
<main class="flex-grow-1 p-4">
<div class="container mt-3">
    <div class="form-section-title">생산 신청</div>

    <!-- 생산 입력 폼 -->
    <form id="mfgForm" action="${pageContext.request.contextPath}/km/mfgRegister" method="post">
		 <div class="mb-3 d-flex gap-2 align-items-center">
		    <label class="form-label mb-0">완제품 선택:</label>
		    <select id="productSelect" class="form-select w-auto" onchange="updatePack()">
		        <c:forEach var="wan" items="${mfgWanList}">
		            <option value="${wan.product_code}" data-pack="${wan.product_pack}">${wan.product_name} (${wan.product_code})</option>
		        </c:forEach>
		    </select>
		
		    <label class="mb-0">생산 수량:</label>
		    <input type="number" id="mfg_amount" min="1" max="99999" class="form-control w-auto" oninput="updatePack()" />
		
		    <label class="mb-0">낱개 수량:</label>
		    <span id="singleAmount" class="fw-bold">-</span>
		
		    <button type="button" class="btn btn-success btn-sm" onclick="addMfg()">추가</button>
		</div>

        <!-- 보고서 형태 테이블 -->
        <table class="table table-bordered table-hover mt-4">
            <thead class="table-light">
                <tr>
                    <th>제품명</th>
                    <th>생산 단위</th>
                    <th>생산 수량</th>
                    <th>낱개 수량</th>
                    <th>요청일</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody id="mfgList"></tbody>
        </table>

    <!--     <button type="button" class="btn btn-primary" onclick="submitForm()">생산 신청하기</button> -->
        <!-- 버튼들을 오른쪽 정렬할 div 추가 -->
		<div class="d-flex justify-content-end gap-2 mt-3">
		    <button type="button" class="btn btn-primary" onclick="submitForm()">생산 신청하기</button>
		    <button type="button" class="btn btn-secondary" onclick="cancelForm()">취소</button>
		</div>
        
    </form>
</div>
</main>
<%@ include file="../footer.jsp" %>
</div>
</div>
<script>
document.addEventListener("DOMContentLoaded", () => {
    let mfgItems = [];
    
    const magamMinDate = "${magamNext}";
    

    const productSelect = document.getElementById("productSelect");
    const amountInput = document.getElementById("mfg_amount");
    const unitSpan = document.getElementById("singleAmount");
    const tbody = document.getElementById("mfgList");

    // ✅ 오늘 날짜 (UTC+9 기준으로 정확하게)
    const today = new Date();
    today.setHours(today.getHours() + 9); // KST 보정
    const todayStr = today.toISOString().split("T")[0];

    // 단위 계산
    window.updatePack = () => {
        const pack = Number(productSelect.selectedOptions[0].dataset.pack);
        const amount = Number(amountInput.value);
        unitSpan.innerText = (!amount || isNaN(amount)) ? "-" : pack * amount;
    };

    // 모달 알림 함수
    function showModal(message) {
        alert(message); // 간단하게 alert 사용. Bootstrap 모달 사용 시 코드 추가 가능
    }

    // 항목 추가
    window.addMfg = () => {
        const selected = productSelect.selectedOptions[0];
        const code = selected.value;
        const name = selected.text.replace(" (추가됨)", "");
        const pack = Number(selected.dataset.pack);
        const amount = Number(amountInput.value);

        // ✅ 생산 수량 유효성 검사
        if (!amount || isNaN(amount) || amount < 1 || amount > 9999) {
            showModal("생산 수량은 1 이상 9999 이하만 입력 가능합니다.");
            return;
        }

        if (mfgItems.find(item => item.code === code)) {
            showModal("이미 추가된 제품입니다.");
            return;
        }

        mfgItems.push({
            code,
            name,
            pack,
            amount,
            total_unit: pack * amount,
            date: ""
        });

        amountInput.value = "";
        unitSpan.innerText = "-";

        renderTable();
        updateSelectOptions();
    };

 // 테이블 렌더링
    function renderTable() {
        tbody.innerHTML = "";

        mfgItems.forEach((item, idx) => {
            const tr = document.createElement("tr");

            const tdName = document.createElement("td");
            tdName.textContent = item.name;

            const tdPack = document.createElement("td");
            tdPack.textContent = item.pack;

            const tdAmount = document.createElement("td");
            tdAmount.textContent = item.amount;

            const tdTotal = document.createElement("td");
            tdTotal.textContent = item.total_unit;

            const tdDate = document.createElement("td");
            const dateInput = document.createElement("input");
            dateInput.type = "datetime-local"; // ✅ 변경
            dateInput.className = "form-control form-control-sm";
            dateInput.min = magamMinDate + "T00:00"; // ✅ min 설정도 datetime에 맞게
            dateInput.value = item.date || "";
            dateInput.onchange = (e) => {
                mfgItems[idx].date = e.target.value;
                console.log(`📅 [${idx}] 날짜 변경 →`, e.target.value);
            };
            tdDate.appendChild(dateInput);

            const tdDelete = document.createElement("td");
            const delBtn = document.createElement("button");
            delBtn.type = "button";
            delBtn.textContent = "삭제";
            delBtn.className = "btn btn-danger btn-sm";
            delBtn.onclick = () => {
                console.log(`❌ 삭제됨 → ${item.name}`);
                mfgItems.splice(idx, 1);
                renderTable();
                updateSelectOptions();
            };
            tdDelete.appendChild(delBtn);

            tr.appendChild(tdName);
            tr.appendChild(tdPack);
            tr.appendChild(tdAmount);
            tr.appendChild(tdTotal);
            tr.appendChild(tdDate);
            tr.appendChild(tdDelete);

            tbody.appendChild(tr);
        });

        console.log("📦 렌더링 완료:", JSON.stringify(mfgItems, null, 2));
    }


    // select 옵션 업데이트
    function updateSelectOptions() {
        const selectedCodes = mfgItems.map(i => i.code);
        for (let option of productSelect.options) {
            const code = option.value;
            const already = selectedCodes.includes(code);
            option.disabled = already;
            option.text = option.text.replace(" (추가됨)", "");
            if (already) option.text += " (추가됨)";
        }
    }

    // 폼 전송
    window.submitForm = () => {
        if (mfgItems.length === 0) {
            showModal("생산 항목을 하나 이상 추가하세요.");
            return;
        }

        if (mfgItems.some(i => !i.date)) {
            showModal("모든 항목에 요청일을 입력해주세요.");
            return;
        }

        const form = document.getElementById("mfgForm");

        const existingInput = form.querySelector("input[name='mfgDetailListJson']");
        if (existingInput) existingInput.remove();

        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "mfgDetailListJson";
        hiddenInput.value = JSON.stringify(mfgItems.map(i => ({
            product_code: i.code,
            mfg_amount: i.amount,
            mfg_request_date: i.date
        })));

        form.appendChild(hiddenInput);
        form.submit();
    };

    updatePack();
    updateSelectOptions();
    renderTable();
});
</script>
</body>
</html>