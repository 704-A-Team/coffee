<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 보고서</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { font-family: '맑은 고딕', sans-serif; background: #f5f5f5; }
        .report-card { max-width: 900px; margin: 24px auto; border: 1px solid #ddd; background: #fff; padding: 22px; }
        .report-title { text-align:center; font-weight:700; font-size:22px; margin-bottom:12px; }
        .divider { border-top: 1px solid #222; margin-bottom: 18px; }
        .sec-title { display:flex; align-items:center; gap:8px; margin-top:18px; margin-bottom:10px; }
        .sec-title .bar { width:4px; height:18px; background:#0d6efd; display:inline-block; }
        .form-label { width:170px; font-weight:600; }
        .read-value { padding-top:6px; }
        table.table-sm th, table.table-sm td { vertical-align: middle; text-align:center; }
        .totals-box { border-top:1px solid #e9ecef; padding-top:10px; margin-top:8px; }
        .btn-row { display:flex; gap:8px; justify-content:flex-start; margin-top:12px; }
        @media (max-width:575px){
            .form-label { width:100%; margin-bottom:4px; }
            .form-row-inline { flex-direction:column; gap:8px; }
        }
    </style>
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

      <div class="d-flex flex-column flex-grow-1">
         <main class="flex-grow-1 p-4">
        	<div class="report-card shadow-sm">
            <div class="report-title">생산 보고서</div>
            <div class="divider"></div>

            <!-- 보고 정보 -->
            <div class="sec-title"><span class="bar"></span><strong>보고 정보</strong></div>

            <div class="row g-2 mb-2 align-items-center">
                <div class="col-12 col-md-6">
                    <div class="d-flex align-items-center">
                        <div class="form-label">생산 신청 코드:</div>
                        <div class="read-value">${mfgRpDTO.mfg_code} (${mfgRpList[0].product_name})</div>
                    </div>
                </div>

                <div class="col-12 col-md-6">
                    <div class="d-flex align-items-center">
                        <div class="form-label">생산 요청 수량:</div>
                        <div class="read-value" id="requestedQty">${mfgRpList[0].mfg_amount}</div>
                    </div>
                </div>
            </div>

            <form id="mfgReportForm" action="${pageContext.request.contextPath}/km/mfgReportSubmit" method="post" class="w-100">
                <!-- Hidden Inputs -->
                <input type="hidden" name="mfgRpDTO.mfg_code" value="${mfgRpDTO.mfg_code}">
                <input type="hidden" name="mfgRpDTO.product_code" value="${mfgRpDTO.product_code}">
                <input type="hidden" name="mfgRpDTO.product_yield" value="${mfgRpList[0].product_yield}">
                <input type="hidden" name="mfgRpDTO.product_weight" value="${mfgRpList[0].product_weight}">
                <input type="hidden" name="mfgRpDTO.product_pack" value="${mfgRpList[0].product_pack}">
                <input type="hidden" id="mfgMatInput" name="mfgRpDTO.mfg_mat" value="0">
                <input type="hidden" id="mfgEndInput" name="mfgRpDTO.mfg_end" value="0">

                <div class="row g-2">
                    <div class="col-md-6 col-sm-12 mt-2">
                        <div class="d-flex align-items-center form-row-inline">
                            <div class="form-label">생산완료날짜:</div>
                            <input type="datetime-local" name="mfgRpDTO.mfg_end_date" class="form-control flex-grow-1" min="${magamNext}T00:00" required="required"/>
                        </div>
                    </div>

                    <div class="col-md-6 col-sm-12 mt-2">
                        <div class="d-flex align-items-center form-row-inline">
                            <div class="form-label">실제 완료 생산 수량:</div>
                            <input type="number" name="mfgRpDTO.mfg_qty" class="form-control flex-grow-1" min="0"/>
                        </div>
                    </div>
                </div>

                <!-- 원재료 사용 내역 -->
                <div class="sec-title"><span class="bar"></span><strong>원재료 사용 내역</strong></div>
                <div class="table-responsive">
                    <table class="table table-sm table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>원재료명</th>
                                <th>예상 사용량 (g)</th>
                                <th>실제 사용량 (g)</th>
                                <th>폐기량 (g)</th>
                                <th>비고</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${mfgRpList}" varStatus="status">
                                <tr>
                                    <td>${item.product_won_name}</td>
                                    <td>${item.target_amount}</td>
                                    <td>
                                        <input type="hidden" name="details[${status.index}].mfg_code" value="${mfgRpDTO.mfg_code}">
                                        <input type="hidden" name="details[${status.index}].product_code" value="${mfgRpDTO.product_code}">
                                        <input type="hidden" name="details[${status.index}].product_won_code" value="${item.product_won_code}">
                                        <input type="number" class="form-control form-control-sm real-input"
                                               name="details[${status.index}].real_amount"
                                               value="${item.target_amount}" min="0" max="${item.target_amount*2}"/>
                                    </td>
                                    <td>
                                        <input type="number" class="form-control form-control-sm trash-input"
                                               name="details[${status.index}].trash_amount"
                                               value="0" min="0" max="${item.target_amount*2}"/>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control form-control-sm"
                                               name="details[${status.index}].trash_contents"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- 총 투입 원재료 -->
                <div class="sec-title"><span class="bar"></span><strong>총 투입 원재료</strong></div>
                <div class="row">
                    <div class="col-6"></div>
                    <div class="col-6">
                        <div class="totals-box">
                            <div class="d-flex justify-content-between">
                                <div>총 예상 투입 원재료 중량:</div>
                                <div id="total_expected">0 g</div>
                            </div>
                            <div class="d-flex justify-content-between mt-2">
                                <div>총 실제 투입 원재료 중량:</div>
                                <div id="total_actual">0 g</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 비고 -->
                <div class="sec-title"><span class="bar"></span><strong>비고</strong></div>
                <textarea name="mfgRpDTO.note" class="form-control mb-2" rows="3" placeholder="특이사항/비고"></textarea>

                <!-- 버튼 -->
                <div class="btn-row">
                    <button type="submit" class="btn btn-success">보고 완료</button>
                    <a href="${pageContext.request.contextPath}/km/mfgReportList" class="btn btn-secondary">취소</a>
                </div>
            </form>
        </div>
       </main>
      <%@ include file="../footer.jsp" %>
    </div>
</div>

<!-- 부트스트랩 모달 -->
<div class="modal fade" id="yieldModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">알림</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body" id="yieldModalBody"></div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function showYieldModal(msg) {
    document.getElementById('yieldModalBody').innerText = msg;
    var modal = new bootstrap.Modal(document.getElementById('yieldModal'));
    modal.show();
}

function calcTotalsAndMfgEnd() {
    const qtyInputElem = document.querySelector('input[name="mfgRpDTO.mfg_qty"]');
    let qtyInput = parseInt(qtyInputElem.value) || 0;
    const requestedQty = parseInt(document.getElementById('requestedQty').innerText) || 0;

    // 요청 수량 초과 방지
    if(qtyInput > requestedQty) {
        qtyInput = requestedQty;
        qtyInputElem.value = qtyInput;
        showYieldModal("생산 완료 수량은 요청 수량을 넘을 수 없습니다!");
    }

    let totalExpected = 0;
    let totalActual = 0;

    document.querySelectorAll('table tbody tr').forEach(row => {
        const expected = parseFloat(row.cells[1].innerText.replace(/,/g,'')) || 0;
        let actual = parseFloat(row.querySelector('.real-input')?.value || 0);
        let trash = parseFloat(row.querySelector('.trash-input')?.value || 0);

        // 입력값이 너무 크면 자동 조정
        if(actual + trash > expected*2) {
            actual = expected;
            trash = 0;
            row.querySelector('.real-input').value = actual;
            row.querySelector('.trash-input').value = trash;
            showYieldModal("원재료 사용량이 비정상적으로 높습니다. 값이 조정되었습니다.");
        }

        totalExpected += expected;
        totalActual += (actual + trash);
    });

    document.getElementById('total_expected').innerText = totalExpected.toLocaleString() + " g";
    document.getElementById('total_actual').innerText = totalActual.toLocaleString() + " g";

    document.getElementById('mfgMatInput').value = totalActual;

    // mfg_end 계산 및 수율 100% 초과 방지
    const productPack = parseInt(document.querySelector('input[name="mfgRpDTO.product_pack"]').value) || 0;
    const productWeight = parseInt(document.querySelector('input[name="mfgRpDTO.product_weight"]').value) || 0;
    let mfgEnd = qtyInput * productPack * productWeight;

    if(totalActual > 0 && mfgEnd > totalActual) {
        mfgEnd = totalActual;
        qtyInput = Math.floor(totalActual / (productPack * productWeight));
        qtyInputElem.value = qtyInput;
        showYieldModal("수율이 100%를 초과할 수 없습니다! 완료 수량이 조정되었습니다.");
    }

    document.getElementById('mfgEndInput').value = mfgEnd;
}

window.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.real-input, .trash-input').forEach(inp => inp.addEventListener('input', calcTotalsAndMfgEnd));
    document.querySelector('input[name="mfgRpDTO.mfg_qty"]').addEventListener('input', calcTotalsAndMfgEnd);
    calcTotalsAndMfgEnd();
});
</script>
</body>
</html>