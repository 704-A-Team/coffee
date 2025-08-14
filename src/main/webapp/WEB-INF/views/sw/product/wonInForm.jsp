<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --main-brown: #6f4e37;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
        }

        body { background-color: #f9f5f1; font-family: 'Segoe UI', sans-serif; }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--main-brown);
        }

        .btn-brown { background-color: var(--soft-brown) !important; color: white !important; border: none !important; }
        .btn-brown:hover { background-color: var(--main-brown) !important; }

        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: white !important;
        }
        .btn-brown-outline:hover {
            background-color: var(--main-brown) !important;
            color: white !important;
            border-color: var(--main-brown) !important;
        }

        @media (max-width: 768px) {
            .d-flex.justify-content-end { justify-content: center !important; }
        }
        
        .btn-secondary-custom {
            background:#eee!important; color:#333!important; border:1px solid #ccc!important;
        }
        .btn-secondary-custom:hover { background:#ccc!important; }
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">제품(원재료) 등록</div>

                <form action="${pageContext.request.contextPath}/sw/wonProductSave" method="post" enctype="multipart/form-data">
                    
                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" placeholder="제품명을 입력해주세요" required>
                    </div>
                    
                    <!-- 제품명 중복체크 리스트 -->
                    <div id="existingNames" style="display:none;">
					  <c:forEach var="p" items="${wonProductAllList}">
					    <div class="name">${p.product_name}</div>
					  </c:forEach>
					</div>

                    <!-- 단위 / 납품 여부 -->
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label for="product_unit" class="form-label">단위</label>
                            <select id="product_unit" name="product_unit" class="form-select" required>
                                <option value="">-- 선택 --</option>
                                <option value="0">ea</option>
                                <option value="1">g</option>
                                <option value="2">ml</option>
                            </select>
                        </div>
                        
                        <div class="col-md-4">
                            <label for="product_isorder" class="form-label">납품 여부</label>
                            <select id="product_isorder" name="product_isorder" class="form-select" required>
                                <option value="">-- 선택 --</option>
                                <option value="0">납품</option>
                                <option value="1">비납품</option>
                            </select>
                        </div>
                    </div>

                    <!-- 기본중량 -->
                    <div class="mb-3">
                        <label for="product_weight" class="form-label">기본중량 (g)</label>
                        <input type="number" class="form-control" id="product_weight" name="product_weight" placeholder="숫자만 입력해주세요" required>
                    </div>

                    <!-- 적정수량 -->
                    <div class="mb-3">
                        <label for="product_min_amount" class="form-label">적정수량</label>
                        <input type="number" class="form-control" id="product_min_amount" name="product_min_amount" placeholder="적정 재고 수량을 입력해주세요" min="0" required>
                    </div>

                    <!-- 판매단위(콤보박스) -->
                    <div class="mb-3">
                        <label for="product_order_pack" class="form-label">판매단위</label>
                        <select class="form-select" id="product_order_pack" name="product_order_pack" disabled>
                            <option value="">-- 단위를 먼저 선택하세요 --</option>
                        </select>
                    </div>

                    <!-- 이미지 -->
                    <div class="mb-3">
                        <label for="files" class="form-label">제품(원재료) 이미지</label>
                        <input type="file" class="form-control" id="files" name="files" multiple accept=".jpg,.jpeg,.png" required="required">
                        <div class="form-text text-danger mt-1">※ 최소 1개의 이미지를 등록해야 하고 최대 3개의 이미지를 업로드할 수 있습니다.</div>
                    </div>

                    <!-- 버튼 영역: 오른쪽 하단 정렬 -->
                    <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                        <button type="submit" class="btn btn-primary">등록</button>
                        <button type="reset" class="btn btn-secondary-custom">초기화</button>
                    </div>
                </form>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<!-- 단위/납품여부에 따라 판매단위 사용 가능 제어 -->
<script>
document.addEventListener("DOMContentLoaded", function () {
    const unitSelect = document.getElementById("product_unit");
    const isOrderSelect = document.getElementById("product_isorder");
    const orderPackSelect = document.getElementById("product_order_pack");
    
    const ajax_existingNameSet = new Set(
       	    Array.from(document.querySelectorAll('#existingNames .name'))
       	      .map(n => n.textContent.trim().toLowerCase())
       	      .filter(Boolean)
       	  );

    const input = document.getElementById('product_name');
    const form  = document.querySelector('form[action$="/sw/wonProductSave"]');

    // blur 시 간단 체크
    input.addEventListener('blur', function(){
      const key = input.value.trim().toLowerCase();
      if (key && ajax_existingNameSet.has(key)) {
        input.setCustomValidity('이미 등록된 제품명입니다.');
        input.reportValidity();
      } else {
        input.setCustomValidity('');
      }
    });

    // 제출 직전 한 번 더 체크
    form.addEventListener('submit', function(e){
      const key = input.value.trim().toLowerCase();
      if (key && ajax_existingNameSet.has(key)) {
        e.preventDefault();
        alert('이미 등록된 제품명입니다. 다른 이름을 입력해 주세요.');
        input.focus();
        return false;
      }
    });

    // 옵션 채우기 로직
    function populateOrderPackOptions() {
        orderPackSelect.innerHTML = "";
        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "-- 선택 --";
        orderPackSelect.appendChild(defaultOption);

        const unit = unitSelect.value;
        if (unit === "0") { // ea
            for (let i = 10; i <= 100; i += 10) {
                const opt = document.createElement("option");
                opt.value = i;
                opt.textContent = i + " ea";
                orderPackSelect.appendChild(opt);
            }
        } else if (unit === "1" || unit === "2") { // g or ml
            for (let i = 100; i <= 1000; i += 100) {
                const opt = document.createElement("option");
                opt.value = i;
                opt.textContent = i + (unit === "1" ? " g" : " ml");
                orderPackSelect.appendChild(opt);
            }
        } else {
            // 단위 미선택 시
            const opt = document.createElement("option");
            opt.value = "";
            opt.textContent = "-- 단위를 먼저 선택하세요 --";
            orderPackSelect.appendChild(opt);
        }
    }

    // 납품여부에 따라 판매단위 활성/비활성
    function toggleOrderPackAvailability() {
        const isOrder = isOrderSelect.value; // "0": 납품, "1": 비납품
        if (isOrder === "0") {
            // 납품: 활성화 + 옵션 채우기 + required 부여
            orderPackSelect.disabled = false;
            populateOrderPackOptions();
            orderPackSelect.required = true;
        } else {
            // 비납품: 비활성화 + 값 초기화 + required 제거
            orderPackSelect.required = false;
            orderPackSelect.disabled = true;
            orderPackSelect.innerHTML = "";
            const opt = document.createElement("option");
            opt.value = "";
            opt.textContent = "-- 납품일 때만 선택 가능합니다 --";
            orderPackSelect.appendChild(opt);
        }
    }
    
    // 이벤트 바인딩
    unitSelect.addEventListener("change", function () {
        // 납품 상태일 때만 단위 변경에 따라 리스트 갱신
        if (!orderPackSelect.disabled) populateOrderPackOptions();
    });

    isOrderSelect.addEventListener("change", function () {
        toggleOrderPackAvailability();
    });

    // 초기 상태 설정
    toggleOrderPackAvailability();
});
</script>

</body>
</html>
 