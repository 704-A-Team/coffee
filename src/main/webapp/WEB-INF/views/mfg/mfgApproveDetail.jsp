<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>생산의뢰서 수정</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    @page { size: A4; margin: 20mm; }
    .title { text-align: center; font-size: 24px; font-weight: bold; margin: 10px 0 20px 0; }
    .table-wrapper { display: flex; justify-content: center; margin-bottom: 30px; }
    table { width: auto; min-width: 900px; border: 1px solid #ccc; border-collapse: collapse; margin-bottom: 20px; }
    th, td { border: 1px solid #ccc; padding: 12px 10px; font-size: 15px; text-align: center; min-width: 100px; }
    th { background-color: #f0f0f0; }
    .left-align { text-align: left; }
    .a4page { width: 210mm; min-height: 297mm; margin: 10mm auto; padding: 10mm 15mm; background: white; box-sizing: border-box; }
    @media print {
      body * { visibility: hidden; }
      .a4page, .a4page * { visibility: visible; }
      .a4page { position: absolute; left: 0; top: 0; width: 210mm; min-height: 297mm; margin: 0; padding: 0; }
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
        <div class="title">생산의뢰서 수정</div>

        <form id="approveForm" action="${pageContext.request.contextPath}/km/mfgApproveUpdate" method="post">
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>제품코드</th>
                  <th>제품명</th>
                  <th>수량</th>
                  <th>단위</th>
                  <th>요청일</th>
                  <th>상태</th>
                  <th>완료예정일</th>
                  <th>승인/거부</th>
                  <th>비고</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="item" items="${mfgDetailList}" varStatus="status">
                  <tr>
                    <td>${item.product_code}</td>
                    <td class="left-align">${item.product_name}</td>
                    <td>${item.mfg_amount}</td>
                    <td>${item.cd_contents}</td>
                    <td>${item.mfg_request_date.toLocalDate()}</td>
                    <td>
                      <c:choose>
                        <c:when test="${item.mfg_status == 1}">요청</c:when>
                        <c:when test="${item.mfg_status == 4}">승인</c:when>
                        <c:when test="${item.mfg_status == 3}">거부</c:when>
                        <c:when test="${item.mfg_status == 5}">완료</c:when>
                        <c:when test="${item.mfg_status == 6}">마감</c:when>
                        <c:otherwise>기타</c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <input type="date" class="form-control mb-1 row-due-date"
                             value="${fn:substring(item.mfg_due_date,0,10)}"
                             <c:if test="${item.mfg_status != 1 && item.mfg_status != 4}">readonly</c:if> />
                    </td>
                    <td>
                      <c:if test="${item.mfg_status == 1  || item.mfg_status == 4}">
                        <button type="button" class="btn btn-success btn-sm mb-1"
                                onclick="submitStatus(this, ${item.mfg_code}, ${item.product_code}, 4)">승인</button>
                        <button type="button" class="btn btn-danger btn-sm"
                                onclick="submitStatus(this, ${item.mfg_code}, ${item.product_code}, 3)">거부</button>
                      </c:if>
                    </td>
                    <td>
                      <textarea class="form-control row-contents"
                                rows="2"
                                <c:if test="${item.mfg_status != 1 && item.mfg_status != 4}">readonly</c:if>>${item.mfg_contents}</textarea>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </form>

        <div class="d-flex justify-content-end gap-2 mt-3">
          <button type="button" class="btn btn-secondary"
                  onclick="location.href='${pageContext.request.contextPath}/km/mfgList'">목록</button>
        </div>

        <script>
        function submitStatus(btn, mfg_code, product_code, status) {
        	  const row = btn.closest('tr');
        	  const dueDateInput = row.querySelector('.row-due-date');
        	  const contentsInput = row.querySelector('.row-contents');
        	  const form = document.getElementById('approveForm');

        	  // 기존 hidden 제거
        	  ['mfg_code','product_code','mfg_status','mfg_due_date','mfg_contents'].forEach(name=>{
        	    const old = form.querySelector(`input[name="${name}"]`);
        	    if(old) old.remove();
        	  });

        	  // 승인일 때 날짜 필수 체크
        	  if (status === 4) {
        	    let dueDateValue = dueDateInput?.value || '';
        	    if (!dueDateValue) {
        	      // 오늘 날짜로 자동 설정
        	      const today = new Date();
        	      dueDateValue = today.toISOString().slice(0, 10); // YYYY-MM-DD
        	      dueDateInput.value = dueDateValue;
        	    }
        	  }

        	  // 새로운 hidden 생성
        	  const inputs = [
        	    {name:'mfg_code', value:mfg_code},
        	    {name:'product_code', value:product_code},
        	    {name:'mfg_status', value:status}
        	  ];

        	  if (status === 4) { // 승인
        	    inputs.push({name:'mfg_due_date', value:(dueDateInput?.value || '')});
        	    inputs.push({name:'mfg_contents', value:(contentsInput?.value || '')});
        	  } else if (status === 3) { // 거부
        	    inputs.push({name:'mfg_due_date', value:''});
        	    inputs.push({name:'mfg_contents', value:(contentsInput?.value || '')});
        	  }

        	  inputs.forEach(obj=>{
        	    const input = document.createElement('input');
        	    input.type = 'hidden';
        	    input.name = obj.name;
        	    input.value = obj.value;
        	    form.appendChild(input);
        	  });

        	  form.submit();
        	}
        </script>

      </main>
    </div>
  </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>