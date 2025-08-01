<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>레시피 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

<!-- HEADER -->
<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
    <!-- SIDEBAR -->
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <!-- 본문 -->
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">레시피 등록</div>

                <div>
                    <p>완제품 : <strong>${product_name}</strong></p>
                    <p>완제품 생산단위 : <strong>${product_pack}</strong></p>
                </div>

                <form id="recipeForm" action="${pageContext.request.contextPath}/km/wanRecipeSave" method="post">
                    <input type="hidden" name="product_code" value="${product_code}" />

                    <div class="mb-3">
                        <label>원재료 선택:
                            <select id="product_won_code" class="form-select d-inline w-auto" onchange="updateUnit()">
                                <c:forEach var="item" items="${wonList}">
                                    <option value="${item.product_code}" data-unit="${item.product_unit}">
                                        ${item.product_name}
                                    </option>
                                </c:forEach>
                            </select>
                        </label>

                        <label>소요량:
                            <input type="number" id="recipe_amount" min="1" class="form-control d-inline w-auto" />
                        </label>

                        <label>단위:
                            <span id="unitDisplay">g</span>
                        </label>

                        <button type="button" class="btn btn-primary btn-sm" onclick="addMaterial()">추가</button>
                    </div>

                    <hr>

                    <table class="table table-bordered table-striped">
                        <thead class="table-light">
                            <tr>
                                <th>원재료명</th>
                                <th>소요량</th>
                                <th>단위</th>
                                <th>비율</th> 
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody id="materialList">
                            <!-- 동적 행 추가됨 -->
                        </tbody>
                    </table>

                    <button type="button" class="btn btn-success" onclick="submitForm()">레시피 등록</button>
                </form>
            </div>
        </main>

        <!-- FOOTER -->
        <%@ include file="../footer.jsp" %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
    let selectedMaterials = [];

    window.updateUnit = function() {
        const unitMap = { 1: 'g', 2: 'ml' };
        const select = document.getElementById("product_won_code");
        const selectedOption = select.options[select.selectedIndex];
        const unit = selectedOption.getAttribute("data-unit");
        document.getElementById("unitDisplay").innerText = unitMap[unit] || '';
    }

    window.addMaterial = function() {
        const select = document.getElementById("product_won_code");
        const selectedOption = select.options[select.selectedIndex];
        const code = selectedOption.value;
        const name = selectedOption.text.trim();
        const unit = selectedOption.getAttribute("data-unit");
        const amount = document.getElementById("recipe_amount").value.trim();

        if (!amount || isNaN(amount) || amount <= 0) {
            alert("소요량을 정확히 입력하세요.");
            return;
        }

        selectedMaterials.push({ code, name, unit, amount });
        renderTable();
    };

    function renderTable() {
        const tbody = document.getElementById("materialList");
        tbody.innerHTML = "";

        // 전체 소요량 합계 계산
        const totalAmount = selectedMaterials.reduce((sum, mat) => sum + Number(mat.amount), 0);

        selectedMaterials.forEach((mat, index) => {
            const unitText = mat.unit === "1" ? "g" : "ml";

            const tr = document.createElement("tr");

            const tdName = document.createElement("td");
            tdName.textContent = mat.name;

            const tdAmount = document.createElement("td");
            tdAmount.textContent = mat.amount;

            const tdUnit = document.createElement("td");
            tdUnit.textContent = unitText;

            // ✅ 비율 계산
            const ratio = totalAmount > 0 ? ((mat.amount / totalAmount) * 100).toFixed(2) : "0.00";
            const tdRatio = document.createElement("td");
            tdRatio.textContent = ratio + " %";

            const tdDelete = document.createElement("td");
            const btn = document.createElement("button");
            btn.type = "button";
            btn.textContent = "삭제";
            btn.onclick = () => removeMaterial(index);
            tdDelete.appendChild(btn);

            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdUnit);
            tr.appendChild(tdRatio); // ✅ 비율 추가
            tr.appendChild(tdDelete);

            tbody.appendChild(tr);
        });

        console.log("렌더링 완료. 현재 원재료 목록:", selectedMaterials);
    }



    window.removeMaterial = function(index) {
        selectedMaterials.splice(index, 1);
        renderTable();
    };

    window.submitForm = function() {
        const form = document.getElementById("recipeForm");

        // 완제품 코드 가져오기
        const productWanCode = document.querySelector('input[name="product_code"]').value;

        // 재료 목록에 완제품 코드 추가
        const materialListWithProductCode = selectedMaterials.map(mat => ({
            product_won_code: mat.code,
            recipe_amount: mat.amount,
            product_wan_code: productWanCode
        }));

        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "materialsJson";								  // ✅ 서버로 전송될 파라미터 이름
        hiddenInput.value = JSON.stringify(materialListWithProductCode);  // ✅ DTO 필드명에 맞춘 JSON 문자열

        form.appendChild(hiddenInput);									  // 폼에 숨은 input 추가
        form.submit();
    }; 

    updateUnit(); // 최초 실행
});
</script>

</body>
</html>