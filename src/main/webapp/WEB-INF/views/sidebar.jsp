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
				     <li class="mt-2">
 			 <a href="${pageContext.request.contextPath}/client/clientList" class="link-light rounded menu-small">거래처 목록</a>
				</li>
					<li class="mt-2">
  			 <a href="${pageContext.request.contextPath}/client/clientInForm" class="link-light rounded menu-small">거래처 등록</a>
				</li>
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
				    <li class="mt-2"><a href="${pageContext.request.contextPath}/provide/provideList" class="link-light rounded menu-small">원재료 조회</a></li>
				    <li class="mt-2"><a href="${pageContext.request.contextPath}/provide/provideInForm" class="link-light rounded menu-small">원재료 등록</a></li>
				    <li class="mt-2"><a href="${pageContext.request.contextPath}/sw/purchaseInForm" class="link-light rounded menu-small">발주 등록</a></li>
					<li class="mt-2"><a href="${pageContext.request.contextPath}/sw/purchaseList" class="link-light rounded menu-small">입고 현황</a></li>
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
					<li class="mt-2"><a href="${pageContext.request.contextPath}/sw/wonProductList" class="link-light rounded menu-small">제품 조회</a></li>
				    <li class="mt-2"><a href="${pageContext.request.contextPath}/sw/wonProductInForm" class="link-light rounded menu-small">제품 등록</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">완제품 등록</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">가격 등록</a></li>
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
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">수주</a></li>
				    <li class="mt-2"><a href="#" class="link-light rounded menu-small">수주</a></li>
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
				   <li class="mt-2">
 			 <a href="${pageContext.request.contextPath}/emp/empList" class="link-light rounded menu-small">사원 목록</a>
				</li>
					<li class="mt-2">
  			 <a href="${pageContext.request.contextPath}/emp/empInForm" class="link-light rounded menu-small">사원 등록</a>
				</li>
				</ul>
			</div>
		</li>
		
		<!-- 부서 메뉴 -->
		<li class="mb-3">
			<button class="btn btn-toggle text-light rounded collapsed" data-bs-toggle="collapse" data-bs-target="#dept-collapse" aria-expanded="false">
				<i class="bi bi-caret-right-fill me-2"></i>부서관리
			</button>
			<div class="collapse" id="dept-collapse">
				<ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
				     <li class="mt-2">
 			 <a href="${pageContext.request.contextPath}/dept/deptList" class="link-light rounded menu-small">부서 목록</a>
				</li>
					<li class="mt-2">
  			 <a href="${pageContext.request.contextPath}/dept/deptInForm" class="link-light rounded menu-small">부서 등록</a>
				</li>
				</ul>
			</div>
		</li>	
    </ul>
  </nav>

</body>
</html>