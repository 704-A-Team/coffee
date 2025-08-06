<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부서 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .form-label {
            font-weight: bold;
        }
        .form-container {
            max-width: 70%;
            margin: 20px auto;
            padding: 20px;
            background-color: #FFBB00;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .btn-custom {
            width: 120px;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <main class="flex-grow-1 p-4">
            <div class="form-container bg-primary bg-opacity-25">
                <h2 class="text-center mb-4">부서 수정</h2>

                <form action="${pageContext.request.contextPath}/dept/deptUpdate" method="post">
                    <input type="hidden" name="dept_code" value="${deptDto.dept_code}">

                    <div class="mb-3">
                        <label class="form-label">부서 이름</label>
                        <input type="text" class="form-control" name="dept_name" value="${deptDto.dept_name}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">전화번호</label>
                        <input type="text" class="form-control" name="dept_tel" value="${deptDto.dept_tel}" required>
                    </div>

                    <div class="d-flex justify-content-center mt-4 gap-2">
                        <button type="submit" class="btn btn-success btn-custom">수정 완료</button>
                        <button type="button" class="btn btn-secondary btn-custom"
                            onclick="location.href='${pageContext.request.contextPath}/dept/deptList'">
                            목록으로
                        </button>
                    </div>

                   

                </form>
            </div>
        </main>
    </div>

    <%@ include file="../footer.jsp" %>

   
</body>
</html>
