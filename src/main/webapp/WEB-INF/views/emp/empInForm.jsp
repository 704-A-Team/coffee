<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

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
        color: var(--main-brown);
    }

    .card-detail {
        background-color: #ffffff;
        border: 1px solid #e0e0e0;
        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
    }

    /* 프로젝트 표준 버튼 */
    .btn-brown-outline {
        border: 1px solid var(--main-brown) !important;
        color: var(--main-brown) !important;
        background-color: #fff !important;
    }
    .btn-brown-outline:hover {
        background-color: #ccc !important;   /* 회색 배경 */
        color: #333 !important;               /* 진회색 글자 */
        border-color: #ccc !important;        /* 회색 테두리 */
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

        <!-- 본문 + 푸터 같은 컬럼 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3" style="max-width: 980px;">
                    <div class="form-section-title">사원 등록</div>

                    <div class="card card-detail">
                        <div class="card-body">
                            <!-- ✅ 기능 유지: action / name / id 동일 -->
                            <form action="${pageContext.request.contextPath}/emp/saveEmp" method="post" id="empCreateForm" novalidate>
                                <!-- 사원명 -->
                                <div class="mb-3">
                                    <label for="emp_name" class="form-label">이름</label>
                                    <input type="text" class="form-control" id="emp_name" name="emp_name" required>
                                </div>

                                <!-- 전화번호 (자동 하이픈) -->
                                <div class="mb-3">
                                    <label for="emp_tel" class="form-label">전화번호</label>
                                    <input type="tel" class="form-control" id="emp_tel" name="emp_tel" maxlength="13" required>
                                </div>

                                <!-- 소속 부서 -->
                                <div class="mb-3">
                                    <label for="emp_dept_code" class="form-label">소속 부서</label>
                                    <select class="form-select" id="emp_dept_code" name="emp_dept_code">
                                        <option value="1000">영업팀</option>
                                        <option value="1001">생산관리팀</option>
                                        <option value="1002">재고팀</option>
                                        <option value="1003">인사팀</option>
                                        <option value="1004">구매팀</option>
                                        <option value="1005">판매팀</option>
                                        <option value="1006">경영팀</option>
                                    </select>
                                </div>

                                <!-- 직급 -->
                                <div class="mb-3">
                                    <label for="emp_grade" class="form-label">직급</label>
                                    <select class="form-select" id="emp_grade" name="emp_grade">
                                        <option value="0">사원</option>
                                        <option value="1">과장</option>
                                        <option value="2">부장</option>
                                        <option value="3">이사</option>
                                        <option value="4">사장</option>
                                    </select>
                                </div>

                                <!-- 급여 -->
                                <div class="mb-3">
                                    <label for="emp_sal" class="form-label">급여</label>
                                    <input type="number" class="form-control" id="emp_sal" name="emp_sal" required>
                                </div>

                                <!-- 이메일 -->
                                <div class="mb-3">
                                    <label for="emp_email" class="form-label">이메일</label>
                                    <input type="email" class="form-control" id="emp_email" name="emp_email" required>
                                </div>

                                <!-- 생일 -->
                                <div class="mb-3">
                                    <label for="emp_birth" class="form-label">생일</label>
                                    <input type="date" class="form-control" id="emp_birth" name="emp_birth" required>
                                </div>

                                <!-- 입사일 -->
                                <div class="mb-3">
                                    <label for="emp_ipsa_date" class="form-label">입사일</label>
                                    <input type="date" class="form-control" id="emp_ipsa_date" name="emp_ipsa_date" required>
                                </div>

                                <!-- 버튼: 오른쪽 정렬(모바일 중앙) -->
                                <div class="d-flex justify-content-end gap-2 mt-4">
                                    <button type="submit" class="btn btn-primary">등록</button>
                                    <button type="reset" class="btn btn-secondary-custom">초기화</button>
                                    <button type="button" class="btn btn-brown-outline"
                                            onclick="location.href='${pageContext.request.contextPath}/emp/empList'">
                                        목록으로
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    // 전화번호 자동 하이픈 (02와 010 등 이동통신/지역번호 모두 처리)
    const telInput = document.getElementById("emp_tel");
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
        telInput.addEventListener("input", () => {
            telInput.value = formatTel(telInput.value);
        });
    }

    // 간단한 HTML5 유효성
    const form = document.getElementById("empCreateForm");
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
