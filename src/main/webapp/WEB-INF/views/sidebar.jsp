<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	.menu-small {
	  padding-left: 2.4rem;
	  text-decoration: none !important;
	}
	#sidebarNav.sidebarNav-toggled {
	  margin-left: -180px; /* 숨김 처리 */
	  transition: margin-left 0.3s;
	}
</style>

</head>
<body>
	<nav class="navbar-expand flex-shrink-0 p-3 navbar-dark bg-dark" id="sidebarNav" style="width: 190px;">
    <ul class="list-unstyled ps-0">
    	
    	<!-- 영업 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#client-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>영업관리
			</button>
			<div class="collapse" id="client-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">가맹점</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">가맹점</a></li>
				</ul>
			</div>
		</li>
		
		<!-- 구매 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#pch-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>구매관리
			</button>
			<div class="collapse" id="pch-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">발주</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">발주</a></li>
				</ul>
			</div>
		</li>
		
		<!-- 생산 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#mf-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>생산관리
			</button>
			<div class="collapse" id="mf-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">완제품</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">완제품</a></li>
				</ul>
			</div>
		</li>
		
		<!-- 재고 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#prd-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>재고관리
			</button>
			<div class="collapse" id="prd-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">마감</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">마감</a></li>
				</ul>
			</div>
		</li>
		
		<!-- 판매 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#order-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>판매관리
			</button>
			<div class="collapse" id="order-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="/order/new" class="link-light rounded menu-small">수주 목록</a></li>
				    <li class="mt-2"><a href="/order/new" class="link-light rounded menu-small">수주서 작성</a></li>
				</ul>
			</div>
		</li>
		
		<!-- 인사 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#hr-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>인사관리
			</button>
			<div class="collapse" id="hr-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">부서</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">부서</a></li>
				</ul>
			</div>
		</li>
		
    </ul>
  </nav>

</body>
</html>