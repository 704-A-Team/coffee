<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 정보 수정</title>
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

        /* 보조 회색 버튼(폼 취소 등) */
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
                <div class="form-section-title">거래처 정보 수정</div>

                <div class="card card-detail">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/client/clientUpdate" method="post" id="clientEditForm" novalidate>
                            <input type="hidden" name="client_code" value="${clientDto.client_code}" />

                            <!-- 거래처명 -->
                            <div class="row mb-3">
                                <label for="client_name" class="col-sm-2 col-form-label">거래처명</label>
                                <div class="col-sm-8">
                                    <input type="text" id="client_name" class="form-control" name="client_name" value="${clientDto.client_name}" required />
                                </div>
                            </div>

                            <!-- 사업자등록번호 -->
                            <div class="row mb-3">
                                <label for="saup_num" class="col-sm-2 col-form-label">사업자등록번호</label>
                                <div class="col-sm-8">
                                    <input type="text" id="saup_num" class="form-control" name="saup_num" value="${clientDto.saup_num}" required />
                                </div>
                            </div>

                            <!-- 대표자명 -->
                            <div class="row mb-3">
                                <label for="boss_name" class="col-sm-2 col-form-label">대표자명</label>
                                <div class="col-sm-8">
                                    <input type="text" id="boss_name" class="form-control" name="boss_name" value="${clientDto.boss_name}" required />
                                </div>
                            </div>

                            <!-- 거래처 유형 -->
                            <div class="row mb-3">
                                <label for="client_type" class="col-sm-2 col-form-label">거래처 유형</label>
                                <div class="col-sm-8">
                                    <select class="form-select" id="client_type" name="client_type" required>
                                        <option value="2" <c:if test="${clientDto.client_type == 2}">selected</c:if>>공급처</option>
                                        <option value="3" <c:if test="${clientDto.client_type == 3}">selected</c:if>>가맹점</option>
                                    </select>
                                </div>
                            </div>

                            <!-- 주소 -->
                            <div class="row mb-3">
                                <label for="client_address" class="col-sm-2 col-form-label">주소</label>
                                <div class="col-sm-8">
                                    <input type="text" id="client_address" class="form-control" name="client_address" value="${clientDto.client_address}" required />
                                </div>
                            </div>

                            <!-- 전화번호 -->
                            <div class="row mb-3">
                                <label for="client_tel" class="col-sm-2 col-form-label">전화번호</label>
                                <div class="col-sm-8">
                                    <input type="tel" id="client_tel" class="form-control" name="client_tel" value="${clientDto.client_tel}" required />
                                </div>
                            </div>

                            <!-- 담당 사원 -->
                            <div class="row mb-3">
                                <label for="client_emp_code" class="col-sm-2 col-form-label">담당 사원</label>
                                <div class="col-sm-8">
                                    <select id="client_emp_code" name="client_emp_code" class="form-select" required>
                                        <c:forEach var="emp" items="${empList}">
                                            <c:if test="${emp.emp_dept_code == 1000}">
                                                <option value="${emp.emp_code}" <c:if test="${clientDto.client_emp_code == emp.emp_code}">selected</c:if>>
                                                    [${emp.emp_code}] ${emp.emp_name}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <!-- 상태 -->
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label">상태</label>
                                <div class="col-sm-8 pt-2">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="client_status" id="status0" value="0"
                                               <c:if test="${clientDto.client_status == 0}">checked</c:if>>
                                        <label class="form-check-label" for="status0">영업중</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="client_status" id="status1" value="1"
                                               <c:if test="${clientDto.client_status == 1}">checked</c:if>>
                                        <label class="form-check-label" for="status1">휴업중</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="client_status" id="status2" value="2"
                                               <c:if test="${clientDto.client_status == 2}">checked</c:if>>
                                        <label class="form-check-label" for="status2">폐점</label>
                                    </div>
                                </div>
                            </div>

                            <!-- 버튼: 카드 밖과 동일하게 오른쪽 정렬 -->
                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <button type="submit" class="btn btn-primary">수정 완료</button>
                                <!-- 목록/취소는 프로젝트 표준 회색 hover 규칙 사용 -->
                                <a href="${pageContext.request.contextPath}/client/clientList" class="btn btn-brown-outline">목록</a>
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
    // 전화번호 포맷팅(숫자만 입력 -> 하이픈 자동)
    const telInput = document.getElementById("client_tel");
    if (telInput) {
        const formatTel = (v) => {
            v = v.replace(/[^0-9]/g, "");
            // 02 국번, 010/011/016/017/018/019 처리
            if (v.startsWith("02")) {
                if (v.length > 2 && v.length <= 5) return v.replace(/(\d{2})(\d{1,3})/, "$1-$2");
                if (v.length > 5 && v.length <= 9) return v.replace(/(\d{2})(\d{3})(\d{1,4})/, "$1-$2-$3");
                if (v.length > 9) return v.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
            } else {
                if (v.length <= 3) return v;
                if (v.length <= 7) return v.replace(/(\d{3})(\d{1,4})/, "$1-$2");
                return v.replace(/(\d{3})(\d{3,4})(\d{4})/, "$1-$2-$3");
            }
        };
        telInput.addEventListener("input", () => {
            const cur = telInput.selectionStart;
            telInput.value = formatTel(telInput.value);
            // 커서 보정은 생략(필요 시 추가)
        });
    }

    // 간단한 HTML5 유효성 검사
    const form = document.getElementById("clientEditForm");
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
