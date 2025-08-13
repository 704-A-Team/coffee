<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품(원재료) 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
        }
        body {
            background-color: #f9f5f1;
            font-family: 'Segoe UI', sans-serif;
        }
        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }
        .image-thumb {
            width: 110px;
            height: auto;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .btn-brown {
            background-color: var(--soft-brown) !important;
            color: white !important;
            border: none !important;
        }
        .btn-brown:hover {
            background-color: var(--main-brown) !important;
        }
        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: #fff !important;
        }
        .btn-brown-outline:hover {
            background-color: var(--main-brown) !important;
            color: white !important;
        }
        .btn-soft-danger {
            background-color: var(--danger-red) !important;
            color: white !important;
            border: none !important;
        }
        .btn-soft-danger:hover {
            background-color: #922d2b !important;
        }
        @media (max-width: 768px) {
            .d-flex.justify-content-end {
                justify-content: center !important;
            }
        }
        .btn-secondary-custom {
		    background:#eee!important;      /* 밝은 회색 배경 */
		    color:#333!important;            /* 진회색 글자 */
		    border:1px solid #ccc!important; /* 연한 회색 테두리 */
		}
		.btn-secondary-custom:hover {
		    background:#ccc!important;       /* hover 시 진한 회색 */
		}
		
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">제품(원재료) 수정</div>

                <form action="${pageContext.request.contextPath}/sw/wonProductModify" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="product_code" value="${wonProductDetail.product_code}" />
                    
                    <!-- 제품명 -->
                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" value="${wonProductDetail.product_name}" required>
                    </div>

                    <!-- 셀렉트박스 row -->
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">단위</label>
                            <select name="product_unit" id="product_unit" class="form-select" required>
                                <option value="0" ${wonProductDetail.product_unit == 0 ? 'selected' : ''}>ea</option>
                                <option value="1" ${wonProductDetail.product_unit == 1 ? 'selected' : ''}>g</option>
                                <option value="2" ${wonProductDetail.product_unit == 2 ? 'selected' : ''}>ml</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">제품유형</label>
                            <select name="product_type" class="form-select" required>
                                <option value="0" ${wonProductDetail.product_type == 0 ? 'selected' : ''}>원재료</option>
                                <option value="1" ${wonProductDetail.product_type == 1 ? 'selected' : ''}>완제품</option>
                            </select>
                        </div>
                        
                        <!-- 납품 유무 -->
						<div class="col-md-4">
						    <label class="form-label">납품 유무</label>
						    <select name="product_isorder" id="product_isorder" class="form-select" required>
						        <option value="0" ${wonProductDetail.product_isorder == 0 ? 'selected' : ''}>납품</option>
						        <option value="1" ${wonProductDetail.product_isorder == 1 ? 'selected' : ''}>비납품</option>
						    </select>
						</div>
                    </div>

                    <!-- 중량 -->
                    <div class="mb-3">
                        <label class="form-label">기본 중량 (g)</label>
                        <input type="number" class="form-control" name="product_weight" value="${wonProductDetail.product_weight}" required>
                    </div>

                    <!-- 판매 단위 -->
                    <div class="mb-3">
                        <label class="form-label">판매단위</label>
                        <select class="form-select" id="product_order_pack" name="product_order_pack" required>
                            <!-- JS에서 옵션 생성 -->
                        </select>
                    </div>

					<!-- 적정수량 -->
                    <div class="mb-3">
                        <label class="form-label">적정수량</label>
                        <input type="number" class="form-select" id="product_min_amount" name="product_min_amount" value="${wonProductDetail.product_min_amount}" required>
                    </div>

                    <!-- 삭제 구분 -->
                    <div class="mb-4">
                        <label class="form-label">삭제 구분</label>
                        <select name="product_isdel" class="form-select">
                            <option value="0" ${wonProductDetail.product_isdel == 0 ? 'selected' : ''}>0</option>
                            <option value="1" ${wonProductDetail.product_isdel == 1 ? 'selected' : ''}>1</option>
                        </select>
                    </div>

                    <!-- 이미지 -->
                    <div class="mb-3">
                        <label class="form-label">제품 이미지 (기존 + 새 최대 3개)</label>
                        <div id="existingFiles" class="mb-3 d-flex flex-wrap gap-3">
                            <c:forEach var="img" items="${wonProductDetail.wonImgList}" varStatus="status">
                                <div class="d-flex flex-column align-items-center existing-file" data-filename="${img.file_name}">
                                    <img src="${pageContext.request.contextPath}/upload/${img.file_name}" alt="img${status.index}" class="image-thumb mb-1">
                                    <button type="button" class="btn btn-sm btn-soft-danger remove-existing">삭제</button>
                                </div>
                            </c:forEach>
                        </div>
                        <input type="file" id="newFiles" name="files" multiple class="form-control" accept=".jpg,.jpeg,.png">
                        <div class="form-text text-danger mt-1">※ 기존 + 새 이미지 포함 최대 3개까지 등록 가능합니다.</div>
                    </div>

                    <c:forEach var="img" items="${wonProductDetail.wonImgList}">
                        <input type="hidden" name="uploadFileNames" value="${img.file_name}" />
                    </c:forEach>

                    <!-- 버튼 영역: 오른쪽 하단 정렬 -->
                    <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/sw/wonProductDetail?product_code=${wonProductDetail.product_code}" class="btn btn-secondary-custom">취소</a>

                    </div>
                </form>
            </div>
        </main>

        <%@ include file="../../footer.jsp" %>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const unitSelect = document.getElementById("product_unit");
    const orderPackSelect = document.getElementById("product_order_pack");
    const isOrderSelect = document.getElementById("product_isorder");

    // ⭐ 백엔드에서 넘긴 기존 값
    const selectedPack = "${wonProductDetail.product_order_pack}";

    // 판매단위 옵션 구성
    function populateOrderPack(unitValue) {
        orderPackSelect.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = '';
        defaultOption.textContent = '-- 선택 --';
        orderPackSelect.appendChild(defaultOption);

        if (unitValue === "0") { // ea
            for (let i = 10; i <= 100; i += 10) {
                const opt = document.createElement("option");
                opt.value = i;
                opt.textContent = i + ' ea';
                if (String(i) === selectedPack) opt.selected = true;
                orderPackSelect.appendChild(opt);
            }
        } else if (unitValue === "1" || unitValue === "2") { // g or ml
            for (let i = 100; i <= 1000; i += 100) {
                const opt = document.createElement("option");
                opt.value = i;
                opt.textContent = i + (unitValue === "1" ? ' g' : ' ml');
                if (String(i) === selectedPack) opt.selected = true;
                orderPackSelect.appendChild(opt);
            }
        }
    }

    // 납품 여부에 따라 판매단위 사용 가능/불가 전환
    function toggleOrderPackAvailability() {
        const isOrder = isOrderSelect.value; // "0": 납품, "1": 비납품
        if (isOrder === "0") {
            // 납품: 활성화 + required + 옵션 구성
            orderPackSelect.disabled = false;
            orderPackSelect.required = true;
            populateOrderPack(unitSelect.value);
        } else {
            // 비납품: 비활성화 + required 제거 + 안내 옵션만
            orderPackSelect.required = false;
            orderPackSelect.disabled = true;
            orderPackSelect.innerHTML = "";
            const opt = document.createElement("option");
            opt.value = "";
            opt.textContent = "-- 납품일 때만 선택 가능합니다 --";
            orderPackSelect.appendChild(opt);
        }
    }

    // 최초 로딩 시 상태 반영
    toggleOrderPackAvailability();

    // 단위 변경 시 (활성 상태일 때만) 옵션 갱신
    unitSelect.addEventListener("change", function () {
        if (!orderPackSelect.disabled) populateOrderPack(this.value);
    });

    // 납품 여부 변경 시 활성/비활성 전환
    isOrderSelect.addEventListener("change", toggleOrderPackAvailability);

    // =======================
    // 이미지 삭제/업로드 3개 제한 (기존 로직 유지)
    // =======================
    const fileInput = document.querySelector("#newFiles");
    const existingFilesContainer = document.querySelector("#existingFiles");
    const form = document.querySelector("form");

    existingFilesContainer.addEventListener("click", function (e) {
        if (e.target.classList.contains("remove-existing")) {
            const fileDiv = e.target.closest(".existing-file");
            fileDiv.remove();
        }
    });

    fileInput.addEventListener("change", function () {
        const existingCount = document.querySelectorAll("#existingFiles .existing-file").length;
        const newCount = this.files.length;
        if (existingCount + newCount > 3) {
            alert("기존 이미지와 새 이미지 합산 최대 3개까지만 가능합니다.");
            this.value = "";
        }
    });

    form.addEventListener("submit", function () {
        // 삭제되지 않고 남아있는 기존 파일명을 hidden으로 재생성해서 전송
        document.querySelectorAll("input[name='uploadFileNames']").forEach(e => e.remove());
        document.querySelectorAll(".existing-file").forEach(fileDiv => {
            const filename = fileDiv.getAttribute("data-filename");
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "uploadFileNames";
            input.value = filename;
            form.appendChild(input);
        });
    });
});
</script>



</body>
</html>
