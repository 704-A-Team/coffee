<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>생산 신청 승인 수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
<style>
    .form-section-title {
        border-left: 4px solid #198754;
        padding-left: 10px;
        margin-bottom: 20px;
        font-weight: 600;
        font-size: 2rem;
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>
<div class="d-flex flex-grow-1">
<%@ include file="../sidebar.jsp" %>

<div class="d-flex flex-column flex-grow-1">
<main class="flex-grow-1 p-4">
<div class="container mt-3">

    <div class="form-section-title">생산 신청 승인 수정</div>

    <!-- 승인 내역 테이블 -->
    <table class="table table-bordered">
        <thead class="table-light">
            <tr>
                <th>제품 코드</th>
                <th>생산 수량</th>
                <th>요청일</th>
                <th>현재 상태</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${approveMfg}">
                <tr>
                    <td>${item.product_code}</td>
                    <td>${item.mfg_amount}</td>
                    <td>
                      ${fn:substring(item.mfg_request_date, 0, 10)}<br/>
                      ${fn:substring(item.mfg_request_date, 11, 16)}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${item.mfg_status == 1}">검토중</c:when>
                            <c:when test="${item.mfg_status == 3}">거부</c:when>
                            <c:when test="${item.mfg_status == 4}">진행중</c:when>
                            <c:when test="${item.mfg_status == 5}">승인</c:when>
                            <c:otherwise>기타</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${item.mfg_contents}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 승인 수정 폼 -->
    <form id="approveForm" action="${pageContext.request.contextPath}/km/mfgApproveUpdate" method="post" class="mt-4">
        <input type="hidden" name="mfg_code" value="${approveMfg[0].mfg_code}" />

        <div class="mb-3 row align-items-center">
            <label class="col-sm-2 col-form-label">완료 예정일 (날짜)</label>
            <div class="col-sm-4">
                <input type="date" class="form-control" name="mfg_due_date_date"
                    value="<c:out value='${fn:substring(approveMfg[0].mfg_due_date, 0, 10)}'/>" />
            </div>
        </div>

        <div class="mb-3 row align-items-center">
            <label class="col-sm-2 col-form-label">완료 예정일 (시간)</label>
            <div class="col-sm-4">
                <input type="time" class="form-control" name="mfg_due_date_time"
                    value="<c:out value='${fn:substring(approveMfg[0].mfg_due_date, 11, 16)}'/>" />
            </div>
        </div>

        <div class="mb-3 row align-items-center">
            <label class="col-sm-2 col-form-label">승인 상태</label>
            <div class="col-sm-4">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="mfg_status" id="status_approve" value="5"
                        <c:if test="${approveMfg[0].mfg_status == 5}">checked</c:if> />
                    <label class="form-check-label" for="status_approve">승인</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="mfg_status" id="status_reject" value="3"
                        <c:if test="${approveMfg[0].mfg_status == 3}">checked</c:if> />
                    <label class="form-check-label" for="status_reject">거부</label>
                </div>
            </div>
        </div>

        <div class="mb-3 row">
            <label for="mfg_contents" class="col-sm-2 col-form-label">비고</label>
            <div class="col-sm-8">
                <textarea class="form-control" id="mfg_contents" name="mfg_contents" rows="3">${approveMfg[0].mfg_contents}</textarea>
            </div>
        </div>

        <div class="d-flex justify-content-end gap-2">
            <button type="submit" class="btn btn-success">수정 저장</button>
            <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#cancelModal">취소</button>
        </div>
    </form>

</div>
</main>
<%@ include file="../footer.jsp" %>
</div>
</div>

<!-- 취소 확인 모달 -->
<div class="modal fade" id="cancelModal" tabindex="-1" aria-labelledby="cancelModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="cancelModalLabel">수정 취소 확인</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
      </div>
      <div class="modal-body">
        수정하지 않고 목록으로 돌아가시겠습니까?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니오</button>
        <button type="button" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/km/mfgList'">예</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>