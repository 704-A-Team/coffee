<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 상세보기</title>
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

        <!-- ✅ flex-column으로 footer를 포함하는 본문 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container">
                    <div class="container mt-3">
			            <div class="form-section-title">게시판 상세보기</div>
			        </div>

                    <table class="table table-bordered">
                        <tbody>
                          	<tr>
                                <th>제목</th>
                                <td>${boardDTO.board_title}</td>
                            </tr>
                            <tr>
                                <th style="width: 15%;">부서</th>
                                <td>${boardDTO.dept_name}</td>
                            </tr>
                            <tr>
                                <th>조회수</th>
                                <td>${boardDTO.read_count}</td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td>${fn:substring(boardDTO.board_reg_date, 0, 10)}</td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td style="white-space: pre-wrap;">${boardDTO.board_contents}</td>
                            </tr>
                        </tbody>
                    </table>

                   <div class="mt-3 d-flex gap-2">
				        <!-- 수정 버튼 -->
				        <a href="${pageContext.request.contextPath}/board/boardEditForm?board_code=${boardDTO.board_code}" class="btn btn-primary">수정</a>
				        
				        <!-- 목록 버튼 -->
				        <a href="${pageContext.request.contextPath}/board/boardList" class="btn btn-secondary">목록</a>
				   </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>