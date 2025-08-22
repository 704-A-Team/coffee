<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
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

        <div class="flex-grow-1 p-4">
            <div class="container">
                <div class="container mt-3">
                    <div class="form-section-title">게시판 수정</div>
                </div>

                <!-- 수정 폼 -->
                <form id="updateForm" action="${pageContext.request.contextPath}/board/boardUpdate" method="post">
                    <input type="hidden" name="board_code" value="${boardDTO.board_code}">

                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th style="width: 15%;">제목</th>
                                <td>
                                    <input type="text" name="board_title" class="form-control" value="${boardDTO.board_title}">
                                </td>
                            </tr>
                            <tr>
                                <th>부서</th>
                                <td>
                                    <input type="text" class="form-control" value="${boardDTO.dept_name}" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th>조회수</th>
                                <td>
                                    <input type="text" class="form-control" value="${boardDTO.read_count}" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td>
                                    <input type="text" class="form-control" value="${fn:substring(boardDTO.board_reg_date, 0, 10)}" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td style="white-space: pre-wrap;">
                                    <textarea name="board_contents" class="form-control" rows="10" required>${boardDTO.board_contents}</textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>

                <!-- 버튼 영역: 저장 / 삭제 / 목록 -->
                <div class="mt-3 d-flex gap-2">
                    <!-- 저장 버튼: form="updateForm" -->
                    <button type="submit" form="updateForm" class="btn btn-primary">저장</button>

                    <!-- 삭제 버튼: href + confirm -->
                    <a href="${pageContext.request.contextPath}/board/boardDelete?board_code=${boardDTO.board_code}"
                       onclick="return confirm('정말 삭제하시겠습니까?');"
                       class="btn btn-danger">삭제</a>

                    <!-- 목록 버튼 -->
                    <a href="${pageContext.request.contextPath}/board/boardList" class="btn btn-secondary">목록</a>
                </div>

            </div>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>
</body>
</html>