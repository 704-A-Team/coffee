<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>재고관리</title>
<script type="text/javascript">

	function magam() {
		magamCheck = $('#magamModalCheck').val();
		console.log(magamCheck)
		if (magamCheck?.trim() !== "확인했습니다.") {
			alert("확인 문장을 입력하세요");
			return false;
		}
		
		if (${isClosedToday}) location.href = "/inventory/cancel";
		else location.href = "/inventory/close"
	}


</script>
</head>

<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1"><!-- 좌/우 영역 -->
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 래퍼: 푸터 하단 고정용 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1">
            	<div class="container mt-4 p-4">
                	<div class="form-section-title">재고 현황</div>
                	
                    <!-- 일마감/취소 -->
                    <div class="d-flex justify-content-end mb-3">
                    	<c:if test="${isClosed }">
                    		<div class="alert alert-danger d-flex align-items-center" role="alert">
						  		<i class="bi bi-exclamation-octagon-fill me-2"></i>
						  		<div>
						    		<strong>월마감이 완료</strong>되어 <u>일마감 진행 및 취소가 불가능</u>합니다.
						  		</div>
							</div>
                    	</c:if>
                    	<c:if test="${not isClosed }">
	                    	<button class="btn btn-danger btn-md fw-bold me-2" ${isClosedToday ? 'disabled' : ''} data-bs-toggle="modal" data-bs-target="#magamModal">일마감</button>
	                        <button class="btn btn-secondary btn-md fw-bold" ${isClosedToday ? '' : 'disabled'} data-bs-toggle="modal" data-bs-target="#magamModal">일마감취소</button>
                 		</c:if>
                 	</div>
                 	
                 	<!-- 마감 모달 -->
				  	<div class="modal fade" id="magamModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <div class="modal-body">
					      	<div>이 동작은 돌이킬 수 없습니다.</div>
					      	<div>계속 진행하려면 아래 문장을 입력하세요.</div>
					      	<div><i>"확인했습니다."</i></div>
					        <textarea rows="4" class="form-control form-control-sm mt-4" placeholder="확인했습니다." id="magamModalCheck"></textarea>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
					        <button type="button" class="btn btn-danger fw-bold" onclick="return magam()">확인</button>
					      </div>
					    </div>
					  </div>
					</div>

	                <!-- 테이블 -->
	                <table class="table table-bordered text-center">
	                    <thead class="table-secondary">
	                        <tr>
	                            <th>제품코드</th>
	                            <th>제품명</th>
	                            <th>단위</th>
	                            <th>제품유형</th>
	                            <th>납품여부</th>
	                            <th>실재고량</th>
	                            <th>가용재고량</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:choose>
	                            <c:when test="${not empty inventoryList}">
	                                <c:forEach var="item" items="${inventoryList}">
	                                    <tr>
	                                        <td>${item.product_code}</td>
	                                        <td>${item.product_name}</td>
	                                        <td>${item.unit_contents}</td>
	                                        <td>${item.type_contents}</td>
	                                        <td>
	                                        	<c:if test="${item.product_isorder }">납품</c:if>
	                                        	<c:if test="${not item.product_isorder }">비납품</c:if>
	                                        </td>
	                                        <td>${item.real_stock}</td>
	                                        <td>${item.usable_stock}</td>
	                                    </tr>
	                                </c:forEach>
	                            </c:when>
	                            <c:otherwise>
	                                <tr>
	                                    <td colspan="7">표시할 데이터가 없습니다.</td>
	                                </tr>
	                            </c:otherwise>
	                        </c:choose>
	                    </tbody>
	                </table>

	                <!-- 페이징 -->
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
	                            <li class="page-item">
									<a class="page-link" href="/inventory/list?page=${page.startPage - page.pageBlock}&size=${page.rowPage}">이전</a>
								</li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
									<a class="page-link" href="/inventory/list?page=${i}&size=${page.rowPage}">${i}</a>
								</li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
									<a class="page-link" href="/inventory/list?page=${page.startPage + page.pageBlock}&size=${page.rowPage}">다음</a>
								</li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
			</main>
			<%@ include file="../footer.jsp" %>
        </div><!-- /본문 래퍼 -->
    </div><!-- /좌우 영역 -->
</body>
</html>

