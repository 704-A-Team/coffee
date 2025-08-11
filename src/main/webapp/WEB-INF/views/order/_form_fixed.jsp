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
<div class="doc-wrapper">
	<h4 class="text-center mb-4 fw-bold">발주서</h4>
	
	<!-- 상단 박스 (정보) -->
	<div class="row mb-4">
    	<div class="col-6 d-flex border border-end-0 p-3">
    	<div class="flex-fill pe-3">
    		<div class="mb-2 d-flex">
				<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">상태</label>
				<c:if test="${order.order_status == 0}">
					<div class="form-control form-control-sm bg-secondary-subtle">${order.cd_contents }</div>
				</c:if>
				<c:if test="${order.order_status == 1}">
					<div class="form-control form-control-sm bg-primary-subtle">${order.cd_contents }</div>
				</c:if>
				<c:if test="${order.order_status == 2}">
					<div class="form-control form-control-sm bg-danger">${order.cd_contents }</div>
				</c:if>
				<c:if test="${order.order_status == 3}">
					<div class="form-control form-control-sm bg-warning">${order.cd_contents }</div>
				</c:if>
				<c:if test="${order.order_status == 4}">
					<div class="form-control form-control-sm bg-success-subtle">${order.cd_contents }</div>
				</c:if>
				<c:if test="${order.order_status == 5}">
					<div class="form-control form-control-sm bg-danger-subtle">${order.cd_contents }</div>
				</c:if>
		    </div>
    		<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">등록 코드</label>
		        <div class="form-control form-control-sm bg-light">${order.order_code }</div>
	        </div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">등록일</label>
		        <div class="form-control form-control-sm bg-light">${order.order_reg_date }</div>
	        </div>
	        <div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">요청일</label>
		        <div class="form-control form-control-sm bg-light">${order.order_req_date }</div>
	        </div>
	        <div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">확정일</label>
		        <div class="form-control form-control-sm bg-light">${order.order_confirmed_date }</div>
	        </div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">담당자명</label>
		        <div class="form-control form-control-sm bg-light">${client.client_emp_name }</div>
	      	</div>
	      	<div class="mb-2 d-flex">
		        <label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">담당자 연락처</label>
		        <div class="form-control form-control-sm bg-light" >${client.client_emp_tel }</div>
	      	</div>
	      	<c:if test="${not empty order.order_refuse }">
	      	<div class="mb-2">
		        <label class="form-label mb-2">반려 사유</label>
		        <div class="form-control form-control-sm bg-danger-subtle">${order.order_refuse }</div>
	      	</div>
	      	</c:if>
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
		        <label class="form-label mb-2">비고</label>
		        <div class="form-control form-control-sm bg-light">${order.order_note }</div>
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
			    <div class="col-1">상태</div>
			</div>
		</div>
		
		<c:set var="totalPrice" value="0" />
		<c:forEach items="${order.orders_details }" var="detail">
			<c:set var="rowTotalPrice" value="${detail.price * detail.order_amount}" />
			<div class="row g-2 mb-2 item-list-item">
				<div class="col-2">
					<div class="form-control form-control-sm bg-light">
						<c:if test="${order.order_status <= 1 and not detail.can_order }">[❌불가] </c:if>${detail.product_name} (${detail.product_code})
					</div>
				</div>
				<div class="col-2">
			    	<div class="form-control form-control-sm bg-light prd-price">${detail.price }</div>
				</div>
				<div class="col-2">
					<div class="form-control form-control-sm bg-light prd-count">${detail.order_amount }</div>
				</div>
				<div class="col-1">
					<div class="form-control form-control-sm bg-light prd-count">${detail.product_order_pack } ${detail.product_cd_contents }</div>
				</div>
				<div class="col-2">
					<div class="form-control form-control-sm bg-light prd-total-price">${rowTotalPrice}</div>
				</div>
				<div class="col-2">
					<fmt:parseDate var="parsedDate" value="${detail.order_ddate }" pattern="yyyyMMdd" />
					<div class="form-control form-control-sm bg-light"><fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" /></div>
				</div>
				<div class="col-1">
				<c:choose>
					<c:when test="${order.order_status != 4}">
						<div class="form-control form-control-sm bg-light text-center">-</div>
					</c:when>
					<c:otherwise>
						<div class="form-control form-control-sm bg-light">${detail.detail_cd_contents }</div>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
			<c:if test="${detail.can_order }">
				<c:set var="totalPrice" value="${totalPrice + rowTotalPrice}" />
			</c:if>
			
		</c:forEach>
		<div class="col text-end">
		<c:choose>
			<c:when test="${order.order_status > 1}">
				<strong>총액: </strong><span>${order.order_final_price }</span> 원
			</c:when>
			<c:otherwise>
				<strong>총액: </strong><span>${totalPrice }</span> 원
			</c:otherwise>
		</c:choose>
		</div>
		</div>
	</div>

	<!-- 저장/요청 버튼 + 수동승인 버튼 -->
	<br><br>
  	<div class="d-flex justify-content-end gap-2 pe-0">
  	<!-- 
  		1. 가맹점: status가 요청상태이하(1이하)면 변경/취소 가능
  		2. 본사: status가 요청상태(1)면 변경/반려/승인 가능
  		2. 가맹점: 아니면 버튼 없음 (조회만 가능)
  	-->
  	<!-- "loginUser가 가맹점이면": <c:if test="${loginUser.login_type == 0 }"></c:if> -->
  	
  	<c:if test="${order.order_status == 0 }">
  		<button type="button" class="btn btn-md btn-secondary fw-bold" onclick="location.href='/order/modify/${order.order_code }'">내용변경</button>
		<button type="button" class="btn btn-md btn-danger fw-bold" onclick="location.href='/order/del/${order.order_code }'">임시저장 삭제</button>
		<button type="button" class="btn btn-md btn-primary fw-bold" onclick="return reqOrder(${order.order_code })">발주요청</button>
  	</c:if>
  	
  	<c:if test="${order.order_status == 1 }">
		<div class="card border-0 pe-0">
			<div class="alert alert-info d-flex align-items-center" role="alert">
		  		<i class="bi bi-info-circle-fill me-2"></i>
		  		<div>
		    		발주가 <strong>승인</strong> 또는 <strong>반려</strong>되기 전까지 <u>내용을 변경하거나 요청을 취소</u>할 수 있습니다.
		  		</div>
			</div>
			<div class="d-flex justify-content-end gap-2 pe-0">
				<button type="button" class="btn btn-md btn-secondary fw-bold" onclick="location.href='/order/modify/${order.order_code }'">발주변경</button>
				<button type="button" class="btn btn-md btn-danger fw-bold" onclick="return cancelOrder(${order.order_code}, false)">요청취소</button>
				
				<div class="vr mx-2"></div>
				<button type="button" class="btn btn-danger fw-bold" data-bs-toggle="modal" data-bs-target="#refuseModal">반려</button>
				<button type="button" class="btn btn-primary fw-bold" onclick="return approveOrder(${order.order_code})">승인</button>

			</div>
		</div>
  	</c:if>
  	
  	</div>
  	
  	<!-- 반려 사유 작성 모달 -->
  	<div class="modal fade" id="refuseModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="myModalLabel">사유 작성</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <textarea rows="4" class="form-control form-control-sm" placeholder="반려 사유를 작성해주세요" id="orderRefuseReason"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-danger fw-bold" onclick="return cancelOrder(${order.order_code}, true)">반려</button>
	      </div>
	    </div>
	  </div>
	</div>
</div>
</body>
</html>