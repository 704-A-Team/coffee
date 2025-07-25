<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<meta charset="UTF-8">
<style type="text/css">
	.navbar-brand{
		font-family: impact;
		font-size: 2em;
		letter-spacing: 1px;
	}
	
	.nav-link{
		font-family: 
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg"> <!-- bg-body-tertiary 제거: bg color -->
		  <div class="container-fluid">
		    <a class="navbar-brand" href="/main">Navbar</a>
	
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
			    <div class="collapse navbar-collapse" id="navbarNavDropdown">
			      <ul class="navbar-nav">
			      
			        <li class="nav-item dropdown">

			          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
			           	부서 관리
			          </a>
			          <ul class="dropdown-menu">
			            <li><a class="dropdown-item" href="/dept/list">부서 조회</a></li>
			            <li><a class="dropdown-item" href="/dept/deptInForm">부서 등록</a></li>
			          </ul>
			        </li>
			        
			        <li class="nav-item dropdown">
			          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
			           	사원 관리
			          </a>
			          <ul class="dropdown-menu">
			            <li><a class="dropdown-item" href="/emp/list">사원 조회</a></li>
			            <li><a class="dropdown-item" href="/emp/empInForm">사원 등록</a></li>
			          </ul>
			        </li>
			        
			        <li class="nav-item dropdown">
			          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
			            게시판 관리
			          </a>
			          <ul class="dropdown-menu">
			            <li><a class="dropdown-item" href="/board/list">게시판 조회</a></li>
			            <li><a class="dropdown-item" href="/board/write_view">게시판 등록</a></li>
			            <li><a class="dropdown-item" href="#">개인정보 수정</a></li>
			          </ul>
			        </li>
			        
			      </ul>
			    </div>
		  </div>
		</nav>
</body>
</html>