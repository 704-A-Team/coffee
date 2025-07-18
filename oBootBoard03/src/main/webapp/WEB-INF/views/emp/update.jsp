<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
		function removeImage(button) {
		    const card = button.closest('.image-card');
		
		    if (confirm('이 이미지를 제거하시겠습니까?')) {
		        // input 요소도 함께 제거되어야 함
		        const hiddenInput = card.querySelector("input[type='hidden'][name='uploadFileNames']");
		        if (hiddenInput) hiddenInput.remove();
		
		        // 카드(div) 제거
		        card.remove();
		    }
		}
</script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <meta charset="UTF-8">
    <title>직원 수정</title>
</head>
<body>

<!-- 헤더 -->
<div id="header" style="background-color:#96bfe7;">
    <%@ include file="../header.jsp" %>
</div>

<div class="container mt-4">
    <h2 class="text-primary mb-4">✏️ 직원 정보 수정</h2>

    <!-- 직원 수정 Form -->
    <form action="${pageContext.request.contextPath}/emp/modify" method="post" enctype="multipart/form-data">
        <input type="hidden" name="emp_no" value="${emp.emp_no}" />
		<input type="hidden" name="emp_password" value="${emp.emp_password}" />
		
        <div class="mb-3">
            <label for="emp_name" class="form-label">이름</label>
            <input type="text" class="form-control" id="emp_name" name="emp_name" value="${emp.emp_name}" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">이메일</label>
            <input type="email" class="form-control" id="email" name="email" value="${emp.email}">
        </div>

        <div class="mb-3">
            <label for="emp_tel" class="form-label">전화번호</label>
            <input type="text" class="form-control" id="emp_tel" name="emp_tel" value="${emp.emp_tel}">
        </div>

        <div class="mb-3">
            <label for="sal" class="form-label">급여</label>
            <input type="number" class="form-control" id="sal" name="sal" value="${emp.sal}">
        </div>

        <div class="mb-3">
            <label for="dept_code" class="form-label">부서</label>
            <select class="form-select" id="dept_code" name="dept_code" required>
                <option value="" disabled>부서를 선택하세요</option>
                <c:forEach var="dept" items="${deptList}">
                    <option value="${dept.dept_code}" 
                        <c:if test="${dept.dept_code == emp.dept_code}">selected</c:if>>
                        ${dept.dept_name}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <!-- 새 이미지 업로드 input -->
	    <div class="mt-4">
	        <label for="newFiles" class="form-label">새 이미지 추가</label>
	        <input type="file" class="form-control" id="file" name="file" multiple>
	    </div>
		
		<!-- 이미지 목록 렌더링 -->
		<div id="imageList" class="card-body d-flex flex-wrap gap-3">
		    <c:forEach var="file" items="${emp.uploadFileNames}">
		        <div class="image-card border rounded p-2 bg-white text-center position-relative" style="width:120px;" data-filename="${file}">
		            <img src="${pageContext.request.contextPath}/upload/${file}" alt="${file}" style="height:100px; object-fit:contain;">
		            <p class="mt-2 small text-truncate" title="${file}">${file}</p>
		
		            <!-- 삭제 버튼 -->                                              					  <!--  this는 지금 눌린 버튼 자기 자신 -->
		            <button type="button" class="btn btn-sm btn-danger position-absolute top-0 end-0 m-1" onclick="removeImage(this)">
		                <i class="bi bi-x-lg"></i>
		            </button>
		
		            <!-- 전송용 hidden input -->
		            <input type="hidden" name="uploadFileNames" value="${file}">
		        </div>
		    </c:forEach>
		</div>
		
        <button type="submit" class="btn btn-success">
            <i class="bi bi-check-circle"></i> 저장
        </button>
        <a href="${pageContext.request.contextPath}/emp/detail?emp_no=${emp.emp_no}" class="btn btn-secondary ms-2">취소</a>
	</form>

	
</div>

<!-- 푸터 -->
<div id="footer">
    <%@ include file="../foot.jsp" %>
</div>


<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
