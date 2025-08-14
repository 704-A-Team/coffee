<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>생산 신청 수정</title>
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
    <div class="form-section-title">생산 신청 수정</div>

    <!-- 생산 입력 폼 -->
    <form id="mfgForm" action="${pageContext.request.contextPath}/km/mfgUpdate" method="post">
        <input type="hidden" name="mfg_code" value="${mfgDetailDTO.mfg_code}" />
        <div class="mb-3 d-flex gap-2 align-items-center">
            <label class="form-label mb-0">완제품 선택:</label>
            <select id="productSelect" class="form-select w-auto">
                <c:forEach var="wan" items="${mfgWanList}">
                    <option value="${wan.product_code}" data-pack="${wan.product_pack}">
                        ${wan.product_name} (${wan.product_code})
                    </option>
                </c:forEach>
            </select>
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
    console.log("DOMContentLoaded 시작");

    // 서버에서 넘어온 JSON 문자열을 파싱해서 mfgItems 배열로 초기화
    let mfgItems = [];
    try {
        mfgItems = JSON.parse('${mfgDetailUpdateJson}');
    } catch(e) {
        console.warn("JSON 파싱 실패, mfgItems 빈 배열로 초기화");
        mfgItems = [];
    }

    // mfgItems 내부 객체 구조 통일화 (필드명 맞춤)
    mfgItems = mfgItems.map(item => ({
        mfg_code: item.mfg_code ? Number(item.mfg_code) : null, // 없으면 null로
        code: item.product_code || item.code,
        name: item.product_name || item.name,
        pack: Number(item.product_pack || item.pack || 1),
        amount: Number(item.mfg_amount || item.amount || 1),
        total_unit: (Number(item.product_pack || item.pack || 1)) * (Number(item.mfg_amount || item.amount || 1)),
        date: item.mfg_request_date || item.date || ""
    }));


    console.log("mfgItems 초기값:", mfgItems);

    const magamMinDate = "${magamNext}";
    const productSelect = document.getElementById("productSelect");
    const tbody = document.getElementById("mfgList");
 
    // select 옵션 업데이트 (이미 추가된 제품 비활성화 + (추가됨) 표시)
    function updateSelectOptions() {
        console.log("updateSelectOptions 호출");
        const selectedCodes = mfgItems.map(i => String(i.code));
    
        for (let option of productSelect.options) {
            if (!option.dataset.baseText) {
                option.dataset.baseText = option.textContent;
            }
            option.textContent = option.dataset.baseText;
    
            const code = option.value;
            const isExisting = selectedCodes.includes(code);
    
            option.disabled = isExisting;
    
            if (isExisting) {
                option.textContent += " (추가됨)";
                option.style.color = "gray";
            } else {
                option.style.color = "black";
            }
        }
    }

    // 테이블 렌더링
    function renderTable() {
        console.log("renderTable 호출, 현재 mfgItems:", mfgItems);
        tbody.innerHTML = "";

        mfgItems.forEach((item, idx) => {
            const tr = document.createElement("tr");

            const tdName = document.createElement("td");
            tdName.textContent = item.name;

            const tdPack = document.createElement("td");
            tdPack.textContent = item.pack;

            const tdAmount = document.createElement("td");
            const amountInput = document.createElement("input");
            amountInput.type = "number";
            amountInput.min = 1;
            amountInput.max = 9999;
            amountInput.value = item.amount;
            amountInput.className = "form-control form-control-sm";
            amountInput.oninput = (e) => {
                let val = Number(e.target.value);
                if (val < 1) val = 1;
                else if (val > 9999) val = 9999;
                e.target.value = val;
                mfgItems[idx].amount = val;
                mfgItems[idx].total_unit = mfgItems[idx].pack * val;
                tdTotal.textContent = mfgItems[idx].total_unit;
            };
            tdAmount.appendChild(amountInput);

            const tdTotal = document.createElement("td");
            tdTotal.textContent = item.total_unit;

            const tdDate = document.createElement("td");
            const dateInput = document.createElement("input");
            dateInput.type = "datetime-local";
            dateInput.className = "form-control form-control-sm";
            dateInput.min = magamMinDate + "T00:00";
            dateInput.value = item.date || "";
            dateInput.onchange = (e) => {
                mfgItems[idx].date = e.target.value;
            };
            tdDate.appendChild(dateInput);

            const tdDelete = document.createElement("td");
            const delBtn = document.createElement("button");
            delBtn.type = "button";
            delBtn.textContent = "삭제";
            delBtn.className = "btn btn-danger btn-sm";
            delBtn.onclick = () => {
                console.log(`삭제: 인덱스 ${idx} 제품코드 ${mfgItems[idx].code}`);
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
        console.log("renderTable 완료");
    }
    
    // 제품 추가 (중복 검사 포함!)
    window.addMfg = () => {
        const selected = productSelect.selectedOptions[0];
        if (!selected || !selected.value) {
            alert("완제품을 선택하세요.");
            return;
        }
        const code = selected.value;
        const name = selected.text.replace(" (추가됨)", "");
        const pack = Number(selected.dataset.pack);

        const isDuplicate = mfgItems.some(item => item.code === code);
        console.log(`addMfg 호출, 선택값: ${code}, 중복 여부: ${isDuplicate}`);

        if (isDuplicate) {
            alert("이미 추가된 제품입니다.");
            return;
        }

        // 신규 추가는 mfg_code를 0으로 설정해서 백엔드에서 insert 처리하도록 함
        const mfgCode = 0;

        const amount = 1;
        const total_unit = pack * amount;
        
        mfgItems.push({ mfg_code: mfgCode, code, name, pack, amount, total_unit, date: "" });

        renderTable();
        updateSelectOptions();
    };

    // 폼 제출
    window.submitForm = () => {
        console.log("submitForm 호출, mfgItems 상태:", mfgItems);

        if (mfgItems.length === 0) {
            alert("생산 항목을 하나 이상 추가하세요.");
            return;
        }
        if (mfgItems.some(i => !i.date)) {
            alert("모든 항목에 요청일을 입력해주세요.");
            return;
        }

        const form = document.getElementById("mfgForm");

        // 기존 숨겨진 input 제거
        const existingInput = form.querySelector("input[name='mfgDetailListJson']");
        if (existingInput) existingInput.remove();

        // JSON 문자열로 숨겨진 input 생성
        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "mfgDetailListJson";
        hiddenInput.value = JSON.stringify(mfgItems.map(i => ({
            mfg_code: Number(i.mfg_code),
            product_code: i.code,
            mfg_amount: i.amount,
            mfg_request_date: i.date
        })));

        form.appendChild(hiddenInput);
        form.submit();
    };

    // 취소 버튼 함수
    window.cancelForm = () => {
        if(confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
            window.history.back();
        }
    };

    updateSelectOptions();
    renderTable();
});
</script>
</body>
</html>