<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="style_funcs.jsp" %>
</head>
<body>
<form id="orderForm" method="post" onsubmit="return setOrderDetails()">
<div class="doc-wrapper">
	<h4 class="text-center mb-4 fw-bold">수주서</h4>
	<input type="hidden" name="orders_client_code" value="${orders_client_code }">
	
	<!-- 상단 박스 (정보) -->
	<div class="row mb-4">
    	<div class="col-6 d-flex border border-end-0 p-3">
    	<div class="flex-fill pe-3">
    		<div class="mb-2">
				<label class="form-label">상태</label>
				<c:choose>
    				<c:when test="${empty order}">
        				<div class="form-control form-control-sm bg-light">신규 작성</div>
    				</c:when>
    				<c:when test="${order.order_status == 0}">
        				<div class="form-control form-control-sm bg-light">요청 전</div>
    				</c:when>
    				<c:otherwise>
        				<div class="form-control form-control-sm bg-light">요청 완료(승인 전)</div>
    				</c:otherwise>
				</c:choose>
		    </div>
    		<c:if test="${not empty order}">
	    		<div class="mb-2">
			        <label class="form-label">등록 코드</label>
			        <div class="form-control form-control-sm bg-light">${order.order_code }</div>
			        <input type="hidden" name="order_code" value="${order.order_code }">
		        </div>
    		</c:if>
	      	<div class="mb-2">
		        <label class="form-label">등록일</label>
		        <div class="form-control form-control-sm bg-light">${nowDate }</div>
	        </div>
	      	<div class="mb-2">
		        <label class="form-label">담당자명</label>
		        <div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	      	<div class="mb-2">
		        <label class="form-label">전화번호</label>
		        <div class="form-control form-control-sm bg-light" >####</div>
	      	</div>
	      	<div class="mb-2">
		        <label class="form-label">비고</label>
		        <textarea class="form-control form-control-sm" name="order_note" rows="4" placeholder="비고를 작성해주세요"></textarea>
	      	</div>
    	</div>
    	</div>

	    <div class="col-6 border p-3">
	    	<div class="mb-2">
	        	<label class="form-label">상호명</label>
	        	<div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	      	<div class="mb-2">
	        	<label class="form-label">대표자명</label>
	        	<div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	      	<div class="mb-2">
		        <label class="form-label">사업자등록번호</label>
		        <div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	      	<div class="mb-2">
		        <label class="form-label">주소</label>
		        <div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	      	<div class="mb-2">
	        	<label class="form-label">전화번호</label>
	        	<div class="form-control form-control-sm bg-light">####</div>
	      	</div>
	    </div>
	</div>

    <!-- 하단 박스 (품목 리스트) -->
	<div class="row">
		<div class="col-12 border p-3 ">
	  	<div id="item-list" class="mb-3">
	  		<div class="row fw-bold text-center border-bottom pb-2 mb-2">
			    <div class="col-3">품목명</div>
			    <div class="col-2">수량</div>
			    <div class="col-4">납기일</div>
			</div>
		</div>

		<!-- 품목 추가 버튼 -->
		<button type="button" class="btn btn-sm btn-outline-primary fw-bold" onclick="addItem()">+ 추가</button>
		</div>
		<div id="ordersDetails"></div>
	</div>

	<!-- 저장/요청 버튼 -->
	<br><br>
  	<div class="d-flex justify-content-end gap-2 pe-0">
  		<!-- 내용 저장 -->
  		<button type="submit" class="btn btn-md btn-secondary fw-bold" onclick="submitReq('/order/save')">
  			<c:choose>
  				<c:when test="${empty order or order.order_status == 0}">임시저장</c:when>
  				<c:otherwise>발주변경</c:otherwise>
  			</c:choose>
  		</button>

  		<!-- 발주 요청/취소 -->
  		<c:choose>
 			<c:when test="${empty order or order.order_status == 0}">
				<button type="submit" class="btn btn-md btn-primary fw-bold" onclick="submitReq('/order/request')">발주요청</button>
			</c:when>
 			<c:otherwise>
				<button type="submit" class="btn btn-md btn-danger fw-bold" onclick="submitReq('/order/xxxxxxxx')">발주취소</button>
			</c:otherwise>
  		</c:choose>
  	</div>
</div>
</form>
</body>
</html>