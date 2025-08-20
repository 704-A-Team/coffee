<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 등록</title>
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
        padding-left: 12px; margin-bottom: 24px;
        font-weight: 700; font-size: 1.8rem; color: var(--main-brown);
    }
    .btn-brown { background-color: var(--soft-brown) !important; color: #fff !important; border: none !important; }
    .btn-brown:hover { background-color: var(--main-brown) !important; }
    .btn-brown-outline { border: 1px solid var(--main-brown) !important; color: var(--main-brown) !important; background-color: #fff !important; }
    .btn-brown-outline:hover { background-color:#ccc!important; color:#333!important; border-color:#ccc!important; }
    .btn-secondary-custom { background:#eee!important; color:#333!important; border:1px solid #ccc!important; }
    .btn-secondary-custom:hover { background:#ccc!important; }
    @media (max-width: 768px) { .d-flex.justify-content-end { justify-content: center !important; } }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3">
                    <div class="form-section-title">거래처 등록</div>

                    <!-- novalidate: 기본 브라우저 팝업 끄고 부트스트랩 스타일로 표시 -->
                    <form action="${pageContext.request.contextPath}/client/saveClient" method="post"
                          id="clientCreateForm" class="needs-validation" novalidate>
                        <!-- 거래처명 -->
                        <div class="mb-3">
                            <label class="form-label" for="client_name">거래처명</label>
                            <input type="text" id="client_name" name="client_name" class="form-control" required>
                            <div class="invalid-feedback">거래처명을 입력하세요.</div>
                        </div>

                        <!-- 사업자등록번호 -->
                        <div class="mb-3">
                            <label class="form-label" for="saup_num">사업자등록번호</label>
                            <!-- 숫자만 10자리, 자동 하이픈(###-##-#####) -->
                            <input type="text" id="saup_num" name="saup_num" class="form-control"
                                   inputmode="numeric" maxlength="12" required
                                   placeholder="000-00-00000">
                            <div class="invalid-feedback">올바른 사업자등록번호(10자리)를 입력하세요.</div>
                        </div>

                        <!-- 대표자명 -->
                        <div class="mb-3">
                            <label class="form-label" for="boss_name">대표자명</label>
                            <input type="text" id="boss_name" name="boss_name" class="form-control" required>
                            <div class="invalid-feedback">대표자명을 입력하세요.</div>
                        </div>

                        <!-- 거래처 유형 -->
                        <div class="mb-3">
                            <label class="form-label" for="client_type">거래처 유형</label>
                            <select id="client_type" name="client_type" class="form-select" required>
                                <option value="" selected disabled>선택하세요</option>
                                <option value="2">공급처</option>
                                <option value="3">가맹점</option>
                            </select>
                            <div class="invalid-feedback">거래처 유형을 선택하세요.</div>
                        </div>

                        <!-- 주소 -->
                        <div class="mb-3">
                            <label class="form-label" for="client_address">주소</label>
                            <input type="text" id="client_address" name="client_address" class="form-control" required>
                            <div class="invalid-feedback">주소를 입력하세요.</div>
                        </div>

                        <!-- 전화번호 -->
                        <div class="mb-3">
                            <label class="form-label" for="client_tel">전화번호</label>
                            <input type="text" class="form-control" id="client_tel" name="client_tel"
                                   inputmode="numeric" maxlength="13" required placeholder="010-1234-5678">
                            <div class="invalid-feedback">올바른 전화번호를 입력하세요.</div>
                        </div>

                        <!-- 담당 사원 (영업부서 1000만 노출) -->
                        <div class="mb-3">
                            <label class="form-label" for="client_emp_code">담당 사원</label>
                            <select id="client_emp_code" name="client_emp_code" class="form-select" required>
                                <option value="" selected disabled>선택하세요</option>
                                <c:forEach var="emp" items="${empList}">
                                    <c:if test="${emp.emp_dept_code == 1000}">
                                        <option value="${emp.emp_code}">[${emp.emp_code}] ${emp.emp_name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">담당 사원을 선택하세요.</div>
                        </div>

                        <!-- 버튼 영역 -->
                        <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                            <button type="submit" class="btn btn-primary">등록</button>
                            <button type="reset" class="btn btn-secondary-custom">초기화</button>
                            <button type="button" class="btn btn-brown-outline"
                                onclick="location.href='${pageContext.request.contextPath}/client/clientList'">
                                목록으로
                            </button>
                        </div>
                    </form>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    // ===== 전화번호 자동 하이픈 (02/지역번호 & 010 등 지원) =====
    const telInput = document.getElementById("client_tel");
    const formatTel = (v) => {
        v = v.replace(/[^0-9]/g, "");
        if (v.startsWith("02")) {
            if (v.length <= 2) return v;
            if (v.length <= 6) return v.replace(/(\d{2})(\d{1,4})/, "$1-$2");
            if (v.length <= 10) return v.replace(/(\d{2})(\d{3,4})(\d{1,4})/, "$1-$2-$3");
            return v.replace(/(\d{2})(\d{4})(\d{4}).*/, "$1-$2-$3");
        } else {
            if (v.length <= 3) return v;
            if (v.length <= 7)  return v.replace(/(\d{3})(\d{1,4})/, "$1-$2");
            return v.replace(/(\d{3})(\d{3,4})(\d{4}).*/, "$1-$2-$3");
        }
    };
    if (telInput) {
        telInput.addEventListener("input", () => {
            telInput.value = formatTel(telInput.value);
        });
    }

    // ===== 사업자등록번호 자동 하이픈 (###-##-#####) =====
    const saupInput = document.getElementById("saup_num");
    const formatSaup = (v) => {
        v = v.replace(/[^0-9]/g, "").slice(0, 10);
        if (v.length <= 3) return v;
        if (v.length <= 5) return v.replace(/(\d{3})(\d{1,2})/, "$1-$2");
        return v.replace(/(\d{3})(\d{2})(\d{1,5})/, "$1-$2-$3");
    };
    if (saupInput) {
        saupInput.addEventListener("input", () => {
            saupInput.value = formatSaup(saupInput.value);
        });
    }

    // ===== 부트스트랩 유효성 검사 =====
    const form = document.getElementById("clientCreateForm");
    form.addEventListener("submit", function (e) {
        // 추가적인 프론트 유효성: 사업자번호 10자리, 전화번호 하이픈 포함 12~13자 정도
        const rawSaup = saupInput.value.replace(/[^0-9]/g, "");
        if (rawSaup.length !== 10) saupInput.setCustomValidity("10자리를 입력하세요.");
        else saupInput.setCustomValidity("");

        if (!form.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }
        form.classList.add('was-validated');
    });
});
</script>

</body>
</html>
