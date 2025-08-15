<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 정보 수정</title>
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

        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: #fff !important;
        }
        .btn-brown-outline:hover {
            background-color: #ccc !important;
            color: #333 !important;
            border-color: #ccc !important;
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

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3" style="max-width: 980px;">
                    <div class="form-section-title">사원 정보 수정</div>

                    <div class="card card-detail">
                        <div class="card-body">
                            <!-- 액션/네임/EL 그대로 유지 -->
                            <form action="${pageContext.request.contextPath}/emp/empUpdate" method="post" novalidate>
                                <input type="hidden" name="emp_code" value="${empDto.emp_code}" />

                                <!-- 사원 이름 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">사원 이름</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" name="emp_name" value="${empDto.emp_name}" required />
                                    </div>
                                </div>

                                <!-- 전화번호 (자동 하이픈) -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">전화번호</label>
                                    <div class="col-sm-8">
                                        <input type="tel" class="form-control" id="emp_tel" name="emp_tel" value="${empDto.emp_tel}" required />
                                    </div>
                                </div>

                                <!-- 소속 부서 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">소속 부서</label>
                                    <div class="col-sm-8">
                                        <select class="form-select" name="emp_dept_code" required>
                                            <option value="1000" ${empDto.emp_dept_code == 1000 ? 'selected' : ''}>영업팀</option>
                                            <option value="1001" ${empDto.emp_dept_code == 1001 ? 'selected' : ''}>생산관리팀</option>
                                            <option value="1002" ${empDto.emp_dept_code == 1002 ? 'selected' : ''}>재고팀</option>
                                            <option value="1003" ${empDto.emp_dept_code == 1003 ? 'selected' : ''}>인사팀</option>
                                            <option value="1004" ${empDto.emp_dept_code == 1004 ? 'selected' : ''}>구매팀</option>
                                            <option value="1005" ${empDto.emp_dept_code == 1005 ? 'selected' : ''}>판매팀</option>
                                            <option value="1006" ${empDto.emp_dept_code == 1006 ? 'selected' : ''}>경영팀</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- 직급 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">직급</label>
                                    <div class="col-sm-8">
                                        <select class="form-select" name="emp_grade" required>
                                            <option value="0" ${empDto.emp_grade == 0 ? 'selected' : ''}>사원</option>
                                            <option value="1" ${empDto.emp_grade == 1 ? 'selected' : ''}>과장</option>
                                            <option value="2" ${empDto.emp_grade == 2 ? 'selected' : ''}>부장</option>
                                            <option value="3" ${empDto.emp_grade == 3 ? 'selected' : ''}>이사</option>
                                            <option value="4" ${empDto.emp_grade == 4 ? 'selected' : ''}>사장</option>
                                            <option value="5" ${empDto.emp_grade == 5 ? 'selected' : ''}>시스템</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- 급여 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">급여</label>
                                    <div class="col-sm-8">
                                        <input type="number" class="form-control" name="emp_sal" value="${empDto.emp_sal}" required />
                                    </div>
                                </div>

                                <!-- 이메일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">이메일</label>
                                    <div class="col-sm-8">
                                        <input type="email" class="form-control" name="emp_email" value="${empDto.emp_email}" required />
                                    </div>
                                </div>

                                <!-- 생일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">생일</label>
                                    <div class="col-sm-8">
                                        <input type="date" class="form-control" name="emp_birth" value="${empDto.emp_birth}" required />
                                    </div>
                                </div>

                                <!-- 입사일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">입사일</label>
                                    <div class="col-sm-8">
                                        <input type="date" class="form-control" name="emp_ipsa_date" value="${empDto.emp_ipsa_date}" required />
                                    </div>
                                </div>

                                <!-- 버튼 -->
                                <div class="d-flex justify-content-end gap-2 mt-4">
                                    <button type="submit" class="btn btn-primary">수정 완료</button>
                                    <button type="button" class="btn btn-brown-outline"
                                        onclick="location.href='${pageContext.request.contextPath}/emp/empList'">
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
    </div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    // 전화번호 자동 하이픈: 02, 010/011/016/017/018/019 등 대응
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
        // 초기 로드 시 값도 포맷
        telInput.value = formatTel(telInput.value || "");
        telInput.addEventListener("input", () => {
            const start = telInput.selectionStart;
            telInput.value = formatTel(telInput.value);
            // 커서 위치 보정은 생략 (필요하면 추가 가능)
        });
    }
});
</script>
</body>
</html>
