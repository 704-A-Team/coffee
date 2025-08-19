<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
  :root{
    --main-brown:#6f4e37;
    --dark-brown:#4e342e;
    --soft-brown:#bfa08e;
    --danger-red:#a94442;
  }
  body{ background:#f9f5f1; font-family:'Segoe UI',sans-serif; }

  .form-container{ width:100%; max-width:980px; margin:0 auto; }
  .form-section-title{
    border-left:5px solid var(--main-brown);
    padding-left:12px;
    margin:6px 0 24px;
    font-weight:700;
    font-size:1.8rem;
    color:var(--dark-brown);
  }
  .card-detail{ background:#fff; border:1px solid #e0e0e0; box-shadow:0 4px 6px rgba(0,0,0,.05); }
  .card-header{ background:linear-gradient(180deg,#fff,#f4ebe3); border-bottom:1px solid #eadfd6; }
  .card-header .title{ color:var(--main-brown); font-weight:700; margin:0; }
  .col-form-label{ font-weight:600; color:#555; }
  .form-control{ border-color:#e6e0db; }
  .form-control:focus{ border-color:var(--soft-brown); box-shadow:0 0 0 .2rem rgba(111,78,55,.15); }

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

  .pw-hint li{ margin-bottom:4px; }
  .pw-hint .ok{ color:#198754; }
  .pw-hint .bad{ color:#a94442; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">
  <%@ include file="../header.jsp" %>

  <div class="d-flex flex-grow-1 content-row">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
      <main class="flex-grow-1 p-4 content-main">
        <div class="form-container">
          <div class="form-section-title">비밀번호 변경</div>

          <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">${success}</div>
          </c:if>
          <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
          </c:if>

          <div class="card card-detail shadow-sm">
            <div class="card-header">
              <h2 class="h5 title mb-0">비밀번호 재설정</h2>
            </div>

            <form id="changePwForm" action="${pageContext.request.contextPath}/MyPage/PasswordChanged"
                  method="post" class="needs-validation" novalidate>
              <div class="card-body">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <!-- 현재 비밀번호 -->
                <div class="row mb-3">
                  <label class="col-sm-3 col-form-label">현재 비밀번호</label>
                  <div class="col-sm-7">
                    <div class="input-group">
                      <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                      <button type="button" class="btn btn-outline-secondary" id="toggleCurrent">보기</button>
                    </div>
                  </div>
                </div>

                <!-- 새 비밀번호 -->
                <div class="row mb-3">
                  <label class="col-sm-3 col-form-label">새 비밀번호</label>
                  <div class="col-sm-7">
                    <div class="input-group">
                      <input type="password" class="form-control" id="newPassword" name="newPassword" minlength="8" required>
                      <button type="button" class="btn btn-outline-secondary" id="toggleNew">보기</button>
                    </div>
                    <div class="progress mt-2">
                      <div id="pwBar" class="progress-bar" style="width:0%"></div>
                    </div>
                    <ul class="mt-2 small pw-hint" id="pwHints">
                      <li id="ruleLen" class="bad">최소 8자 이상</li>
                      <li id="ruleUp"  class="bad">대문자 포함</li>
                      <li id="ruleLow" class="bad">소문자 포함</li>
                      <li id="ruleNum" class="bad">숫자 포함</li>
                    </ul>
                  </div>
                </div>

                <!-- 새 비밀번호 확인 -->
                <div class="row mb-3">
                  <label class="col-sm-3 col-form-label">비밀번호 확인</label>
                  <div class="col-sm-7">
                    <div class="input-group">
                      <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                      <button type="button" class="btn btn-outline-secondary" id="toggleConfirm">보기</button>
                    </div>
                    <div class="form-text" id="matchText"></div>
                  </div>
                </div>
              </div>

              <div class="card-footer bg-transparent d-flex justify-content-end gap-2">
                <a href="javascript:history.back()" class="btn btn-brown-outline">돌아가기</a>
                <button type="submit" class="btn btn-primary">비밀번호 변경</button>
              </div>
            </form>
          </div>
        </div>
      </main>
      <%@ include file="../footer.jsp" %>
    </div>
  </div>

<script>
  // 보기 토글
  const toggle = (btnId, inputId) => {
    document.getElementById(btnId).addEventListener('click', () => {
      const el = document.getElementById(inputId);
      el.type = (el.type === 'password') ? 'text' : 'password';
    });
  };
  toggle('toggleCurrent', 'currentPassword');
  toggle('toggleNew', 'newPassword');
  toggle('toggleConfirm', 'confirmPassword');

  // 강도 체크
  const newPw = document.getElementById('newPassword');
  const confirmPw = document.getElementById('confirmPassword');
  const pwBar = document.getElementById('pwBar');
  const matchText = document.getElementById('matchText');

  function setRule(id, ok) {
    const el = document.getElementById(id);
    el.classList.remove('ok','bad');
    el.classList.add(ok ? 'ok' : 'bad');
  }

  function evaluateStrength(v){
    const rules = {
      len: v.length >= 8,
      up:  /[A-Z]/.test(v),
      low: /[a-z]/.test(v),
      num: /[0-9]/.test(v)
    };
    setRule('ruleLen', rules.len);
    setRule('ruleUp',  rules.up);
    setRule('ruleLow', rules.low);
    setRule('ruleNum', rules.num);

    const score = Object.values(rules).filter(Boolean).length; // 0~4
    const percent = (score/4)*100;
    pwBar.style.width = percent + '%';
    pwBar.className = "progress-bar";
    if(score <= 1) pwBar.classList.add('bg-danger');
    else if(score == 2 || score == 3) pwBar.classList.add('bg-warning');
    else pwBar.classList.add('bg-success');
  }

  newPw.addEventListener('input', () => {
    evaluateStrength(newPw.value);
    if (confirmPw.value) updateMatch();
  });

  confirmPw.addEventListener('input', updateMatch);

  function updateMatch(){
    if (newPw.value && confirmPw.value && newPw.value === confirmPw.value) {
      matchText.textContent = "비밀번호가 일치합니다.";
      matchText.className = "text-success";
      confirmPw.setCustomValidity('');
    } else {
      matchText.textContent = "비밀번호가 일치하지 않습니다.";
      matchText.className = "text-danger";
      confirmPw.setCustomValidity('mismatch');
    }
  }
</script>
</body>
</html>
