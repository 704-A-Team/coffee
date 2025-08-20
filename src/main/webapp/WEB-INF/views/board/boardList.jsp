<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="flex-grow-1 p-4">
            <div class="d-flex justify-content-center mb-4">
                <h1>게시판 목록</h1>
            </div>

            <div class="container">
                <!-- 글쓰기 버튼 -->
                <div class="d-flex justify-content-end mb-2">
                    <a href="${pageContext.request.contextPath}/board/boardWriteForm" class="btn btn-success">글쓰기</a>
                </div>

                <!-- 게시글 테이블 -->
				<table class="table table-striped table-bordered text-center">
				    <thead class="table-dark">
				        <tr>
				            <th style="width: 10%;">번호</th>
				            <th style="width: 40%;">제목</th>
				            <th style="width: 15%;">부서</th>
				            <th style="width: 10%;">조회수</th>
				            <th style="width: 15%;">등록일</th>
				        </tr>
				    </thead>
				    <tbody>
				        <c:forEach var="board" items="${boardList}">
				            <tr>
				                <!-- 번호 대신 board_code -->
				                <td>${board.board_code}</td>
				
				                <!-- 제목 클릭하면 상세보기 이동 -->
				                <td class="text-start">
				                    <a href="${pageContext.request.contextPath}/board/boardView?board_code=${board.board_code}">
				                        ${board.board_title}
				                    </a>
				                </td>
				
				                <!-- 부서 -->
				                <td>${board.dept_name}</td>
				
				                <!-- 조회수 -->
				                <td>${board.read_count}</td>
				
				                <!-- 등록일 -->
				                <td>${fn:substring(board.board_reg_date, 0, 10)}</td>
				            </tr>
				        </c:forEach>
				
				        <c:if test="${empty boardList}">
				            <tr>
				                <td colspan="5">등록된 게시글이 없습니다.</td>
				            </tr>
				        </c:if>
				    </tbody>
				</table>

                <!-- 페이징 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/board/boardList?currentPage=${page.startPage - page.pageBlock}">&laquo; 이전</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/board/boardList?currentPage=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/board/boardList?currentPage=${page.startPage + page.pageBlock}">다음 &raquo;</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <%@ include file="../footer.jsp" %>

</body>
</html>