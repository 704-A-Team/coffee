<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="../header.jsp" %>
	</div>
	
	<div id="contents" class="container mt-4">
	  <c:set var="num" value="${page.total - page.start + 1}"></c:set>
	
	  <table class="table table-striped table-bordered table-hover text-center align-middle">
	    <thead class="table-primary">
	      <tr>
	        <th>순서</th>
	        <th>부서 이름</th>
	        <th>부서 위치</th>
	        <th>부서 대표 전화</th>
	        <th>부서장</th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:forEach var="dept" items="${deptList}">
	        <tr>
	          <td hidden=""></td>
	          <td>${num}</td>
	          <td><a href="/dept/deptModify?dept_code=${dept.dept_code}">${dept.dept_name}</a></td>
	          <td>${dept.dept_loc}</td>
	          <td>${dept.dept_tel}</td>
	          <td>${dept.dept_captain}</td>
	        </tr>
	        <c:set var="num" value="${num - 1}"></c:set>
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
<!-- 부트스트랩 JS CDN 링크 (<body> 태그 닫기 직전에!) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>