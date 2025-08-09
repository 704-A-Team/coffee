<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 리스트</title>
 
<style>
  .form-section-title {
      border-left: 4px solid #0d6efd;
      padding-left: 10px;
      margin-bottom: 20px;
      font-weight: 600;
      font-size: 2rem;
  }

  .btn-light-primary {
    background-color: #cce5ff !important;
    color: #004085 !important;
    border: 1px solid #b8daff !important;
  }
  .btn-light-primary:hover {
    background-color: #b8daff !important;
    color: #002752 !important;
  }

  .btn-light-warning {
    background-color: #fff3cd !important;
    color: #856404 !important;
    border: 1px solid #ffeeba !important;
  }
  .btn-light-warning:hover {
    background-color: #ffe8a1 !important;
    color: #533f03 !important;
  }

  .btn-light-danger {
    background-color: #f8d7da !important;
    color: #721c24 !important;
    border: 1px solid #f5c6cb !important;
  }
  .btn-light-danger:hover {
    background-color: #f1b0b7 !important;
    color: #491217 !important;
  }

  .card-title {
    font-weight: 700;
  }
  .product-info-list {
    list-style: none;
    padding: 0;
    margin-bottom: 1rem; 
  }
  .product-info-list li {
    display: flex;
    margin-bottom: 6px;
  }
  .product-info-list li strong {
    width: 40%;      
    /* opacity: 0.7;      */
  }
  .product-info-list li span {
    width: 60%;      
  }
</style>

       
</head>
<body class="d-flex flex-column min-vh-100">
	
	<!-- HEADER -->
	<%@ include file="../header.jsp" %>
	
	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="../sidebar.jsp" %>
		
		<div class="d-flex flex-column flex-grow-1">
			
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
		    <div class="container">
		    	<div class="container mt-3">
		        <div class="form-section-title">완제품 리스트</div>
		
		   		        <div class="row">
		            <c:forEach var="product" items="${wanProductList}">
		                <div class="col-md-6 mb-4">
		                    <div class="card h-100">
		                        <div class="row g-0">
		                     <div class="col-md-4">
							    <c:choose>
							        <c:when test="${not empty product.simage}">
							            <img src="${pageContext.request.contextPath}/upload/s_${product.simage}" class="img-fluid rounded-start" alt="${product.product_name}">
							        </c:when>
							        <c:otherwise>
							            <img src="${pageContext.request.contextPath}/upload/default.png" class="img-fluid rounded-start" alt="기본 이미지">
							        </c:otherwise>
							    </c:choose>
							 </div>
		                            <div class="col-md-8">
		                                <div class="card-body d-flex flex-column h-100">
		                                    <h5 class="card-title">${product.product_name}</h5>
		                                    <p class="card-text text-truncate" title="${product.product_contents}">
		                                        ${product.product_contents}
		                                    </p>
		                                    <ul class="product-info-list">
		                                        <li><strong>예상 수율</strong> <span>${product.product_yield} %</span></li>
		                                        <li><strong>생산 단위</strong> <span>${product.product_pack} 개</span></li>
		                                        <li>
													<strong>가격</strong>
													<span>
												    <c:choose>
												        <c:when test="${product.price == 0}">
												            <span style="color: red; font-weight: bold;">가격 조정 필요</span>
												        </c:when>
												        <c:otherwise>
												            ${product.price} 원
												        </c:otherwise>
												    </c:choose>
												    </span>
												</li>
												<li><strong>판매 여부</strong> 
													  <c:choose>
													    <c:when test="${product.product_isdel == false}">
													      <span style="color:green;">판매중</span>
													    </c:when>
													    <c:otherwise>
													      <span style="color:red;">판매 중지</span>
													    </c:otherwise>
													  </c:choose>
												</li>
		                                        <li><strong>등록일</strong> <span>${product.start_date != null ? product.start_date : '정보 없음'}</span></li>
		                                    </ul>
		
			       							<div class="d-flex gap-2 mt-auto">
											  <!-- 상세 보기 버튼은 그대로 유지 -->
											  <form action="/km/wanAndRcpDetailInForm" method="get" style="flex-grow:1;">
											    <input type="hidden" name="product_code" value="${product.product_code}" />
											    <button type="submit" class="btn btn-light-primary btn-sm w-100">상세 보기</button>
											  </form>
											
											  <!-- 가격 조정 버튼 조건부 처리 -->
											  <c:choose>
											    <c:when test="${product.product_isdel == true}">
											      <!-- 판매 중지일 경우 모달 버튼 -->
											      <div style="flex-grow:1;">
												  	<button type="button" class="btn btn-light-warning btn-sm w-100" onclick="showStoppedModal()">가격 조정</button>
												  </div>
											    </c:when>
											    <c:otherwise>
											      <!-- 판매 중이면 정상 폼 전송 -->
											      <form action="/km/wanPriceModifyInForm" method="get" style="flex-grow:1;">
											        <input type="hidden" name="product_code" value="${product.product_code}" />
											        <button type="submit" class="btn btn-light-warning btn-sm w-100">가격 조정</button>
											      </form>
											    </c:otherwise>
											  </c:choose>
											</div>


		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </c:forEach>
		        </div>
		
		        <!-- 페이징 -->
		        <nav aria-label="Page navigation">
		            <ul class="pagination justify-content-center">
		                <c:if test="${page.startPage > page.pageBlock}">
		                    <li class="page-item">
		                        <a class="page-link" href="/km/wanList?currentPage=${page.startPage - page.pageBlock}" aria-label="Previous">
		                            &laquo; 이전
		                        </a>
		                    </li>
		                </c:if>
		
		                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
		                    <li class="page-item ${i == page.currentPage ? 'active' : ''}">
		                        <a class="page-link" href="/km/wanList?currentPage=${i}">${i}</a>
		                    </li>
		                </c:forEach>
		
		                <c:if test="${page.endPage < page.totalPage}">
		                    <li class="page-item">
		                        <a class="page-link" href="/km/wanList?currentPage=${page.startPage + page.pageBlock}" aria-label="Next">
		                            다음 &raquo;
		                        </a>
		                    </li>
		                </c:if>
		            </ul>
		        </nav>
		     </div>
		   </div>
		</main>
		<!-- 판매중지 알림 모달 -->
		<div class="modal fade" id="stoppedModal" tabindex="-1" aria-labelledby="stoppedModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header bg-warning">
		        <h5 class="modal-title" id="stoppedModalLabel">알림</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
		      </div>
		      <div class="modal-body">
		        해당 제품은 현재 <strong>판매 중지</strong> 상태입니다.<br>가격 조정이 불가능합니다.
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
		</div>			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>	
</body>
<!-- Bootstrap JS (Modal 작동에 필수) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
  function showStoppedModal() {
    var modal = new bootstrap.Modal(document.getElementById('stoppedModal'));
    modal.show();
  }
</script>

</html>