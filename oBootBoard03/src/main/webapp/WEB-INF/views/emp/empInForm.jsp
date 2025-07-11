<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<meta charset="UTF-8">
<title>직원 등록 폼</title>
<style>
    .form-container {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 15px;
        box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.1);
    }

    .btn-babyblue {
        background-color: #96bfe7 !important;
        color: white !important;
        border: 1px solid #96bfe7 !important;
    }

    .btn-babyblue:hover {
        background-color: #76aadd !important;
        color: white !important;
        border: 1px solid #76aadd !important;
    }

    body {
        background-color: #e3f2fd;
    }

    h3 i {
        color: #96bfe7;
        margin-right: 10px;
    }
</style>
</head>
<body>

<!-- 헤더 -->
<div id="header" style="background-color:#96bfe7;">
    <%@ include file="../header.jsp" %>
</div>

<!-- 본문 -->
<div id="contents" class="container mt-4">
    <div class="form-container mx-auto col-md-8">
        <h3 class="text-center mb-4">
            <i class="bi bi-person-plus-fill"></i>직원 등록
        </h3>

        <form action="/emp/empSave" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label class="form-label">아이디</label>
                <input type="text" name="emp_id" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">비밀번호</label>
                <input type="password" name="emp_password" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">이름</label>
                <input type="text" name="emp_name" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">이메일</label>
                <input type="email" name="email" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">전화번호</label>
                <input type="text" name="emp_tel" class="form-control" placeholder="010-1234-5678" />
            </div>
            <div class="mb-3">
                <label class="form-label">급여</label>
                <input type="number" name="sal" class="form-control" />
            </div>
            <div class="mb-3">
                <label class="form-label">부서</label>
                <select name="dept_code" class="form-select">
                    <option value="">부서를 선택하세요</option>
                    <c:forEach var="dept" items="${deptList}">
                        <option value="${dept.dept_code}">${dept.dept_name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">파일 업로드</label>
                <input type="file" name="file" class="form-control" multiple />
            </div>

            <input type="hidden" name="del_status" value="false" />

            <div class="text-center">
                <button type="submit" class="btn btn-babyblue px-4">등록하기</button>
            </div>
        </form>
    </div>
</div>

<!-- 푸터 -->
<div id="footer">
    <%@ include file="../foot.jsp" %>
</div>

<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
