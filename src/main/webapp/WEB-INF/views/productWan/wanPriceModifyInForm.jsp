<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 가격 조정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        .section-title {
            font-size: 1.5rem;
            font-weight: bold;
            border-left: 4px solid #6f4e37;
            padding-left: 10px;
            margin-bottom: 20px;
            color: #6f4e37;
        }
        .status-up { color: red; font-weight: bold; }
        .status-down { color: blue; font-weight: bold; }
        .status-same { color: gray; font-weight: bold; }
        .status-none { color: black; font-style: italic; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>
<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>
    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title border-start border-4 ps-2 mb-4" style="border-color:#0d6efd !important;">
                    완제품 가격 조정
                </div>

                <div class="row">
                    <!-- 가격 조정 카드 -->
                    <div class="col-md-5">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white fw-bold">
                                가격 조정
                            </div>
                            <div class="card-body">
                                <form id="priceForm" action="${pageContext.request.contextPath}/km/wanPriceModify" method="post">
                                    <input type="hidden" name="product_code" value="${param.product_code}" />
                                    <div class="mb-3">
									    <label for="newPrice" class="form-label">새 가격</label>
									    <input type="number" step="0.01" class="form-control" id="newPrice" name="price" 
									           placeholder="${tot_wan_price}원 이상으로 입력하세요" required">
									</div>
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">적용 시작일</label>
                                        <input type="datetime-local" step="1" class="form-control" id="startDate" name="start_date" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary">가격 조정</button>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- 가격 히스토리 카드 -->
                    <div class="col-md-7">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white fw-bold">
                                가격 히스토리
                            </div>
                            <div class="card-body">
                                <table class="table table-bordered align-middle text-center">
                                    <thead class="table-light">
                                        <tr>
                                            <th>시작일</th>
                                            <th>종료일</th>
                                            <th>가격</th>
                                            <th>상태</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="history" items="${priceHistory}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty history.start_date}">
                                                            <c:set var="startDateStr" value="${history.start_date}" />
                                                            ${fn:substring(fn:replace(startDateStr, 'T', ' '), 0, 19)}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${history.end_date == '9999-12-31'}">-</c:when>
                                                        <c:when test="${not empty history.end_date}">
                                                            <c:set var="endDateStr" value="${history.end_date}" />
                                                            ${fn:substring(fn:replace(endDateStr, 'T', ' '), 0, 19)}
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${history.price}원</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${status.index == priceHistory.size() - 1}">
                                                            <span class="status-none">-</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="next" value="${priceHistory[status.index + 1]}" />
                                                            <c:choose>
                                                                <c:when test="${history.price > next.price}">
                                                                    <span class="status-up">▲ 상승</span>
                                                                </c:when>
                                                                <c:when test="${history.price < next.price}">
                                                                    <span class="status-down">▼ 하락</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="status-same">동결</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty priceHistory}">
                                            <tr>
                                                <td colspan="4">가격 히스토리가 없습니다.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@ include file="../footer.jsp" %>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('#priceForm');
    const newPriceInput = document.getElementById('newPrice');
    const startDateInput = document.getElementById('startDate');
    const minPrice = Number('${tot_wan_price}'); // 서버에서 전달된 최소 가격

    console.log('minPrice',minPrice);
    form.addEventListener('submit', function (event) {
        console.log("폼 submit 이벤트 발생 ✅");
        console.log("newPriceInput.value:", newPriceInput.value);
        const enteredPrice = parseFloat(newPriceInput.value);
        console.log("enteredPrice:", enteredPrice, "minPrice:", minPrice);
        // 가격 검증 (minPrice 이하일 경우 차단)
        if (isNaN(enteredPrice) || enteredPrice < minPrice) {
		    event.preventDefault();
		    event.stopPropagation();   // 이벤트 전파도 차단
		    showModal(`가격은 최소 ${minPrice}원 이상 입력해야 합니다.`);
		    return false;              // 폼 제출 완전히 차단
		}

        // 초 단위 붙여서 전송 (datetime-local에 초 없는 경우 보정)
        if (startDateInput.value && !startDateInput.value.includes(":")) {
            const now = new Date();
            const seconds = String(now.getSeconds()).padStart(2, '0');
            startDateInput.value = startDateInput.value + ":" + seconds;
        }
    });

    // 모달 생성 함수
    function showModal(minPrice) {
        // 기존 모달 제거
        const existingModal = document.getElementById('priceModal');
        if (existingModal) existingModal.remove();

        const modalHtml = `
        <div class="modal fade" id="priceModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title text-danger">가격 오류</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                    </div>
                    <div class="modal-body fw-bold text-center">가격을 재입력 하십시오.</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
                    </div>
                </div>
            </div>
        </div>`;
        document.body.insertAdjacentHTML('beforeend', modalHtml);
        const modal = new bootstrap.Modal(document.getElementById('priceModal'));
        modal.show();
    }
});
</script>
</body>
</html>