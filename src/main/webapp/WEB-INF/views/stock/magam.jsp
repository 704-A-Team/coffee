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
<script type="text/javascript">
	
	function openMagamModal() {
		if (confirm('일마감 전일 경우 일마감이 먼저 진행됩니다.\n진행하시겠습니까?')) {
		    var myModal = new bootstrap.Modal(document.getElementById('mMagamModal'));
		    myModal.show();
		}
	}

	function mMagam() {
		magamCheck = $('#mMagamModalCheck').val();
		console.log(magamCheck)
		if (magamCheck?.trim() !== "확인했습니다.") {
			alert("확인 문장을 입력하세요");
			return false;
		}
		
		location.href = "/inventory/close/mm"
	}


</script>
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
                	
                	<!-- 월마감 -->
                    <div class="d-flex justify-content-end mb-3">
                    	<c:if test="${isClosed }">
                    		<div class="alert alert-danger d-flex align-items-center" role="alert">
						  		<i class="bi bi-exclamation-octagon-fill me-2"></i>
						  		<div>
						    		<strong>월마감이 완료</strong>되었습니다
						  		</div>
							</div>
                    	</c:if>
                    	<c:if test="${not isClosed }">
							<div class="d-flex justify-content-end gap-2 pe-0">
		                    	<button class="btn btn-danger btn-md fw-bold me-2" onclick="openMagamModal()">월마감</button>
                			</div>
                 		</c:if>
                 	</div>
                 	
                 	<!-- 마감 모달 -->
				  	<div class="modal fade" id="mMagamModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <div class="modal-body">
					      	<div>이 동작은 돌이킬 수 없습니다.</div>
					      	<div>계속 진행하려면 아래 문장을 입력하세요.</div>
					      	<div><i>"확인했습니다."</i></div>
					        <textarea rows="4" class="form-control form-control-sm mt-4" placeholder="확인했습니다." id="mMagamModalCheck"></textarea>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
					        <button type="button" class="btn btn-danger fw-bold" onclick="return mMagam()">확인</button>
					      </div>
					    </div>
					  </div>
					</div>
                	
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
