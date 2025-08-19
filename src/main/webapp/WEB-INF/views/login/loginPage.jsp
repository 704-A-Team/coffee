<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root{
            --main-brown:#6f4e37;
            --dark-brown:#4e342e;
            --soft-brown:#bfa08e;
        }
        .form-container{ width:100%; max-width:980px; margin:0 auto; }
        /* 프로젝트 표준 버튼 */
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
        /* 입력 포커스 톤 */
        .form-control:focus{
            border-color:var(--soft-brown);
            box-shadow:0 0 0 .2rem rgba(111,78,55,.15);
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <div id="header"><%@ include file="../header.jsp" %></div>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 d-flex align-items-center justify-content-center p-4">
                <div class="form-container">

                    <!-- 카드 위 알림 -->
                    <c:if test="${!empty param.error}">
                        <div class="alert alert-danger mb-3" role="alert">
                            잘못된 아이디나 암호입니다.
                        </div>
                    </c:if>
                    <c:if test="${!empty param.logout}">
                        <div class="alert alert-success mb-3" role="alert">
                            로그아웃 되었습니다.
                        </div>
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

                                <!-- 1행: 아이디 + 버튼(오른쪽) -->
                                <div class="row g-3 align-items-end">
                                    <div class="col-md-8">
                                        <label for="username" class="form-label">아이디</label>
                                        <input type="text" class="form-control" id="username" name="username"
                                               placeholder="아이디를 입력하세요" required>
                                    </div>
                                    <div class="col-md-4 d-flex gap-2 justify-content-start">
                                        <button type="submit" class="btn btn-primary">로그인</button>
                                    </div>
                                </div>

                                <!-- 2행: 비밀번호 -->
                                <div class="row g-3 mt-1">
                                    <div class="col-md-8">
                                        <label for="password" class="form-label">비밀번호</label>
                                        <input type="password" class="form-control" id="password" name="password"
                                               placeholder="비밀번호를 입력하세요" required>
                                    </div>
                                </div>
                            </form>
                        </div><!-- card-body -->
                    </div><!-- card -->
                </div><!-- form-container -->
            </main>

            <div id="footer"><%@ include file="../footer.jsp" %></div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
