<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주서</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<style>
:root {
	--main-brown: #6f4e37;
	--soft-brown: #bfa08e;
	--dark-brown: #4e342e;
}
body { background-color: #f9f5f1; }
.doc-wrapper { background:#fff; border:1px solid #eee; border-radius:10px; padding:24px; }
.form-label { font-weight:600; }
.form-control[readonly], .bg-light { background:#f7f7f7 !important; }
.form-section-title {
	border-left: 5px solid var(--main-brown);
	padding-left: 12px;
	margin-bottom: 24px;
	font-weight: 700;
	font-size: 1.8rem;
	color: var(--dark-brown);
}
.doc-title { background:#fff; border:1px solid #eee; border-radius:10px; padding:16px 20px; }
.doc-title h4 { margin:0; font-weight:700; }
.doc-sub { font-size:.95rem; }
.doc-chip { display:inline-block; padding:2px 10px; border-radius:999px; background:#f1f1f1; margin-left:6px; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

	<%@ include file="../../header.jsp"%>

	<div class="d-flex flex-grow-1">
		<%@ include file="../../sidebar.jsp"%>

		<div class="d-flex flex-column flex-grow-1">
			<main class="flex-grow-1 p-4">
				<div class="container mt-3">
					<h4 class="text-center mb-4 fw-bold">발주서</h4>

					<div class="doc-wrapper">

						<!-- ================= 상단: 좌(발주 정보) / 우(거래처 정보) ================= -->
						<div class="row mb-4">
							<!-- 좌: 발주 정보 -->
							<div class="col-6 d-flex border border-end-0 p-3">
								<div class="flex-fill pe-3 w-100">
									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">상태</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].statusName}">${purchaseDetailList[0].statusName}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">발주 코드</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].purchase_code}">${purchaseDetailList[0].purchase_code}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">등록일</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].purchase_reg_date}">
													<fmt:formatDate value="${purchaseDetailList[0].purchase_reg_date}" pattern="yyyy-MM-dd HH:mm:ss" />
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">입고일</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].purchase_ipgo_date}">
													<fmt:formatDate value="${purchaseDetailList[0].purchase_ipgo_date}" pattern="yyyy-MM-dd HH:mm:ss" />
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">승인자</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].empPermName}">${purchaseDetailList[0].empPermName}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

									<div class="mb-2 d-flex">
										<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">비고</label>
										<div class="form-control form-control-sm bg-light">
											<c:choose>
												<c:when test="${not empty purchaseDetailList[0].purchase_refuse}">${purchaseDetailList[0].purchase_refuse}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</div>
									</div>

								</div>
							</div>

							<!-- 우: 거래처 정보 -->
							<div class="col-6 border p-3">
								<div class="mb-2 d-flex">
									<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">거래처명</label>
									<div class="form-control form-control-sm bg-light">
										<c:choose>
											<c:when test="${not empty purchaseDetailList[0].clientName}">${purchaseDetailList[0].clientName}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="mb-2 d-flex">
									<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">대표자명</label>
									<div class="form-control form-control-sm bg-light">
										<c:choose>
											<c:when test="${not empty purchaseDetailList[0].bossName}">${purchaseDetailList[0].bossName}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="mb-2 d-flex">
									<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">사업자등록번호</label>
									<div class="form-control form-control-sm bg-light">
										<c:choose>
											<c:when test="${not empty purchaseDetailList[0].saupNum}">${purchaseDetailList[0].saupNum}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="mb-2 d-flex">
									<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">주소</label>
									<div class="form-control form-control-sm bg-light">
										<c:choose>
											<c:when test="${not empty purchaseDetailList[0].clientAddress}">${purchaseDetailList[0].clientAddress}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="mb-2 d-flex">
									<label class="form-label me-2 mb-0 col-3" style="white-space: nowrap;">전화번호</label>
									<div class="form-control form-control-sm bg-light">
										<c:choose>
											<c:when test="${not empty purchaseDetailList[0].clientTel}">${purchaseDetailList[0].clientTel}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</div>
								</div>

								<!-- 필요 시 추가 정보들 여기에 더 배치 -->
							</div>
						</div>

						<!-- ================= 하단: 품목 리스트 ================= -->
						<div class="row">
							<div class="col-12 border p-3">
								<div class="row g-2 fw-bold text-center border-bottom pb-2 mb-2">
									<div class="col-3">품목명</div>
									<div class="col-2">공급단위</div>
									<div class="col-2">단가</div>
									<div class="col-2">수량</div>
									<div class="col-2">금액</div>
								</div>

								<c:if test="${not empty purchaseDetailList}">
									<c:set var="totalPrice" value="0" />
									<c:forEach var="d" items="${purchaseDetailList}">
										<c:set var="rowTotal"
											value="${ (d.purchase_danga / (d.provideAmount == 0 ? 1 : d.provideAmount)) * d.purchase_amount }" />
										<div class="row g-2 mb-2 align-items-center">
											<div class="col-3">
												<div class="form-control form-control-sm bg-light">
													${d.productName} (${d.product_won_code})
												</div>
											</div>
											<div class="col-2">
												<div class="form-control form-control-sm bg-light">
													<fmt:formatNumber value="${d.provideAmount}" type="number" /> ${d.unitName}
												</div>
											</div>
											<div class="col-2">
												<div class="form-control form-control-sm bg-light">
													<fmt:formatNumber value="${d.purchase_danga}" type="number" />
												</div>
											</div>
											<div class="col-2">
												<div class="form-control form-control-sm bg-light">
													<fmt:formatNumber value="${d.purchase_amount}" type="number" /> ${d.unitName}
												</div>
											</div>
											<div class="col-2">
												<div class="form-control form-control-sm bg-light">
													<fmt:formatNumber value="${rowTotal}" type="number" />
												</div>
											</div>
										</div>
										<c:set var="totalPrice" value="${totalPrice + rowTotal}" />
									</c:forEach>

									<div class="col text-end mt-2">
										<strong>총액:&nbsp;</strong>
										<span><fmt:formatNumber value="${totalPrice}" type="number" /></span> 원
									</div>
								</c:if>

							</div>
						</div>
						<!-- ================= /하단: 품목 리스트 ================= -->

					</div> <!-- /.doc-wrapper -->

					<!-- ===== 버튼 영역: 박스 밖 ===== -->
					<div class="d-flex justify-content-end gap-2 mt-4">
						<c:if test="${isApprovable}">
							<form action="${pageContext.request.contextPath}/sw/purchaseApprove" method="post" class="m-0">
								<input type="hidden" name="purchase_code" value="${purchaseDetailList[0].purchase_code}">
								<button type="submit" class="btn btn-primary">승인</button>
							</form>
							<button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#refuseModal">거부</button>
						</c:if>
						<a href="javascript:history.back()" class="btn btn-outline-secondary">목록</a>
					</div>

					<!-- 거부 사유 모달 -->
					<c:if test="${isApprovable}">
						<div class="modal fade" id="refuseModal" tabindex="-1" aria-labelledby="refuseModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<form action="${pageContext.request.contextPath}/sw/purchaseRefuse" method="post">
									<div class="modal-content">
										<div class="modal-header bg-danger text-white">
											<h5 class="modal-title" id="refuseModalLabel">거부 사유 입력</h5>
											<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
										</div>
										<div class="modal-body">
											<input type="hidden" name="purchase_code" value="${purchaseDetailList[0].purchase_code}">
											<div class="mb-3">
												<label for="purchase_refuse" class="form-label">거부 사유</label>
												<textarea class="form-control" id="purchase_refuse" name="purchase_refuse" rows="4" required></textarea>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
											<button type="submit" class="btn btn-danger">거부 확정</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</c:if>

				</div> <!-- /.container -->
			</main>

			<%@ include file="../../footer.jsp"%>
		</div>
	</div>
	
</body>
</html>
