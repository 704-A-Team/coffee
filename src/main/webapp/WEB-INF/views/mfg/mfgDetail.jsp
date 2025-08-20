<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>생산의뢰서</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    @page {
      size: A4;
      margin: 20mm;
    }
    .title {
      text-align: center;
      font-size: 24px;
      font-weight: bold;
      margin: 10px 0 20px 0;
    }
    table {
      width: 100%;
      border: 2px solid #666;
      border: 1px solid #ccc;
      border-collapse: collapse;
      margin-bottom: 20px;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 8px;
      font-size: 14px;
      text-align: center;
    }
    th {
      background-color: #f0f0f0;
    }
    .left-align {
      text-align: left;
    }
    .footer-cell {
      height: 80px;
      vertical-align: top;
    }
    .a4page {
	    width: 210mm;
	    min-height: 297mm;
	    margin: 10mm auto;
	    padding: 10mm 15mm;   /* 20mm; */
	    box-shadow: 0 0 5px rgba(0,0,0,0.1);
	    background: white;
	    box-sizing: border-box;
	  }
	
	  @media print {
	    body * {
	      visibility: hidden;
	    }
	    .a4page, .a4page * {
	      visibility: visible;
	    }
	    .a4page {
	      position: absolute;
	      left: 0;
	      top: 0;
	      width: 210mm;
	      min-height: 297mm;
	      margin: 0;
	      padding: 0;
	      box-shadow: none;
	    }
  }
  </style>
</head>
<body class="d-flex flex-column min-vh-100">
		<%@ include file="../header.jsp" %>
	<div class="d-flex flex-grow-1">
		<%@ include file="../sidebar.jsp" %>
	  
    
    <div class="d-flex flex-column flex-grow-1">
    <div class="a4page"> 
	<main class="flex-grow-1 p-4">
		<div class="title">생산의뢰서</div>
<table style="border-collapse: collapse; width: 100%; border: 1px solid #ccc;">
  <!-- 등록일자 한 줄 차지 -->
  <tr>
    <th style="width: 100px; border: 1px solid #ccc; background-color: #f0f0f0;">등록일자</th>
    <td colspan="7" style="border: 1px solid #ccc; text-align: left; padding-left: 10px;">
      ${mfgDetailList[0].mfg_reg_date.toLocalDate()}
    </td>
  </tr>

  <!-- 문서번호와 성명 수직 정렬로 배치 -->
  <tr>
    <th style="width: 100px; border: 1px solid #ccc; background-color: #f0f0f0;">문서번호</th>
    <td style="border: 1px solid #ccc; text-align: left;" colspan="3">${mfgDetailList[0].mfg_code}</td>
    <th style="width: 100px; border: 1px solid #ccc; background-color: #f0f0f0;">성명</th>
    <td style="border: 1px solid #ccc; text-align: left;" colspan="2">${mfgDetailList[0].emp_name}</td>
  </tr>

  <!-- 소속 -->
  <tr>
    <th style="border: 1px solid #ccc; background-color: #f0f0f0;">소속</th>
    <td colspan="7" style="border: 1px solid #ccc; text-align: left; padding-left: 10px;">
      ${mfgDetailList[0].dept_name}
    </td>
  </tr>

  <!-- 사원 전화번호 -->
  <tr>
    <th style="border: 1px solid #ccc; background-color: #f0f0f0;">전화번호</th>
    <td colspan="7" style="border: 1px solid #ccc; text-align: left; padding-left: 10px;">
      ${mfgDetailList[0].emp_tel}
    </td>
  </tr>
</table>

				
				  <table>
				    <thead>
				      <tr>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">제품코드</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">제품명</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">수량</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">단위</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">요청일</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">상태</th>
				        <th style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">완료예정일</th>
				      </tr>
				    </thead>
				    <tbody>
				      <c:forEach var="item" items="${mfgDetailList}">
				        <tr>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">${item.product_code}</td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;" class="left-align">${item.product_name}</td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">${item.mfg_amount}</td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">${item.cd_contents}</td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">${item.mfg_request_date.toLocalDate()}</td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">
				            <c:choose>
				              <c:when test="${item.mfg_status == 1}">요청</c:when>
				              <c:when test="${item.mfg_status == 4}">승인</c:when>
				              <c:when test="${item.mfg_status == 3}">거부</c:when>
				              <c:when test="${item.mfg_status == 5}">완료</c:when>
				              <c:when test="${item.mfg_status == 6}">마감</c:when>
				              <c:otherwise>기타</c:otherwise>
				            </c:choose>
				          </td>
				          <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">${item.mfg_due_date}</td>
				        </tr>
				      </c:forEach>
				    </tbody>
				  </table>
				
				<table style="border-collapse: collapse; width: 100%; border: 1px solid #ccc;">
				  <tr>
				    <th style="width: 120px; border: 1px solid #ccc; background-color: #f0f0f0; 
				               text-align: center; vertical-align: middle;" 
				        rowspan="${fn:length(mfgDetailList) + 1}">
				      비고
				    </th>
				    <th style="width: 200px; border: 1px solid #ccc; background-color: #f0f0f0;
				               text-align: center; vertical-align: middle;">
				      제품명
				    </th>
				    <th style="border: 1px solid #ccc; background-color: #f0f0f0;
				               text-align: center; vertical-align: middle;">
				      제품별 특이사항
				    </th>
				  </tr>
				  <c:forEach var="item" items="${mfgDetailList}">
				    <tr>
				      <td style="border: 1px solid #ccc; text-align: center; vertical-align: middle;">
				        ${item.product_name}
				      </td>
				      <td style="border: 1px solid #ccc; text-align: left; vertical-align: middle; padding-left: 8px;">
				        ${item.mfg_contents}
				      </td>
				    </tr>
				  </c:forEach>
				</table>


		</main>
		<div style="text-align: right; margin-top: 30px;">
		  <form action="/km/mfgUpdateForm" method="get" style="display: inline-block;">
		    <input type="hidden" name="mfg_code" value="${mfgDetailList[0].mfg_code}" />
		    <button type="submit" class="btn btn-primary">수정</button>
		  </form>
		  
		    <button type="button" class="btn btn-secondary" style="margin-left: 8px;"
          		onclick="location.href='${pageContext.request.contextPath}/km/mfgList'">
		    	목록
		  	</button>
		</div>
		
		</div>
	</div>
	</div>
	<%@ include file="../footer.jsp" %>
	<!-- 모달 HTML -->
<div class="modal fade" id="alertModal" tabindex="-1" aria-labelledby="alertModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-warning">
        <h5 class="modal-title" id="alertModalLabel">알림</h5>
      </div>
      <div class="modal-body">
        ${modalMessage}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

<!-- 모달 자동 실행 스크립트 -->
<c:if test="${not empty modalMessage}">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    window.onload = function () {
      var alertModal = new bootstrap.Modal(document.getElementById('alertModal'));
      alertModal.show();
    }
  </script>
</c:if>
</body>
</html>