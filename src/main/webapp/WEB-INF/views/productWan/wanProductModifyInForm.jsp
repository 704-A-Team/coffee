<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>완제품 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
    </style>
	<script>
	function deleteImage(productCode, ord) {
	    if(confirm("이 이미지를 삭제할까요?")) {
	        fetch('${pageContext.request.contextPath}/km/deleteWanImage', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	                // CSRF 토큰 필요시 추가
	            },
	            body: JSON.stringify({
	                product_code: productCode,
	                ord: ord
	            })
	        })
	        .then(response => {
	            if(response.ok) {
	                alert('이미지가 삭제되었습니다.');
	                location.reload();
	            } else {
	                alert('삭제 실패! 다시 시도해주세요.');
	            }
	        })
	        .catch(error => {
	            console.error('삭제 오류:', error);
	            alert('삭제 중 오류가 발생했습니다.');
	        });
	    }
	}
	</script>
</head>
<body class="d-flex flex-column min-vh-100">

	<%@ include file="../header.jsp" %>
	<div class="d-flex flex-grow-1">
		<%@ include file="../sidebar.jsp" %>
		<div class="d-flex flex-column flex-grow-1">
			<main class="flex-grow-1 p-4">
				<div class="container mt-5">
				  <div class="form-section-title">완제품 수정</div>
					<c:if test="${not empty wanAndRcpModifyDTO}">
					  <!-- 가장 첫번째 완제품 객체를 꺼내서 변수에 저장 -->
					  <c:set var="product" value="${wanAndRcpModifyDTO[0]}" />
					
					  <form action="${pageContext.request.contextPath}/km/wanProductModify" method="post" enctype="multipart/form-data">
					
					    <!-- 기본 제품 코드 숨김 -->
					    <input type="hidden" name="product_code" value="${product.product_code}" />
					
					    <!-- 제품명 -->
					    <div class="mb-3">
					      <label for="product_name" class="form-label">제품명</label>
					      <input type="text" class="form-control" id="product_name" name="product_name" value="${product.product_name}" required>
					    </div>
					
					    <!-- 제품 설명 -->
					    <div class="mb-3">
					      <label for="product_contents" class="form-label">제품 설명</label>
					      <textarea class="form-control" id="product_contents" name="product_contents" rows="3" required>${product.product_contents}</textarea>
					    </div>
					
					    <!-- 예상 수율, 생산 단위 -->
					    <div class="row mb-3">
					      <div class="col-md-4">
					        <label for="product_yield" class="form-label">예상 수율 (%)</label>
					        <input type="number" class="form-control" id="product_yield" name="product_yield" value="${product.product_yield}" min="0" max="100" required>
					      </div>
					      <div class="col-md-4">
					        <label for="product_pack" class="form-label">완제품 생산 단위 (EA)</label>
					        <input type="number" class="form-control" id="product_pack" name="product_pack" value="${product.product_pack}" required>
					      </div>
					    </div>
					
					    <!-- 기존 이미지 보여주기 + 삭제 버튼 -->
					    <div class="mb-3">
					      <label class="form-label">기존 제품 이미지</label>
					      <div class="d-flex gap-3 flex-wrap">
					        <c:forEach var="img" items="${product.wanImgList}">
					          <div class="position-relative" style="width: 150px;">
					            <img src="${pageContext.request.contextPath}/upload/${img.file_name}" alt="제품 이미지" class="img-thumbnail" style="width: 100%; height: auto;">
					            <button type="button" class="btn btn-sm btn-danger position-absolute top-0 end-0"
					                    onclick="deleteImage(${img.product_code}, ${img.ord})">
					              X
					            </button>
					          </div>
					        </c:forEach>
					      </div>
					    </div>
					
					    <!-- 새 이미지 업로드 -->
					    <div class="mb-3">
					      <label for="file" class="form-label">새 제품 이미지 (최대 3개)</label>
					      <input type="file" class="form-control" id="file" name="file" multiple>
					      <div class="form-text text-danger">※ 최대 3개의 이미지만 업로드할 수 있습니다.</div>
					    </div>
					
					    <!-- 레시피 수정 테이블 -->
					    <div class="mb-4">
					      <label class="form-label">레시피 (원재료 및 수량)</label>
					      <table class="table table-bordered">
					        <thead>
					          <tr>
					            <th>원재료 코드</th>
					            <th>원재료 이름</th>
					            <th>수량</th>
					          </tr>
					        </thead>
					        <tbody>
					          <c:forEach var="recipe" items="${product.recipeList}" varStatus="status">
					            <tr>
					              <td>
					                <input type="hidden" name="recipeList[${status.index}].product_wan_code" value="${recipe.product_wan_code}" />
					                <input type="text" name="recipeList[${status.index}].product_won_code" class="form-control" value="${recipe.product_won_code}" readonly />
					              </td>
					              <td>
					                <input type="number" name="recipeList[${status.index}].recipe_amount" class="form-control" value="${recipe.recipe_amount}" min="0" required />
					              </td>
					            </tr>
					          </c:forEach>
					        </tbody>
					      </table>
					    </div>
					
					    <button type="submit" class="btn btn-primary">수정 완료</button>
					  </form>
					</c:if>
				</div>
			</main>
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
</body>
</html>
