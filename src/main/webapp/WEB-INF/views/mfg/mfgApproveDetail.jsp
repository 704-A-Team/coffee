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
                        <input type="date" 
                               class="form-control mb-1 row-due-date"
                               value="<c:out value='${item.mfg_due_date != null ? fn:substring(item.mfg_due_date,0,10) : magamNext}'/>"
                               required="required"
                               min="${magamNext}"
                               <c:if test="${item.mfg_status != 1 && item.mfg_status != 4}">readonly</c:if> />
                    </td>
                    <td>
                        <c:if test="${item.mfg_status == 1  || item.mfg_status == 4}">
                            <button type="button" class="btn btn-success btn-sm mb-1"
                                    onclick="checkAndApprove(this, ${item.mfg_code}, ${item.product_code})">승인</button>
                            <button type="button" class="btn btn-danger btn-sm"
                                    onclick="submitReject(this, ${item.mfg_code}, ${item.product_code})">거부</button>
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

        <!-- 모달 -->
        <div class="modal fade" id="shortageModal" tabindex="-1" aria-labelledby="shortageModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="shortageModalLabel">원재료 부족</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body" id="shortageBody"></div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
              </div>
            </div>
          </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
 <script>
  // 날짜를 'YY/MM/DD' 형식으로 변환
  function formatYYMMDD(dateStr){
      const [year, month, day] = dateStr.split('-');
      return year.slice(-2) + '/' + month + '/' + day;
  }

  // 승인 전 원재료 체크 + 승인
  function checkAndApprove(btn, mfg_code, product_code) {
      const row = btn.closest('tr');
      const dueDateInput = row.querySelector('.row-due-date');
      const contentsInput = row.querySelector('.row-contents');

      if(!dueDateInput.value){
          alert('완료 예정일을 반드시 입력하세요.');
          dueDateInput.focus();
          return;
      }

      const dueDateFormatted = formatYYMMDD(dueDateInput.value);

      // Ajax 호출
      fetch('${pageContext.request.contextPath}/km/checkApprove', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
              mfg_code: mfg_code,
              product_code: product_code,
              mfg_due_date: dueDateFormatted,
              mfg_contents: contentsInput.value
          })
      })
      .then(res => res.json())
      .then(data => {
          const modalEl = document.getElementById('shortageModal');
          const modalBody = document.getElementById('shortageBody');
          const modal = new bootstrap.Modal(modalEl);

          if(data.result_status === 1){
              // 충분 -> 승인 가능 모달
              modalBody.innerHTML = '<p>원재료가 충분합니다. 승인 가능합니다.</p>';
              modal.show();

              // 승인 버튼 활성화
              btn.disabled = false;

              // 승인 hidden input 세팅 후 submit
              setHiddenAndSubmit(mfg_code, product_code, 4, dueDateFormatted, contentsInput.value);
          } else {
              // 부족 -> 모달 표시, 승인 버튼 비활성화
              
               console.log('data.result_status', data.result_status);
               console.log('data.listWonCodeLackDTO', data.listWonCodeLackDTO);
				// data = resultMap
				let html = '<ul>';
				
				data.listWonCodeLackDTO.forEach(won => {              // 실제 원재료 정보
				    // html += `<li>${won.product_name}: 부족 ${won.storege}</li>`;
				     html += "<li>"+won.product_name + ": 부족 " + won.storege +"</li>";
				});
				
				html += '</ul>';
				
				// 모달 출력
				const modalBody = document.getElementById('shortageBody');
				modalBody.innerHTML = html;
				const modal = new bootstrap.Modal(document.getElementById('shortageModal'));
				modal.show();

			    // 승인 버튼 비활성화
			    btn.disabled = true;
          }
      })
      .catch(err => {
          console.error(err);
          alert('서버 오류 발생!');
      });
  }

  // 거부 버튼
  function submitReject(btn, mfg_code, product_code){
	  const row = btn.closest('tr'); 
	  const contentsInput = row.querySelector('.row-contents');  // 비고 textarea

	  setHiddenAndSubmit(mfg_code, product_code, 3, '', contentsInput.value);
  }

  // hidden input 세팅 + form submit
  function setHiddenAndSubmit(mfg_code, product_code, status, dueDate, contents){
      const form = document.getElementById('approveForm');
      ['mfg_code','product_code','mfg_status','mfg_due_date','mfg_contents'].forEach(name=>{
          const old = form.querySelector(`input[name="${name}"]`);
          if(old) old.remove();
      });

      const inputs = [
          {name:'mfg_code', value:mfg_code},
          {name:'product_code', value:product_code},
          {name:'mfg_status', value:status},
          {name:'mfg_due_date', value:dueDate},
          {name:'mfg_contents', value:contents}
      ];

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