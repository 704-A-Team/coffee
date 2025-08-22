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
            <main class="flex-grow-1 p-4">
                <div class="container mt-3">
		            <div class="form-section-title">게시글 작성</div>
                    
                    <form action="${pageContext.request.contextPath}/board/boardWrite" method="post">
                        
                        <!-- ✅ 게시글 타입 선택 -->
                        <div class="mb-3">
                            <label class="form-label">글 종류</label><br>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="board_type" id="notice" value="0">
                                <label class="form-check-label" for="notice">공지사항</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="board_type" id="normal" value="1" checked>
                                <label class="form-check-label" for="normal">일반글</label>
                            </div>
                        </div>

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
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

    <!-- ✅ JS로 제목 자동 prefix 처리 -->
    <script>
        const titleInput = document.getElementById("board_title");
        const noticeRadio = document.getElementById("notice");
        const normalRadio = document.getElementById("normal");

        function updateTitlePrefix() {
            let value = titleInput.value;

            // 이미 [공지사항]이 붙어 있으면 제거
            if (value.startsWith("[공지사항]")) {
                value = value.replace(/^\[공지사항\]\s*/, "");
            }

            // 공지사항이면 prefix 추가
            if (noticeRadio.checked) {
                titleInput.value = "[공지사항] " + value;
            } else {
                titleInput.value = value;
            }
        }

        // 라디오 버튼 클릭 시 실행
        noticeRadio.addEventListener("change", updateTitlePrefix);
        normalRadio.addEventListener("change", updateTitlePrefix);

        // 제목 입력 후 focusout 시에도 맞춰주기
        titleInput.addEventListener("blur", updateTitlePrefix);
    </script>

</body>
</html>