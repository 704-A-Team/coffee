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
    
</style>
</head>
<body>

<!-- 헤더 -->
<div id="header" style="background-color:#96bfe7;">
    <%@ include file="../header.jsp" %>
</div>

<!-- 본문 -->
<div id="contents" class="container mt-4">
<!-- 직원 상세보기 제목 -->
<div class="text-center mb-4">
    <h2 class="fw-bold text-primary">👩‍💼 직원 상세보기 👨‍💼</h2>
    <p class="text-muted">사원의 상세 정보를 확인해보세요 😊</p>
</div>

<!-- 직원 기본 정보 카드 -->
<div class="card mb-4 shadow-sm">
    <div class="row g-0">
        <!-- 이미지 -->
      <div class="col-md-4">
            <c:choose>
                <c:when test="${not empty emp.uploadFileNames}">
                    <img src="${pageContext.request.contextPath}/upload/s_${emp.uploadFileNames[0]}" class="img-fluid rounded-start" alt="대표 이미지">
                </c:when>
                <c:otherwise>
                    <img src="/images/default-profile.png" class="img-fluid rounded-start" alt="기본 이미지">
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 정보 -->
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title text-info">${emp.emp_name} 🧑‍💼</h5>
                <p class="card-text">
                    <strong>아이디:</strong> ${emp.emp_id}<br/>
                    <strong>이메일:</strong> ${emp.email}<br/>
                    <strong>전화번호:</strong> ${emp.emp_tel}<br/>
                    <strong>급여:</strong> ${emp.sal} 만 원<br/>
                    <strong>입사일:</strong> <tf:formatDateTime value="${emp.in_date}" pattern="yyyy-MM-dd" /><br/>
                    <strong>부서코드:</strong> ${emp.dept_code}<br/>
                    <strong>상태:</strong> 
                    <c:choose>
                        <c:when test="${emp.del_status}">🟥 삭제됨</c:when>
                        <c:otherwise>🟩 정상</c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
    </div>
</div>

<!-- 첨부 이미지 목록 -->
<c:if test="${not empty emp.uploadFileNames}">
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-light">
            <h5 class="mb-0 text-secondary">📎 첨부 이미지</h5>
        </div>
        <div class="card-body">
            <div class="d-flex flex-wrap gap-3">
                <c:forEach var="file" items="${emp.uploadFileNames}">
                    <div class="border rounded p-2 bg-white text-center">
                        <img src="${pageContext.request.contextPath}/upload/${file}" 
                             alt="${file}" style="height:100px; object-fit:contain;">
                        <p class="mt-2 small">${file}</p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:if>

</div>

<!-- 푸터 -->
<div id="footer">
    <%@ include file="../foot.jsp" %>
</div>

<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
