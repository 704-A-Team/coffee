<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<script>
	document.addEventListener("DOMContentLoaded", function () {
		const sidebarToggle = document.getElementById("sidebarToggle");
		const sidebarNav = document.getElementById("sidebarNav");
		
		sidebarToggle.addEventListener("click", function () {
			sidebarNav.classList.toggle("sidebarNav-toggled");
		});
	});
</script>
<style type="text/css">
	:root {
	    --main-brown: #6f4e37;
	    --light-brown: #e6d3c1;
	    --dark-brown: #4e342e;
    }
    
	.form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
	}
    .pagination .page-link {
            color: var(--main-brown);
	}

	.pagination .page-item.active .page-link {
            background-color: var(--main-brown);
            border-color: var(--main-brown);
            color: white;
	}

</style>
</head>
<body class="sb-nav-fixed">
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<!-- Navbar Brand-->
		<a class="navbar-brand ps-3" href="/">Coffee Yummy</a>
		<!-- Sidebar Toggle-->
		<button class="btn btn-sm text-white border-0 bg-transparent" id="sidebarToggle"><i class="bi bi-list"></i></button>

	   <!-- 우측 메뉴 -->
		<ul class="navbar-nav ms-auto me-3 me-lg-4">
		  <li class="nav-item dropdown">
		    <a class="nav-link dropdown-toggle" href="#" id="userMenu"
		       role="button" data-bs-toggle="dropdown" aria-expanded="false">
		      <i class="bi bi-person-fill"></i>
		    </a>
		
		    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenu">
		      <sec:authorize access="isAnonymous()">
		        <li><a class="dropdown-item" href="/login">Login</a></li>
		      </sec:authorize>
		
		      <sec:authorize access="isAuthenticated()">
		       <sec:authorize access="isAuthenticated()">
						    <li class="dropdown-item-text p-2" style="line-height: 1.6;">
						        <div>
						            <strong>로그인:</strong>
						            <span><sec:authentication property="principal.username"/></span>
						        </div>
						        <div>
						            <strong>권한:</strong>
						            <span><sec:authentication property="principal.roles"/></span>
						        </div>
						    </li>
						</sec:authorize>
				<li class="px-3">
						  <sec:authorize access="hasAuthority('ROLE_MANAGER')">
						    <a href="${pageContext.request.contextPath}/MyPage/manager"
						       class="btn btn-sm btn-outline-primary w-100">My Page</a>
						  </sec:authorize>
						
						  <sec:authorize access="hasAuthority('ROLE_USER')">
						    <a href="${pageContext.request.contextPath}/MyPage/user"
						       class="btn btn-sm btn-outline-primary w-100">My Page</a>
						  </sec:authorize>
						
						  <sec:authorize access="hasAuthority('ROLE_CLIENT2')">
						    <a href="${pageContext.request.contextPath}/MyPage/client2"
						       class="btn btn-sm btn-outline-primary w-100">My Page</a>
						  </sec:authorize>
						
						  <sec:authorize access="hasAuthority('ROLE_CLIENT')">
						    <a href="${pageContext.request.contextPath}/MyPage/client"
						       class="btn btn-sm btn-outline-primary w-100">My Page</a>
						  </sec:authorize>
						
						  <sec:authorize access="hasAuthority('ROLE_GUEST')">
						    <a href="${pageContext.request.contextPath}/MyPage/guest"
						       class="btn btn-sm btn-outline-primary w-100">My Page</a>
						  </sec:authorize>
						</li>
		        <li>
		          <form action="/logout" method="post" class="px-3">
		            <sec:csrfInput/>
		            <button type="submit" class="btn btn-sm btn-outline-danger w-100">Log out</button>
		          </form>
		        </li>
		      </sec:authorize>
		    </ul>
		  </li>
</ul>
	</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>