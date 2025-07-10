<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* 폼 컨테이너를 페이지 중앙에 배치하고 최대 너비 설정 (선택 사항) */
        .form-container {
            max-width: 600px;
            margin-top: 50px;
            padding: 30px;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="../header.jsp" %>
	</div>
	
	<div id="contents">
		    <div class="container form-container">
		        <h2 class="text-center mb-4">부서 등록</h2>
		        <form action="${pageContext.request.contextPath}/dept/saveDept" method="post">
		
		            <!-- 부서 이름 입력 필드 -->
		            <div class="mb-3">
		                <label for="deptName" class="form-label">부서 이름</label>
		                <!-- <input type="text" class="form-control" id="dept_name" name="dept_name" placeholder="부서 이름을 입력하세요 (예: 개발팀)" required> -->
		                <select class="form-select" id="dept_name" name="dept_name" required>
					        <option value="회계팀">회계팀</option>
					        <option value="개발팀">개발팀</option>
					        <option value="디자인팀">디자인팀</option>
					        <option value="마케팅팀">마케팅팀</option>
					        <option value="인사팀">인사팀</option>
					    </select>
		            </div>
		            
		            <!-- 부서 위치 입력 필드 -->
		            <div class="mb-3">
		                <label for="location" class="form-label">부서 위치</label>
		                <!-- <input type="text" class="form-control" id="dept_loc" name="dept_loc" placeholder="부서 위치를 입력하세요 (예: 서울)" required> -->
		                <select class="form-select" id="dept_loc" name="dept_loc" required>
					        <option value="서울">서울(강남) 본사</option>
					        <option value="부산">부산 지사</option>
					        <option value="세종">세종 지사</option>
					        <option value="대전">대전 지사</option>
					        <option value="춘천">춘천 지사</option>
					    </select>
		            </div>
		
		            <!-- 부서 대표전화 입력 필드 -->
		            <div class="mb-3">
		                <label for="deptNo" class="form-label">부서 대표 전화</label>
		                <input type="tel" class="form-control" id="dept_tel" name="dept_tel" placeholder="부서 대표전화를 입력하세요 (예: 010-)" required>
		                <div class="form-text">고유한 부서 번호를 입력해주세요.</div>
		            </div> 
		          
		             <!-- 폼 제출 버튼 -->
		            <div class="d-grid gap-2">
		                <button type="submit" class="btn btn-primary btn-lg">등록하기</button>
		                <button type="reset" class="btn btn-secondary btn-lg">초기화</button>
		            </div>
		        </form>
		    </div>            
				
	</div>
	
	<div id="footer">
		<%@ include file="../foot.jsp" %>
	</div>
<!-- 부트스트랩 JS CDN 링크 (<body> 태그 닫기 직전에!) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>