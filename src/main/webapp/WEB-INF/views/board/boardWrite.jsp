<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 글등록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="flex-grow-1 p-4">
            <div class="d-flex justify-content-center mb-4">
                <h1>게시판 글 작성</h1>
            </div>

            <div class="container">
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

    <%@ include file="../footer.jsp" %>

</body>
</html>