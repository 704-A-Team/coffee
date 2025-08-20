<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    :root {
        --main-brown: #6f4e37;
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
        color: var(--main-brown);
    }

    /* 프로젝트 표준 버튼 */
    .btn-brown {
        background-color: var(--soft-brown) !important;
        color: white !important;
        border: none !important;
    }
    .btn-brown:hover {
        background-color: var(--main-brown) !important;
    }

    /* 목록/아웃라인 표준: hover 시 회색(#ccc) 배경, #333 글자, #ccc 테두리 */
    .btn-brown-outline {
        border: 1px solid var(--main-brown) !important;
        color: var(--main-brown) !important;
        background-color: white !important;
    }
    .btn-brown-outline:hover {
        background-color: #ccc !important;
        color: #333 !important;
        border-color: #ccc !important;
    }

    /* reset 전용(제품/거래처 폼과 동일) */
    .btn-secondary-custom {
        background:#eee!important;
        color:#333!important;
        border:1px solid #ccc!important;
    }
    .btn-secondary-custom:hover { background:#ccc!important; }

    @media (max-width: 768px) {
        .d-flex.justify-content-end { justify-content: center !important; }
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3" style="max-width: 980px;">
                    <div class="form-section-title">부서 등록</div>

                    <form action="${pageContext.request.contextPath}/dept/saveDept" method="post" id="deptForm" novalidate>
                        <!-- 부서명 -->
                        <div class="mb-3">
                            <label for="dept_name" class="form-label">부서명</label>
                            <input type="text" class="form-control" id="dept_name" name="dept_name"
                                   placeholder="부서이름을 입력하세요 (예: 개발팀)" required>
                        </div>

                        <!-- 부서 대표 전화 -->
                        <div class="mb-3">
                            <label for="dept_tel" class="form-label">부서 대표 전화</label>
                            <input type="text" class="form-control" id="dept_tel" name="dept_tel"
                                   placeholder="예: 02-333-1234" maxlength="13" required>
                        </div>

                        <!-- 버튼 영역: 우측 정렬(거래처 등록과 동일) -->
                        <div class="d-flex justify-content-end gap-2 mt-4 mb-5">
                            <button type="submit" class="btn btn-primary">등록</button>
                            <button type="reset" class="btn btn-secondary-custom">초기화</button>
                            <button type="button" class="btn btn-brown-outline"
                                    onclick="location.href='${pageContext.request.contextPath}/dept/deptList'">
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
    // 전화번호 자동 하이픈 (거래처 등록과 동일 로직)
    const telInput = document.getElementById("dept_tel");
    telInput.addEventListener("input", function () {
        let value = telInput.value.replace(/[^0-9]/g, "");
        if (value.length === 9) {
            value = value.replace(/(\d{2})(\d{3})(\d{4})/, "$1-$2-$3");
        } else if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
        } else if (value.length === 10) {
            if (value.startsWith("02")) {
                value = value.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
            } else {
                value = value.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
            }
        }
        telInput.value = value;
    });

    // 간단한 HTML5 유효성 검사
    const form = document.getElementById("deptForm");
    form.addEventListener("submit", function (e) {
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
