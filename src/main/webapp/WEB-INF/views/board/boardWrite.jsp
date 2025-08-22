<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 글등록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <style type="text/css">
    	.form-section-title {
		    border-left: 4px solid #0d6efd; /* 파란색 세로선 */
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

        <div class="flex-grow-1 p-4">
            
            <div class="container">
            <div class="container mt-3">
		    <div class="form-section-title">게시글 작성</div>
                <form action="${pageContext.request.contextPath}/board/boardWrite" method="post">
                    <div class="mb-3">
                        <label for="board_title" class="form-label">제목</label>
                        <input type="text" class="form-control" id="board_title" name="board_title" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="board_contents" class="form-label">내용</label>
                        <textarea class="form-control" id="board_contents" name="board_contents" rows="10" required></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">등록</button>
                    <a href="${pageContext.request.contextPath}/board/boardList" class="btn btn-secondary">취소</a>
                </form>
            </div>
        </div>
    </div>
  </div>
</div>

    <%@ include file="../footer.jsp" %>

</body>
</html>