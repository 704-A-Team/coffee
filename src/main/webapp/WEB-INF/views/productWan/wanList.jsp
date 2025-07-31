<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 리스트</title>
 
<style>
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
		        <h1 class="text-center mb-4">완제품 리스트</h1>
		
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
		                                    <ul class="list-unstyled mb-3">
		                                        <li><strong>예상 수율:</strong> ${product.product_yield}%</li>
		                                        <li><strong>생산 단위:</strong> ${product.product_pack}</li>
		                                        <li><strong>가격:</strong> ${product.price}원</li>
		                                        <li><strong>최근 등록일:</strong> ${product.start_date != null ? product.start_date : '정보 없음'}</li>
		                                    </ul>
		
		       								<div class="d-flex gap-2 mt-auto">
												  <form action="/km/wanProductModifyInForm" method="get" style="flex-grow:1;">
												    <input type="hidden" name="product_code" value="${product.product_code}" />
												    <button type="submit" class="btn btn-light-primary btn-sm w-100">수정</button>
												  </form>
												
												  <form action="/km/wanPriceModifyInForm" method="get" style="flex-grow:1;">
												    <input type="hidden" name="product_code" value="${product.product_code}" />
												    <button type="submit" class="btn btn-light-warning btn-sm w-100">가격 조정</button>
												  </form>
												
												  <form action="/km/wanProductdelete" method="post" style="flex-grow:1;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
												    <input type="hidden" name="product_code" value="${product.product_code}" />
												    <button type="submit" class="btn btn-light-danger btn-sm w-100">삭제</button>
												  </form>
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
		</main>

			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>