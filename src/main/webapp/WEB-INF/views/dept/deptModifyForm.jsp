<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부서 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">

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
        .card-detail {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        }
        .form-label { font-weight: 600; }

        /* 프로젝트 표준 버튼 */
        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: #fff !important;
        }
        .btn-brown-outline:hover {
            background-color: #ccc !important;  /* 회색 배경 */
            color: #333 !important;             /* 진회색 글자 */
            border-color: #ccc !important;      /* 회색 테두리 */
        }
        .btn-brown {
            background-color: var(--soft-brown) !important;
            color: #fff !important;
            border: 1px solid var(--soft-brown) !important;
        }
        .btn-brown:hover {
            background-color: var(--main-brown) !important;
            border-color: var(--main-brown) !important;
        }
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

        <!-- 본문 + 푸터 래퍼 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3" style="max-width: 980px;">
                    <div class="form-section-title">부서 수정</div>

                    <div class="card card-detail">
                        <div class="card-body">
                            <!-- ✅ 기능 유지: action / name / value 동일 -->
                            <form action="${pageContext.request.contextPath}/dept/deptUpdate" method="post" id="deptEditForm" novalidate>
                                <input type="hidden" name="dept_code" value="${deptDto.dept_code}">

                                <!-- 부서 이름 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">부서 이름</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" name="dept_name" value="${deptDto.dept_name}" required>
                                    </div>
                                </div>

                                <!-- 전화번호 (자동 하이픈) -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">전화번호</label>
                                    <div class="col-sm-8">
                                        <input type="tel" class="form-control" id="dept_tel" name="dept_tel" value="${deptDto.dept_tel}" required>
                                    </div>
                                </div>

                                <!-- 버튼: 오른쪽 정렬(모바일 중앙) -->
                                <div class="d-flex justify-content-end gap-2 mt-4">
                                    <button type="submit" class="btn btn-primary">수정 완료</button>
                                    <button type="button" class="btn btn-brown-outline"
                                            onclick="location.href='${pageContext.request.contextPath}/dept/deptList'">
                                        목록
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
        <!-- /본문 + 푸터 래퍼 -->
    </div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    // 부서 대표전화 자동 하이픈 (02 / 일반 국번 케이스 대응)
    const telInput = document.getElementById("dept_tel");
    if (telInput) {
        const formatTel = (v) => {
            v = v.replace(/[^0-9]/g, "");
            if (v.startsWith("02")) {
                if (v.length <= 2) return v;
                if (v.length <= 5) return v.replace(/(\d{2})(\d{1,3})/, "$1-$2");
                if (v.length <= 9) return v.replace(/(\d{2})(\d{3})(\d{1,4})/, "$1-$2-$3");
                return v.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
            } else {
                if (v.length <= 3) return v;
                if (v.length <= 7) return v.replace(/(\d{3})(\d{1,4})/, "$1-$2");
                return v.replace(/(\d{3})(\d{3,4})(\d{4})/, "$1-$2-$3");
            }
        };
        // 초기 로드 시 값 포맷
        telInput.value = formatTel(telInput.value || "");
        telInput.addEventListener("input", () => {
            telInput.value = formatTel(telInput.value);
        });
    }

    // 간단한 HTML5 유효성
    const form = document.getElementById("deptEditForm");
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
