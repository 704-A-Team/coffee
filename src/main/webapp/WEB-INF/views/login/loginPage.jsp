<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root{
            --main-brown:#6f4e37;
            --dark-brown:#4e342e;
            --soft-brown:#bfa08e;
        }
        .form-container{ width:100%; max-width:480px; } /* 카드 너비 */
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
        .btn-brown{
            background-color: var(--main-brown) !important;
            border-color: var(--main-brown) !important;
            color:#fff !important;
        }
        .btn-brown:hover{
            background-color:#5f4231 !important;
            border-color:#5f4231 !important;
        }
        .form-control:focus{
            border-color:var(--soft-brown);
            box-shadow:0 0 0 .2rem rgba(111,78,55,.15);
        }
        .caps-hint{ display:none; font-size:.85rem; color:#b02a37; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<div id="header"><%@ include file="../header.jsp" %></div>

<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
        <!-- 메인 영역만 가운데 정렬 -->
        <main class="flex-grow-1 p-4 d-flex align-items-center justify-content-center">
            <div class="form-container">
                <!-- 알림 -->
                <c:if test="${!empty param.error}">
                    <div class="alert alert-danger mb-3" role="alert">잘못된 아이디나 암호입니다.</div>
                </c:if>
                <c:if test="${!empty param.logout}">
                    <div class="alert alert-success mb-3" role="alert">로그아웃 되었습니다.</div>
                </c:if>

                <div class="card shadow-sm">
                    <div class="card-header text-white" style="background-color: var(--main-brown);">
                        <h2 class="h5 mb-0">로그인</h2>
                    </div>

                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation" novalidate>
                            <input type="hidden" value="secret" name="secret_key"/>
                            <c:if test="${_csrf != null}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </c:if>

                            <!-- 아이디 -->
                            <div class="mb-3">
                                <label for="username" class="form-label">아이디</label>
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="아이디를 입력하세요" required autocomplete="username" autofocus>
                                <div class="invalid-feedback">아이디를 입력하세요.</div>
                            </div>

                            <!-- 비밀번호 (보기 토글 + CapsLock 안내) -->
                            <div class="mb-1">
                                <label for="password" class="form-label">비밀번호</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="password" name="password"
                                           placeholder="비밀번호를 입력하세요" required autocomplete="current-password">
                                    <button class="btn btn-brown-outline" type="button" id="togglePwd">
                                        <span id="eyeText">보기</span>
                                    </button>
                                    <div class="invalid-feedback">비밀번호를 입력하세요.</div>
                                </div>
                                <div id="capsHint" class="caps-hint mt-2">Caps Lock이 켜져 있습니다.</div>
                            </div>

                            <!-- 비밀번호 찾기 -->
                            <div class="mt-2 mb-3 text-end">
                                <a href="<c:url value='/login/findPassword'/>"
                                   class="link-secondary text-decoration-underline small">
                                    비밀번호를 잊으셨나요?
                                </a>
                            </div>

                            <!-- 버튼 -->
                            <div class="d-flex justify-content-between">
                                <a href="javascript:history.back();" class="btn btn-brown-outline">이전으로</a>
                                <button type="submit" class="btn btn-brown">로그인</button>
                            </div>
                        </form>
                    </div><!-- card-body -->
                </div><!-- card -->
            </div><!-- form-container -->
        </main>

        <div id="footer"><%@ include file="../footer.jsp" %></div>
    </div>
</div>

<script>
(() => {
    // Bootstrap 폼 검증
    const forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(form => {
        form.addEventListener('submit', e => {
            if (!form.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // 비밀번호 보기/숨기기
    const pwd = document.getElementById('password');
    const toggleBtn = document.getElementById('togglePwd');
    const eyeText = document.getElementById('eyeText');
    toggleBtn.addEventListener('click', () => {
        const isPwd = pwd.type === 'password';
        pwd.type = isPwd ? 'text' : 'password';
        eyeText.textContent = isPwd ? '숨기기' : '보기';
        pwd.focus();
    });

    // Caps Lock 안내
    const capsHint = document.getElementById('capsHint');
    const onCaps = (e) => {
        const capsOn = e.getModifierState && e.getModifierState('CapsLock');
        capsHint.style.display = capsOn ? 'block' : 'none';
    };
    pwd.addEventListener('keydown', onCaps);
    pwd.addEventListener('keyup', onCaps);
})();
</script>
</body>
</html>
