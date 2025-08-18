<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보 수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
  :root{
    --main-brown:#6f4e37;
    --dark-brown:#4e342e;
    --soft-brown:#bfa08e;
    --danger-red:#a94442;
  }
  body{
    background:#f9f5f1;
    font-family:'Segoe UI',sans-serif;
  }

  .form-container{ width:100%; max-width:980px; margin:0 auto; }
  .form-section-title{
    border-left:5px solid var(--main-brown);
    padding-left:12px;
    margin:6px 0 24px;
    font-weight:700;
    font-size:1.8rem;
    color:var(--dark-brown);
  }
  .card-detail{
    background:#fff;
    border:1px solid #e0e0e0;
    box-shadow:0 4px 6px rgba(0,0,0,.05);
  }
  .card-header{
    background:linear-gradient(180deg,#fff,#f4ebe3);
    border-bottom:1px solid #eadfd6;
  }
  .card-header .title{ color:var(--main-brown); font-weight:700; margin:0; }
  .col-form-label{ font-weight:600; color:#555; }

  .btn-brown-outline{
    border:1px solid var(--main-brown) !important;
    color:var(--main-brown) !important;
    background-color:#fff !important;
  }
  .btn-brown-outline:hover{
    background-color:#ccc !important;
    color:#333 !important;
    border-color:#ccc !important;
  }

  /* 🔒 읽기전용/비활성 회색 처리 */
  .form-control[readonly],
  .form-control:disabled,
  .form-select:disabled{
    background-color:#eee !important;
    color:#6c757d !important;
    border-color:#dee2e6 !important;
    cursor:not-allowed;
    opacity:1 !important;
  }
  /* date 입력기의 달력 아이콘도 흐리게 */
  input[type="date"][readonly]::-webkit-calendar-picker-indicator{
    filter:grayscale(1);
    opacity:.6;
    pointer-events:none;
  }

  .content-row{ min-height:0; }
  .content-main{ min-width:0; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">
  <%@ include file="../header.jsp" %>

  <div class="d-flex flex-grow-1 content-row">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
      <main class="flex-grow-1 p-4 content-main">
        <div class="form-container">
          <div class="form-section-title">내 정보 수정</div>

          <div class="card card-detail shadow-sm">
            <div class="card-header">
              <h2 class="h5 title">사원 정보 수정</h2>
            </div>

            <div class="card-body">
              <form action="${pageContext.request.contextPath}/MyPage/UserUpdate" method="post" novalidate>
                <c:if test="${not empty _csrf}">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </c:if>

                <!-- 사원 번호 (PK, 회색/비활성) -->
                <input type="hidden" name="emp_code" value="${empDto.emp_code}"/>
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">사원 번호</label>
                  <div class="col-sm-8">
                    <input type="text" class="form-control" value="${empDto.emp_code}" disabled>
                  </div>
                </div>

                <!-- 사원 이름 (수정 가능) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">사원 이름</label>
                  <div class="col-sm-8">
                    <input type="text" class="form-control" name="emp_name" value="${empDto.emp_name}" required />
                  </div>
                </div>

                <!-- 전화번호 (수정 가능 + 자동 하이픈) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">전화번호</label>
                  <div class="col-sm-8">
                    <input type="tel" class="form-control" id="emp_tel" name="emp_tel" value="${empDto.emp_tel}" required />
                  </div>
                </div>

                <!-- 소속 부서 (수정 불가: select disabled + hidden 제출) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">소속 부서</label>
                  <div class="col-sm-8">
                    <select class="form-select" disabled>
                      <option value="1000" ${empDto.emp_dept_code == 1000 ? 'selected' : ''}>영업팀</option>
                      <option value="1001" ${empDto.emp_dept_code == 1001 ? 'selected' : ''}>생산관리팀</option>
                      <option value="1002" ${empDto.emp_dept_code == 1002 ? 'selected' : ''}>재고팀</option>
                      <option value="1003" ${empDto.emp_dept_code == 1003 ? 'selected' : ''}>인사팀</option>
                      <option value="1004" ${empDto.emp_dept_code == 1004 ? 'selected' : ''}>구매팀</option>
                      <option value="1005" ${empDto.emp_dept_code == 1005 ? 'selected' : ''}>판매팀</option>
                      <option value="1006" ${empDto.emp_dept_code == 1006 ? 'selected' : ''}>경영팀</option>
                    </select>
                    <input type="hidden" name="emp_dept_code" value="${empDto.emp_dept_code}"/>
                  </div>
                </div>

                <!-- 직급 (수정 불가: select disabled + hidden 제출) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">직급</label>
                  <div class="col-sm-8">
                    <select class="form-select" disabled>
                      <option value="0" ${empDto.emp_grade == 0 ? 'selected' : ''}>사원</option>
                      <option value="1" ${empDto.emp_grade == 1 ? 'selected' : ''}>과장</option>
                      <option value="2" ${empDto.emp_grade == 2 ? 'selected' : ''}>부장</option>
                      <option value="3" ${empDto.emp_grade == 3 ? 'selected' : ''}>이사</option>
                      <option value="4" ${empDto.emp_grade == 4 ? 'selected' : ''}>사장</option>
                      <option value="5" ${empDto.emp_grade == 5 ? 'selected' : ''}>시스템</option>
                    </select>
                    <input type="hidden" name="emp_grade" value="${empDto.emp_grade}"/>
                  </div>
                </div>

                <!-- 급여 (수정 불가: disabled + hidden 제출) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">급여</label>
                  <div class="col-sm-8">
                    <input type="number" class="form-control" value="${empDto.emp_sal}" disabled />
                    <input type="hidden" name="emp_sal" value="${empDto.emp_sal}" />
                  </div>
                </div>

                <!-- 이메일 (수정 가능) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">이메일</label>
                  <div class="col-sm-8">
                    <input type="email" class="form-control" name="emp_email" value="${empDto.emp_email}" required />
                  </div>
                </div>

                <!-- 생년월일 (수정 불가: readonly) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">생년월일</label>
                  <div class="col-sm-8">
                    <input type="date" class="form-control" name="emp_birth" value="${empDto.empBirthFormatted}" readonly />
                  </div>
                </div>

                <!-- 입사일 (수정 불가: readonly) -->
                <div class="row mb-3">
                  <label class="col-sm-2 col-form-label">입사일</label>
                  <div class="col-sm-8">
                    <input type="date" class="form-control" name="emp_ipsa_date" value="${empDto.empIpsaDateFormatted}" readonly />
                  </div>
                </div>

                <div class="d-flex justify-content-end gap-3 mt-4">
                  <button type="submit" class="btn btn-primary">수정 완료</button>                  
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
  const telInput = document.getElementById("emp_tel");
  if (!telInput) return;

  const formatTel = (v) => {
    v = (v || "").replace(/[^0-9]/g, "");
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

  // 초기 포맷
  telInput.value = formatTel(telInput.value);

  // 입력 시 포맷
  telInput.addEventListener("input", () => {
    telInput.value = formatTel(telInput.value);
  });

  // 붙여넣기 시 포맷
  telInput.addEventListener("paste", () => {
    setTimeout(() => { telInput.value = formatTel(telInput.value); });
  });
});
</script>
</body>
</html>
