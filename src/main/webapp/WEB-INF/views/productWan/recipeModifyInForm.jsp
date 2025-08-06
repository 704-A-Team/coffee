<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>레시피 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        table th, table td {
            vertical-align: middle !important;
            color: black;
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
                <div class="form-section-title">레시피 수정</div>

                <div>
                    <p>제품명 : <strong>${wanAndRcpDetail.product_name}</strong></p>
                    <p>중량 : <strong>${wanAndRcpDetail.product_pack}</strong></p>
                </div>

                <form id="recipeForm" action="${pageContext.request.contextPath}/km/recipeModify" method="post">
                    <input type="hidden" name="product_code" value="${wanAndRcpDetail.product_code}" />

                    <div class="mb-3 d-flex align-items-center gap-2">
                        <label for="product_won_code" class="mb-0">재료:</label>
                        <select id="product_won_code" class="form-select w-auto" onchange="updateUnit()">
                            <c:forEach var="item" items="${wonList}">
                                <option value="${item.product_code}" data-unit="${item.product_unit}">
                                    ${item.product_name}
                                </option>
                            </c:forEach>
                        </select>

                        <label for="recipe_amount" class="mb-0">수량:</label>
                        <input type="number" id="recipe_amount" min="1" class="form-control w-auto" />

                        <label class="mb-0">단위: <span id="unitDisplay">g</span></label>

                        <button type="button" class="btn btn-primary btn-sm" onclick="addMaterial()">추가</button>
                    </div>

                    <hr />

                    <table class="table table-bordered table-striped">
                        <thead class="table-light">
                            <tr>
                                <th>원재료명</th>
                                <th>소요량</th>
                                <th>단위</th>
                                <th>비율(%)</th>
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody id="materialList"></tbody>
                    </table>

                    <button type="button" class="btn btn-success" onclick="submitForm()">레시피 수정하기</button>
                    <a href="${pageContext.request.contextPath}/km/wanAndRcpDetailInForm?product_code=${wanAndRcpDetail.product_code}" class="btn btn-secondary">취소</a>
                </form>
            </div>
        </main>

        <%@ include file="../footer.jsp" %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    let selectedMaterials = [];

    // 1) 기존 레시피 JSON 문자열을 JS 객체로 변환
    const existingRecipeList = JSON.parse('${recipeListJson}');
    console.log("기존 레시피 리스트:", existingRecipeList);

    if (Array.isArray(existingRecipeList)) {
        selectedMaterials = existingRecipeList.map(item => ({
            code: item.product_won_code,
            name: item.won_product_name,
            unit: String(item.product_unit),  // 문자열로 맞춤
            amount: Number(item.recipe_amount)
        }));
    }

    const selectElem = document.getElementById("product_won_code");

    // 2) 단위 표시 업데이트
    window.updateUnit = () => {
        const unitMap = { "1": 'g', "2": 'ml' };
        const selectedOption = selectElem.options[selectElem.selectedIndex];
        const unit = selectedOption.getAttribute("data-unit");
        document.getElementById("unitDisplay").innerText = unitMap[unit] || '';
    };

    // 3) 재료 추가 함수
    window.addMaterial = () => {
        const selectedOption = selectElem.options[selectElem.selectedIndex];
        const code = selectedOption.value;
        const name = selectedOption.text.trim();
        const unit = selectedOption.getAttribute("data-unit");
        const amountInput = document.getElementById("recipe_amount");
        const amount = Number(amountInput.value);

        if (!amount || amount <= 0) {
            alert("소요량을 정확히 입력하세요.");
            amountInput.focus();
            return;
        }

        if (selectedMaterials.find(mat => mat.code === code)) {
            alert("이미 추가된 재료입니다.");
            return;
        }

        selectedMaterials.push({ code, name, unit, amount });
        amountInput.value = "";
        updateUnit();
        renderTable();
        updateSelectOptions(selectedMaterials);
    };

    // 4) 테이블 렌더링 함수
    function renderTable() {
        const tbody = document.getElementById("materialList");
        tbody.innerHTML = "";
        
        console.log("renderTable 호출, selectedMaterials:", selectedMaterials);

        if (selectedMaterials.length === 0) {
            console.warn("renderTable: selectedMaterials가 비어있음!");
        }

        const totalAmount = selectedMaterials.reduce((sum, mat) => sum + Number(mat.amount), 0);

        selectedMaterials.forEach((mat, idx) => {
            const tr = document.createElement("tr");

            const tdName = document.createElement("td");
            tdName.textContent = mat.name;

            const tdAmount = document.createElement("td");
            tdAmount.textContent = mat.amount;

            const tdUnit = document.createElement("td");
            tdUnit.textContent = mat.unit === "1" ? "g" : "ml";

            const ratio = totalAmount > 0 ? ((mat.amount / totalAmount) * 100).toFixed(2) : "0.00";
            const tdRatio = document.createElement("td");
            tdRatio.textContent = ratio + " %";

            const tdDelete = document.createElement("td");
            const btn = document.createElement("button");
            btn.type = "button";
            btn.textContent = "삭제";
            btn.classList.add("btn", "btn-danger", "btn-sm");
            btn.onclick = () => {
                removeMaterial(idx);
            };
            tdDelete.appendChild(btn);

            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdUnit);
            tr.appendChild(tdRatio);
            tr.appendChild(tdDelete);

            tbody.appendChild(tr);
        });

        console.log("렌더링 완료. 현재 원재료 목록:", selectedMaterials);
    }

    // 5) 삭제 함수
    function removeMaterial(index) {
        selectedMaterials.splice(index, 1);
        renderTable();
        updateSelectOptions(selectedMaterials);
    }

    window.removeMaterial = removeMaterial;

    // 6) select 옵션 상태 갱신 함수
function updateSelectOptions(selectedMaterials) {
  const selectElement = document.getElementById("product_won_code"); // ✅ 수정된 id
  const selectedCodes = selectedMaterials.map(item => Number(item.code)); // 코드 숫자로 정리

  for (let i = 0; i < selectElement.options.length; i++) {
    const option = selectElement.options[i];
    const materialCode = Number(option.value);

    if (selectedCodes.includes(materialCode)) {
      option.disabled = true;
      if (!option.text.includes("(추가된 원재료)")) {
        option.text += " (추가된 원재료)";
      }
    } else {
      option.disabled = false;
      option.text = option.text.replace(" (추가된 원재료)", "");
    }
  }
}


    // 7) 폼 제출 시 JSON으로 변환해서 hidden input에 담아 전송
    window.submitForm = () => {
        if (selectedMaterials.length === 0) {
            alert("적어도 하나 이상의 재료를 추가해주세요.");
            return;
        }

        const form = document.getElementById("recipeForm");
        const productCode = form.querySelector('input[name="product_code"]').value;

        // 기존 숨은 필드 제거 (중복 방지)
        const existingInput = form.querySelector('input[name="materialsJson"]');
        if (existingInput) form.removeChild(existingInput);

        const materialsForSubmit = selectedMaterials.map(mat => ({
            product_wan_code: Number(productCode),
            product_won_code: Number(mat.code),
            recipe_amount: Number(mat.amount)
        }));

        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "materialsJson";
        hiddenInput.value = JSON.stringify(materialsForSubmit);

        form.appendChild(hiddenInput);
        form.submit();
    };

    // 최초 화면 셋업
    updateUnit();
    renderTable();
    updateSelectOptions(selectedMaterials);
});
</script>

</body>
</html>
