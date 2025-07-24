<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>📌 게시판 목록</title>
</head>
<body>
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="../header.jsp" %>
	</div>
	
	<div id="contents" class="container mt-4">
	  <h3 class="mb-3">📋 게시판</h3>
	  <c:set var="num" value="${page.total - page.start + 1}"></c:set>
	
	  <table class="table table-striped table-bordered table-hover text-center align-middle">
	    <thead class="table-primary">
	      <tr>
	        <th>글번호</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>조회수</th>
	        <th>등록일</th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:forEach var="board" items="${boardList}">
	        <tr>
	          <td>${num}</td>
	          <td class="text-start ps-3">
	            <a href="/board/detail?board_no=${board.board_no}">
	              ${board.title}
	            </a>
	          </td>
	          <td>${board.emp_no}</td>
	          <td>${board.read_count}</td>
	          <td>
	            <fmt:formatDate value="${board.in_date}" pattern="yyyy-MM-dd"/>
	          </td>
	        </tr>
	        <c:set var="num" value="${num - 1}" />
	      </c:forEach>
	    </tbody>
	  </table>
	
	  
	  <!-- 페이징 -->
	  <nav aria-label="Page navigation">
	    <ul class="pagination justify-content-center">
	      <c:if test="${page.startPage > page.pageBlock}">
	        <li class="page-item">
	          <a class="page-link" href="/dept/list?currentPage=${page.startPage - page.pageBlock}" aria-label="Previous">
	            &laquo; 이전
	          </a>
	        </li>
	      </c:if>
	
	      <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
	        <li class="page-item ${i == page.currentPage ? 'active' : ''}">
	          <a class="page-link" href="/dept/list?currentPage=${i}">${i}</a>
	        </li>
	      </c:forEach>
	
	      <c:if test="${page.endPage < page.totalPage}">
	        <li class="page-item">
	          <a class="page-link" href="/dept/list?currentPage=${page.startPage + page.pageBlock}" aria-label="Next">
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
	
	<!-- 부트스트랩 JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
