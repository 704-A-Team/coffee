<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="funcs.jsp" %>

</head>
<body>
<c:set var="reqOrderCode" value="${empty order ? 0 : order.order_code}" />
<form id="orderForm" action="/order/save" method="post" onsubmit="return setOrderDetails(${reqOrderCode})">
<div class="doc-wrapper">
	<h4 class="text-center mb-4 fw-bold">발주서</h4>
	
	<input type="hidden" name="orders_client_code" value="${client.client_code }">
	
	<!-- 상단 박스 (정보) -->
	<div class="row mb-4">
    	<div class="col-6 d-flex border border-end-0 p-3">
    	<div class="flex-fill pe-3">
    		<div class="mb-2 d-flex">
				<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">상태</label>
				<c:choose>
    				<c:when test="${empty order}">
        				<div class="form-control form-control-sm bg-light">신규작성</div>
    				</c:when>
    				<c:otherwise>
        				<div class="form-control form-control-sm bg-light">${order.cd_contents }</div>
    				</c:otherwise>
				</c:choose>
		    </div>
    		<c:if test="${not empty order}">
	    		<div class="mb-2 d-flex">
			        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">등록 코드</label>
			        <div class="form-control form-control-sm bg-light">${order.order_code }</div>
			        <input type="hidden" name="order_code" value="${order.order_code }">
		        </div>
    		</c:if>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">등록일</label>
		        <div class="form-control form-control-sm bg-light">
		        	<c:choose>
		        		<c:when test="${empty order }">${nowDate }</c:when>
		        		<c:otherwise>${order.order_reg_date }</c:otherwise>
		        	</c:choose>
		        </div>
	        </div>
	        <c:if test="${not empty order}">
		        <div class="mb-2 d-flex">
			        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">요청일</label>
			        <div class="form-control form-control-sm bg-light">${order.order_req_date }</div>
		        </div>
		        <div class="mb-2 d-flex">
			        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">확정일</label>
			        <div class="form-control form-control-sm bg-light">${order.order_confirmed_date }</div>
		        </div>
		    </c:if>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">담당자명</label>
		        <div class="form-control form-control-sm bg-light">${client.client_emp_name }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">담당자 연락처</label>
		        <div class="form-control form-control-sm bg-light" >${client.client_emp_tel }</div>
	      	</div>
    	</div>
    	</div>

	    <div class="col-6 border p-3">
	    	<div class="mb-2 d-flex">
	        	<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">상호명</label>
	        	<div class="form-control form-control-sm bg-light">${client.client_name }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
	        	<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">대표자명</label>
	        	<div class="form-control form-control-sm bg-light">${client.boss_name }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">사업자등록번호</label>
		        <div class="form-control form-control-sm bg-light">${client.saup_num }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">주소</label>
		        <div class="form-control form-control-sm bg-light">${client.client_address }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
	        	<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">전화번호</label>
	        	<div class="form-control form-control-sm bg-light">${client.client_tel }</div>
	      	</div>
	      	<div class="mb-2">
		        <label class="form-labe mb-2">비고</label>
		        <textarea class="form-control form-control-sm" name="order_note" rows="4" placeholder="비고를 작성해주세요">${order.order_note }</textarea>
	      	</div>
	    </div>
	</div>
    <!-- 하단 박스 (품목 리스트) -->
	<div class="row">
		<div class="col-12 border p-3 ">
	  	<div id="item-list" class="mb-3">
	  		<div class="row fw-bold text-center border-bottom pb-2 mb-2">
			    <div class="col-2">품목명</div>
			    <div class="col-2">단가(원)</div>
			    <div class="col-2">수량</div>
			    <div class="col-1">단위</div>
			    <div class="col-2">공급가액(원)</div>
			    <div class="col-2">납기일</div>
			    <div class="col-1"></div>
			</div>
		</div>
		<div class="col text-end">
			<strong>총액: </strong><span class="total-price">0</span> 원
		</div>

		<!-- 품목 추가 버튼 -->
		<button type="button" class="btn btn-sm btn-outline-primary fw-bold" onclick="addItem()">+ 추가</button>
		</div>
		<div id="ordersDetails"></div>
	</div>

	<!-- 저장/리셋 버튼 -->
	<br><br>
  	<div class="d-flex justify-content-end gap-2 pe-0">
	<c:choose>
  		<c:when test="${empty order or order.order_status == 0}">
  			<c:choose>
  				<c:when test="${empty order}">
  					<button type="reset" class="btn btn-md btn-danger fw-bold" onclick="location.href='/order/list'">취소</button>
  				</c:when>
  				<c:otherwise>
  					<button type="reset" class="btn btn-md btn-danger fw-bold" onclick="location.href='/order/${order.order_code }'">변경취소</button>
  				</c:otherwise>
  			</c:choose>
  			<button type="submit" class="btn btn-md btn-primary fw-bold">저장</button>
  		</c:when>
  		<c:otherwise>
  			<div class="card border-0 pe-0">
  				<div class="alert alert-info d-flex align-items-center" role="alert">
			  		<i class="bi bi-info-circle-fill me-2"></i>
			  		<div>
			    		발주가 <strong>승인</strong> 또는 <strong>반려</strong>되기 전까지 <u>내용을 변경하거나 요청을 취소</u>할 수 있습니다.
			  		</div>
				</div>
				<div class="d-flex justify-content-end gap-2 pe-0">
					<button type="reset" class="btn btn-md btn-danger fw-bold" onclick="location.href='/order/${order.order_code }'">변경취소</button>
  					<button type="submit" class="btn btn-md btn-primary fw-bold">발주저장</button>
				</div>
			</div>
  		</c:otherwise>
  	</c:choose>
  	</div>
</div>
</form>
</body>
</html>