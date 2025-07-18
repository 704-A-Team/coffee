<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
	.sky-box {
		background-color: #e0f2ff;
		border-radius: 10px;
		padding: 30px;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	}
	.btn-sky {
		background-color: #96bfe7;
		color: white;
	}
	.btn-sky:hover {
		background-color: #7bb0e5;
	}
</style>
</head>
<body>
<div id="header" style="background-color:#96bfe7;">
	<%@ include file="../header.jsp" %>
</div>

<div id="contents" class="container mt-5">
	<div class="sky-box">
		<h3 class="mb-4">📄 게시글 작성</h3>
		
		<form action="/board/write" method="post">
			<div class="mb-3">
				<label for="title" class="form-label">제목</label>
				<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요" required>
			</div>

			<div class="mb-3">
				<label for="emp_no" class="form-label">작성자</label>
				<select name="emp_no" id="emp_no" class="form-select" required>
					<option value="" disabled selected>-- 작성자 선택 --</option>
					<c:forEach var="emp" items="${empName}">
						<option value="${emp.emp_no}">${emp.emp_name}</option>
					</c:forEach>
				</select>
			</div>

			<div class="mb-3">
				<label for="content" class="form-label">내용</label>
				<textarea class="form-control" id="content" name="content" rows="6" placeholder="내용을 입력하세요" required></textarea>
			</div>

			<div class="d-flex justify-content-between">
				<button type="reset" class="btn btn-secondary">초기화</button>
				<button type="submit" class="btn btn-sky">확인</button>
				<a href="/board/list" class="btn btn-outline-primary">목록가기</a>
			</div>
		</form>
	</div>
</div>

<div id="footer">
	<%@ include file="../foot.jsp" %>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
