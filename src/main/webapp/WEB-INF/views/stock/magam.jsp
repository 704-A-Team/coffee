<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마감 목록 내역</title>
<style>
  .card-hover {
    transition: transform 0.2s, box-shadow 0.2s;
  }
  .card-hover:hover {
    transform: translateY(-5px);
    box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.2);
  }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 영역 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
				<div class="container mt-4 p-4">
                	<div class="form-section-title">마감 내역</div>
                	
                	<div class="row g-4">
					<c:forEach var="magam" items="${monthMagams}">
						<div class="col-12 col-md-3">
							<div class="card h-100 shadow-sm card-hover" onclick="location.href='/inventory/magam/${magam.month_yrmo}/${magam.month_magam_status}'">
								<div class="card-body">
							    <h5 class="card-title">
									${fn:substring(magam.month_yrmo, 0, 4) }년 ${fn:substring(magam.month_yrmo, 4, 6) }월
								</h5>
								<p class="card-text">
									<div class="d-flex gap-2 mb-2">
							  			<strong>년도</strong><span>${fn:substring(magam.month_yrmo, 0, 4) }년</span>
							  		</div>
							  		<div class="d-flex gap-2 mb-2">
								  		<strong>월</strong><span>${fn:substring(magam.month_yrmo, 4, 6) }월</span>
									</div>
									<div class="d-flex gap-2 mb-2">
								  		<strong>구분</strong>
								  		<span>
								  			<c:if test="${magam.month_magam_status == 0 }">기초</c:if>
								  			<c:if test="${magam.month_magam_status == 1 }">기말</c:if>
								  		</span>
								  	</div>
								</p>
								</div>
							</div>
						</div>
					</c:forEach>
 					</div>
                    
                    <!-- 페이징 -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
	                            <li class="page-item">
									<a class="page-link" href="/inventory/magam?page=${page.startPage - page.pageBlock}&size=${page.rowPage}">이전</a>
								</li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
									<a class="page-link" href="/inventory/magam?page=${i}&size=${page.rowPage}">${i}</a>
								</li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
									<a class="page-link" href="/inventory/magam?page=${page.startPage + page.pageBlock}&size=${page.rowPage}">다음</a>
								</li>
                            </c:if>
                        </ul>
                    </nav>
                    
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>
