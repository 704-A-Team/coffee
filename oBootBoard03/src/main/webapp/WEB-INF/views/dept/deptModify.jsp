<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<meta charset="UTF-8">
<title>부서 수정</title>
   <!--  <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"> -->
    <style>
        body {
            background-color: #e0f7ff; /* 베이비블루 느낌 */
        }
        .form-container {
            background-color: white;
            border-radius: 1rem;
            padding: 2rem;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 3rem auto;
        }
        .btn-skyblue {
            background-color: #87ceeb;
            color: white;
        }
        .btn-skyblue:hover {
            background-color: #6ab7db;
        }
		.icon-colored {
		    color: #87ceeb;  /* 스카이블루 */
		    vertical-align: middle; /* 텍스트랑 세로 정렬 */
		    font-size: 1.5rem; /* 아이콘 크기 */
		}        
    </style>
</head>
<body>
	<div id="header" style="background-color:#96bfe7;">
		<%@ include file="../header.jsp" %>
	</div>
	
	<div id="contents">
		 <div class="form-container">
		        <h3 class="text-center mb-4 fw-bold" style="color: black;">
				  <i class="bi bi-pencil-square icon-colored me-2"></i> 부서 정보 수정
				</h3>
		        <form method="post" action="${pageContext.request.contextPath}/dept/deptUpdate">
		            <!-- dept_code는 수정 불가, hidden으로 전송만 -->
		            <div class="mb-3">
		                <label class="form-label">부서 코드</label>
		                <input type="text" class="form-control" value="${dept.dept_code}" disabled>
		                <input type="hidden" name="dept_code" value="${dept.dept_code}">
		            </div>
		            
		            <div class="mb-3">
		                <label class="form-label">부서명</label>
		                <input type="text" class="form-control" name="dept_name" value="${dept.dept_name}" required>
		            </div>
		
		            <div class="mb-3">
		                <label class="form-label">부서장 사번</label>
		                <input type="number" class="form-control" name="dept_captain" value="${dept.dept_captain}">
		            </div>
		
		            <div class="mb-3">
		                <label class="form-label">전화번호</label>
		                <input type="tel" class="form-control" name="dept_tel" value="${dept.dept_tel}">
		            </div>
		
		            <div class="mb-3">
		                <label class="form-label">위치</label>
		                <input type="text" class="form-control" name="dept_loc" value="${dept.dept_loc}">
		            </div>
		
		            <%-- <div class="mb-3">
		                <label class="form-label">구분</label>
		                <select class="form-select" name="dept_gubun">
		                    <option value="true" ${dept.dept_gubun ? 'selected' : ''}>삭제</option>
		                    <option value="false" ${!dept.dept_gubun ? 'selected' : ''}>존재</option>
		                </select>
		            </div> --%>
		
		            <div class="mb-3">
		                <label class="form-label">생성일</label>
		                <input type="datetime-local" class="form-control" name="in_date"
       						  value="${fn:substring(dept.in_date, 0, 16)}">
		            </div>
		
		            <div class="d-flex justify-content-between">
		            	<a href="/dept/list" class="btn btn-secondary">목록</a>
		                <button type="submit" class="btn text-white" style="background-color: #87ceeb; border: none;">
						    수정
						</button>
		                <button type="button" class="btn btn-danger"
		                        onclick="deleteDept(${dept.dept_code})">삭제</button>
		            </div>
		        </form>
		    </div>
		
		    <script>
		        function deleteDept(code) {
		            if (confirm("정말 삭제하시겠습니까?")) {
		                window.location.href = "/dept/deptDelete?dept_code=" + code;
		            }
		        }
		    </script>		
	</div>
	
	<div id="footer">
		<%@ include file="../foot.jsp" %>
	</div>
<!-- 부트스트랩 JS CDN 링크 (<body> 태그 닫기 직전에!) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>