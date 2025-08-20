<!-- /login/findPassword.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    :root{
      --main-brown:#6f4e37;
      --dark-brown:#4e342e;
      --soft-brown:#bfa08e;
    }
    .form-container{ width:100%; max-width:520px; }
    .btn-brown-outline{
      border:1px solid var(--main-brown)!important;
      color:var(--main-brown)!important;
      background:#fff!important;
    }
    .btn-brown-outline:hover{
      background:#ccc!important; color:#333!important; border-color:#ccc!important;
    }
    .btn-brown{
      background:var(--main-brown)!important; border-color:var(--main-brown)!important; color:#fff!important;
    }
    .btn-brown:hover{
      background:#5f4231!important; border-color:#5f4231!important;
    }
    .form-control:focus{
      border-color:var(--soft-brown);
      box-shadow:0 0 0 .2rem rgba(111,78,55,.15);
    }
  </style>
</head>

<body class="d-flex flex-column min-vh-100">
  <!-- header -->
  <div id="header"><%@ include file="../header.jsp" %></div>

  <!-- content row: sidebar + (main + footer) -->
  <div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

    <!-- 오른쪽 컬럼(메인+푸터) -->
    <div class="d-flex flex-column flex-grow-1">
      <!-- 메인: 카드 가운데 정렬 -->
      <main class="flex-grow-1 p-4 d-flex align-items-center justify-content-center">
        <div class="form-container">
          <c:if test="${not empty msg}">
            <div class="alert alert-info">${msg}</div>
          </c:if>
          <c:if test="${not empty err}">
            <div class="alert alert-danger">${err}</div>
          </c:if>

          <div class="card shadow-sm">
            <div class="card-header text-white" style="background:var(--main-brown);">
              <h2 class="h5 mb-0">비밀번호 찾기</h2>
            </div>

            <div class="card-body">
              <form action="<c:url value='/login/findPassword/request'/>" method="post" class="needs-validation" novalidate>
                <c:if test="${_csrf != null}">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </c:if>

                <!-- 사원 코드번호 -->
						<div class="mb-3">
						  <label for="code" class="form-label">코드번호</label>
						  <input type="text"
						         name="code" id="code"
						         class="form-control"
						         required
						         inputmode="numeric"
						         placeholder="사원 코드번호를 입력해주세요.">
						  <div class="invalid-feedback">코드번호(숫자) 를 입력하세요.</div>
						</div>

                <!-- 이름 -->
                <div class="mb-3">
                  <label for="name" class="form-label">이름</label>
                  <input type="text" name="name" id="name" class="form-control" required placeholder="이름을 입력해주세요.">
                  <div class="invalid-feedback">이름을 입력하세요.</div>
                </div>

                <!-- 등록 전화번호 -->
                <div class="mb-3">
                  <label for="emp_tel" class="form-label">등록 전화번호</label>
                  <input type="text" id="emp_tel" name="phone" class="form-control" required placeholder="전화번호를 입력해주세요.">
                  <div class="invalid-feedback">전화번호를 입력하세요.</div>
                </div>

                <div class="d-flex justify-content-between">
                  <a href="javascript:history.back();" class="btn btn-brown-outline">이전으로</a>
                  <button type="submit" class="btn btn-brown">이메일로 비밀번호 전송</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </main>

      <!-- footer -->
      <div id="footer"><%@ include file="../footer.jsp" %></div>
    </div>
  </div>

  <script>
    // Bootstrap 폼 검증
    document.querySelectorAll('.needs-validation').forEach(f=>{
      f.addEventListener('submit', e=>{
        if(!f.checkValidity()){ e.preventDefault(); e.stopPropagation(); }
        f.classList.add('was-validated');
      });
    });

    // 코드번호 숫자만 입력
    (function(){
      const code = document.getElementById('code');
      if (!code) return;
      const onlyDigits = v => (v||'').replace(/\D/g,'');
      code.addEventListener('input', ()=> { code.value = onlyDigits(code.value); });
      code.addEventListener('paste', ()=> { setTimeout(()=>{ code.value = onlyDigits(code.value); }); });
    })();

    // 전화번호 자동 하이픈 포맷
    (function(){
      const telInput = document.getElementById('emp_tel');
      if (!telInput) return;

      const formatTel = (v) => {
        v = (v || '').replace(/[^0-9]/g, '');
        if (v.startsWith('02')) {
          if (v.length <= 2) return v;
          if (v.length <= 5) return v.replace(/(\d{2})(\d{1,3})/, '$1-$2');
          if (v.length <= 9) return v.replace(/(\d{2})(\d{3})(\d{1,4})/, '$1-$2-$3');
          return v.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
        } else {
          if (v.length <= 3) return v;
          if (v.length <= 7) return v.replace(/(\d{3})(\d{1,4})/, '$1-$2');
          return v.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
        }
      };

      telInput.value = formatTel(telInput.value);
      telInput.addEventListener('input', () => { telInput.value = formatTel(telInput.value); });
      telInput.addEventListener('paste', () => { setTimeout(() => { telInput.value = formatTel(telInput.value); }); });
    })();
  </script>
</body>
</html>
