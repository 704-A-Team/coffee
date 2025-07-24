<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<!-- 부트스트랩 CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<meta charset="UTF-8">
	<title>📄 게시글 상세보기</title>
	<style>
		body {
			background-color: #e6f2fb;
		}
		.card {
			border: 2px solid #96bfe7;
			box-shadow: 0 4px 8px rgba(0,0,0,0.1);
		}
		.card-header {
			background-color: #96bfe7;
			color: white;
			font-weight: bold;
		}
	</style>
</head>
<body>

	<!-- 헤더 영역 -->
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="../header.jsp" %>
	</div>

	<!-- 본문 영역 -->
	<div class="container mt-5">
		<div class="card">
			<div class="card-header">
				📌 ${board.title}
			</div>
			<div class="card-body">
				<h6 class="card-subtitle mb-2 text-muted">
					글번호: ${board.board_no} | 작성자 사번: ${board.emp_no} | 조회수: ${board.read_count}
				</h6>
				<hr>
				<p class="card-text">${board.content}</p>

				<!-- 버튼들 -->
				<div class="mt-4 text-end">
					<a href="/board/list" class="btn btn-outline-primary btn-sm">목록으로</a>
					<a href="/board/modify?board_no=${board.board_no}" class="btn btn-outline-success btn-sm">수정</a>
					<a href="/board/delete?board_no=${board.board_no}" class="btn btn-outline-danger btn-sm"
					   onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
				</div>
			</div>
		</div>
	</div>

	<!-- 푸터 영역 -->
	<div id="footer" class="mt-5">
		<%@ include file="../foot.jsp" %>
	</div>

	<!-- 부트스트랩 JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
