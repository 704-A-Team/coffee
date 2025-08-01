<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
          .image-thumb {
		    width: 100px;
		    height: auto;
		    border-radius: 4px;
		    border: 1px solid #ccc;
		  }
    </style>
    <script>
    document.addEventListener("DOMContentLoaded", () => {
    	  const fileInput = document.querySelector("#newFiles");
    	  const existingFilesContainer = document.querySelector("#existingFiles");

    	  // 삭제 클릭 시 input hidden도 제거
    	  existingFilesContainer.addEventListener("click", function (e) {
    	    if (e.target.classList.contains("remove-existing")) {
    	      const fileDiv = e.target.closest(".existing-file");
    	      fileDiv.remove();
    	    }
    	  });

    	  fileInput.addEventListener("change", function () {
    	    const existingCount = document.querySelectorAll("#existingFiles .existing-file").length;
    	    const newCount = this.files.length;

    	    if (existingCount + newCount > 3) {
    	      alert("기존 이미지와 새 이미지 합산 최대 3개까지만 가능합니다.");
    	      this.value = ""; // 선택 취소
    	    }
    	  });
    	});
	</script>
</head>
<body class="d-flex flex-column min-vh-100">
	
	<!-- HEADER -->
	<%@ include file="../header.jsp" %>
	
	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="../sidebar.jsp" %>
		
		<div class="d-flex flex-column flex-grow-1">
			
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
				<div class="container mt-3">
				  <div class="form-section-title">완제품 등록</div>
        	<form action="${pageContext.request.contextPath}/km/wanModify" method="post" enctype="multipart/form-data">
	          <input type="hidden" name="product_code" value="${wanModifyDTO.product_code}">
	
	          <!-- 제품명 -->
	          <div class="mb-3">
	            <label for="product_name" class="form-label">제품명</label>
	            <input type="text" class="form-control" id="product_name" name="product_name"
	                   value="${wanModifyDTO.product_name}" required>
	          </div>
	
	          <!-- 제품 설명 -->
	          <div class="mb-3">
	            <label for="product_contents" class="form-label">제품 설명</label>
	            <textarea class="form-control" id="product_contents" name="product_contents" rows="3"
	                      required>${wanModifyDTO.product_contents}</textarea>
	          </div>
	
	          <!-- 수율 / 생산 단위 50:50 -->
	          <div class="row mb-3">
	            <div class="col-md-6">
	              <label for="product_yield" class="form-label">예상 수율 (%)</label>
	              <input type="number" class="form-control" id="product_yield" name="product_yield"
	                     value="${wanModifyDTO.product_yield}" min="0" max="100" required>
	            </div>
	            <div class="col-md-6">
	              <label for="product_pack" class="form-label">완제품 생산 단위 (EA)</label>
	              <input type="number" class="form-control" id="product_pack" name="product_pack"
	                     value="${wanModifyDTO.product_pack}" required>
	            </div>
	          </div>
	
	          <!-- 파일 업로드 -->
	          <div class="mb-3">
	            <label class="form-label">첨부 이미지 (기존 + 신규 합쳐 최대 3개)</label>
	
				<!-- 기존 이미지 보여주기 (한 줄에 이미지 + 삭제 버튼) -->
				<div id="existingFiles" class="mb-3 d-flex flex-wrap gap-3">
				  <c:forEach var="img" items="${wanModifyDTO.wanImgList}" varStatus="status">
				    <div class="d-flex flex-column align-items-center existing-file" data-filename="${img.file_name}">
				      <img src="/upload/${img.file_name}" alt="img${status.index}" class="image-thumb mb-1">
				      <input type="hidden" name="existingFileNames" value="${img.file_name}" />
				      <button type="button" class="btn btn-sm btn-outline-danger remove-existing">삭제</button>
				    </div>
				  </c:forEach>
				</div>
				
	            <!-- 새 이미지 업로드 -->
	            <input type="file" id="newFiles" name="file" multiple class="form-control" accept=".jpg,.jpeg,.png">
	            <div class="form-text text-danger mt-1">※ 기존 + 새 이미지 포함 최대 3개까지 등록 가능합니다.</div>
	          </div>
	
	          <button type="submit" class="btn btn-primary">수정 완료</button>
	          <a href="/km/wanAndRcpDetailInForm?product_code=${wanModifyDTO.product_code}" class="btn btn-secondary ms-2">취소</a>
	        </form>
        </div>
      </main>
			
			<!-- FOOTER -->
			<%@ include file="../footer.jsp" %>
		</div>
	</div>
	
</body>
</html>