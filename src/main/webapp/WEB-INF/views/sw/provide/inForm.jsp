<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>원재료 등록</title>
</head>
<body class="d-flex flex-column min-vh-100">

	<!-- HEADER -->
	<%@ include file="../../header.jsp"%>

	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="../../sidebar.jsp"%>

		<div class="d-flex flex-column flex-grow-1">
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
				<div class="d-flex justify-content-center mt-5">
					<h1>원재료 등록</h1>
				</div>
				<form action="${pageContext.request.contextPath}/provide/provideSave"
					method="post" class="container mt-4">

					<!-- 제품 선택 -->
					<div class="form-group row mb-3">
						<label for="product_won_code" class="col-sm-2 col-form-label">제품</label>
						<div class="col-sm-6">
							<select id="product_won_code" name="product_won_code"
								class="form-select" required>
								<option value="">-- 제품을 선택하세요 --</option>
								<c:forEach var="product" items="${productList}">
									<option value="${product.product_code}">${product.product_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<!-- 거래처 선택 -->
					<div class="form-group row mb-3">
						<label for="provide_client_code" class="col-sm-2 col-form-label">거래처</label>
						<div class="col-sm-6">
							<select id="provide_client_code" name="provide_client_code"
								class="form-select" required>
								<option value="">-- 거래처를 선택하세요 --</option>
								<c:forEach var="client" items="${clientList}">
									<option value="${client.client_code}">${client.client_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<!-- 공급 단위 입력 -->
					<div class="form-group row mb-4">
						<label for="provide_amount" class="col-sm-2 col-form-label">공급단위</label>
						<div class="col-sm-6">
							<input type="number" class="form-control" id="provide_amount"
								name="provide_amount" placeholder="공급단위를 입력하세요" required>
						</div>
					</div>

					<!-- 단가 입력 -->
					<div class="form-group row mb-3">
						<label for="current_danga" class="col-sm-2 col-form-label">단가(₩)</label>
						<div class="col-sm-6">
							<input type="number" class="form-control" id="current_danga"
								name="current_danga" placeholder="단가를 입력하세요" required>
						</div>
					</div>

					<!-- 버튼 -->
					<div class="form-group row">
						<div class="col-sm-8 text-center">
							<button type="submit" class="btn btn-primary btn-lg me-2">등록</button>
							<button type="reset" class="btn btn-secondary btn-lg">취소</button>
						</div>
					</div>
				</form>
			</main>

			<!-- FOOTER -->
			<%@ include file="../../footer.jsp"%>
		</div>
	</div>

</body>
</html>
