<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 등록</title>
<!-- 부트스트랩 CSS CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<style>
    .form-container {
        max-width: 70%;
        margin: 0 auto;
        padding: 20px;
        background-color: #FFBB00;
        border: 1px solid #dee2e6;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- HEADER -->
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <!-- SIDEBAR -->
        <%@ include file="../sidebar.jsp" %>

        <!-- CONTENT -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container">
                    <div class="form-container bg-primary bg-opacity-25">
                        <h2 class="text-center mb-4">부서 등록</h2>
                        <form action="${pageContext.request.contextPath}/dept/saveDept" method="post">
                            <!-- 부서 이름 -->
                            <div class="mb-3">
                                <label for="dept_name" class="form-label">부서명</label>
                                <input type="text" class="form-control" id="dept_name" name="dept_name" placeholder="부서이름을 입력하세요 (예: 개발팀)" required>
                            </div>

                            <!-- 부서 전화 -->
                            <div class="mb-3">
                                <label for="dept_tel" class="form-label">부서 대표 전화</label>
                                <input type="text" class="form-control" id="dept_tel" name="dept_tel" placeholder="부서대표전화를 입력하세요 (예: 02-333-1234)" required>
                            </div>

                            <!-- 버튼 -->
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg">등록하기</button>
                                <button type="reset" class="btn btn-secondary btn-lg">초기화</button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>

            <!-- FOOTER -->
            <%@ include file="../footer.jsp" %>
        </div>
    </div>

    <!-- 부트스트랩 JS CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>
