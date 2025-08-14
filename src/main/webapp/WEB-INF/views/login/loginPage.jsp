<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <!-- 부트스트랩 CSS CDN 링크 -->
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
    .form-container {
        max-width: 70%;
        margin: 20px auto;
        padding: 20px;
        background-color: #FFE08C;
        border: 1px solid #dee2e6;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    
    .form-row {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
    }
    
    .form-row label {
        width: 120px;
        margin-bottom: 0;
    }
    
    .form-row input {
        flex: 1;
    }
    
    .button-row {
        display: flex;
        gap: 10px;
    }
    
    .button-row button {
        flex: 1;
    }
</style>
</head>
<body>
	<div id="header">
		<%@ include file="../header.jsp" %>
	</div>
	
	<div id="contents">
	  <div class="container form-container">
	        <h2 class="text-center mb-4">Login</h2>
	        
	        <!-- 오류 메시지 위치 변경 - 여기로 이동 -->
	        <c:if test="${!empty param.error}">
	            <div class="alert alert-danger text-center mb-3">잘못된 아이디나 암호입니다</div>
	        </c:if>
	        
	        <form action="${pageContext.request.contextPath}/login" method="post">
	            <input type="hidden" value="secret" name="secret_key" />
	        
	            <!-- username 입력 필드 -->
	            <div class="form-row">
	                <label for="username" class="form-label">UserName</label>
	                <input type="text" class="form-control" id="username" name="username" placeholder="username을 입력하세요" required>
	            </div>
	
	            <!-- 비밀번호 입력 필드 -->
	            <div class="form-row">
	                <label for="password" class="form-label">비밀번호</label>
	                <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
	            </div>
	
	            <!-- 폼 제출 버튼 -->
	            <div class="button-row mt-4">
	                <button type="submit" class="btn btn-primary btn-lg">LogIn하기</button>
	                <button type="reset" class="btn btn-secondary btn-lg">초기화</button>
	            </div>
	        </form>
	    </div>    
	</div>
	
	<div id="footer">
		<%@ include file="../footer.jsp" %>
	</div>
		
	<!-- 부트스트랩 JS CDN 링크 (<body> 태그 닫기 직전에!) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>