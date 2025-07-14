<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>직원 목록</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
	body {
		background-color: #f4faff;
	}
	.card {
		border-radius: 12px;
		box-shadow: 0 2px 10px rgba(0,0,0,0.1);
		transition: transform 0.2s ease-in-out;
	}
	.card:hover {
		transform: translateY(-5px);
	}
	.card-img-top {
		height: 200px;
		object-fit: cover;
		border-radius: 12px 12px 0 0;
	}
	.card-title {
		color: #007acc;
		font-weight: bold;
	}
</style>
</head>
<body>
<div id="header" style="background-color:#96bfe7;">
	<%@ include file="../header.jsp" %>
</div>

<div id="contents" class="container mt-4">
	<h2 class="mb-4 text-center">직원 리스트</h2>

	<div class="row row-cols-1 row-cols-md-2 g-4">
		<c:forEach var="emp" items="${empList}">
			<div class="col">
				<div class="card">
					<a href="${pageContext.request.contextPath}/emp/detail?emp_no=${emp.emp_no}" style="text-decoration:none; color:inherit;">
					<!-- 카드 내용 -->
						<!-- 대표 이미지 -->
						<c:choose>
							<c:when test="${not empty emp.simage}">
								<img src="${pageContext.request.contextPath}/upload/s_${emp.simage}" class="card-img-top" alt="직원 이미지">
							</c:when>
							<c:otherwise>
								<img src="/images/default-profile.png" class="card-img-top" alt="기본 이미지">
							</c:otherwise>
						</c:choose>
	
						<div class="card-body">
							<h5 class="card-title">${emp.emp_name}</h5>
							<p class="card-text">
								<strong>아이디:</strong> ${emp.emp_id}<br/>
								<strong>이메일:</strong> ${emp.email}<br/>
								<strong>전화번호:</strong> ${emp.emp_tel}<br/>
								<strong>급여:</strong> ${emp.sal} 원<br/>
								<strong>입사일:</strong> 
								  <tf:formatDateTime value="${emp.in_date}"  pattern="yyyy-MM-dd" />
							</p>
	
							<!-- 히든 필드 -->
							<input type="hidden" name="emp_no" value="${emp.emp_no}" />
							<input type="hidden" name="del_status" value="${emp.del_status}" />
							<input type="hidden" name="dept_code" value="${emp.dept_code}" />
						</div>
					</a>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- 페이징 -->
	<nav aria-label="Page navigation" class="mt-5">
		<ul class="pagination justify-content-center">
			<c:if test="${page.startPage > page.pageBlock}">
				<li class="page-item">
					<a class="page-link" href="/emp/list?currentPage=${page.startPage - page.pageBlock}">
						&laquo; 이전
					</a>
				</li>
			</c:if>

			<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
				<li class="page-item ${i == page.currentPage ? 'active' : ''}">
					<a class="page-link" href="/emp/list?currentPage=${i}">${i}</a>
				</li>
			</c:forEach>

			<c:if test="${page.endPage < page.totalPage}">
				<li class="page-item">
					<a class="page-link" href="/emp/list?currentPage=${page.startPage + page.pageBlock}">
						다음 &raquo;
					</a>
				</li>
			</c:if>
		</ul>
	</nav>
</div>

<div id="footer">
	<%@ include file="../foot.jsp" %>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
