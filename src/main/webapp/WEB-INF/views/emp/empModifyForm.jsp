<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 정보 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .form-container {
            max-width: 70%;
            margin: 20px auto;
            padding: 20px;
            background-color: #FFBB00;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-label {
            font-weight: bold;
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
                <h2 class="text-center mb-4">사원 정보 수정</h2>
                <form action="${pageContext.request.contextPath}/emp/empUpdate" method="post">
                    <input type="hidden" name="emp_code" value="${empDto.emp_code}" />

                    <!-- 사원 이름 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">사원 이름</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="emp_name" value="${empDto.emp_name}" required />
                        </div>
                    </div>

                    <!-- 전화번호 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">전화번호</label>
                        <div class="col-sm-8">
                            <input type="tel" class="form-control" name="emp_tel" value="${empDto.emp_tel}" required />
                        </div>
                    </div>

                    <!-- 소속 부서 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">소속 부서</label>
                        <div class="col-sm-8">
                            <select class="form-select" name="emp_dept_code" required>
                                <option value="1000" ${empDto.emp_dept_code == 1000 ? 'selected' : ''}>영업팀</option>
                                <option value="1001" ${empDto.emp_dept_code == 1001 ? 'selected' : ''}>생산관리팀</option>
                                <option value="1002" ${empDto.emp_dept_code == 1002 ? 'selected' : ''}>재고팀</option>
                                <option value="1003" ${empDto.emp_dept_code == 1003 ? 'selected' : ''}>인사팀</option>
                                <option value="1004" ${empDto.emp_dept_code == 1004 ? 'selected' : ''}>구매팀</option>
                                <option value="1005" ${empDto.emp_dept_code == 1005 ? 'selected' : ''}>판매팀</option>
                            </select>
                        </div>
                    </div>

                    <!-- 직급 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">직급</label>
                        <div class="col-sm-8">
                            <select class="form-select" name="emp_grade" required>
                                <option value="0" ${empDto.emp_grade == 0 ? 'selected' : ''}>사원</option>
                                <option value="1" ${empDto.emp_grade == 1 ? 'selected' : ''}>과장</option>
                                <option value="2" ${empDto.emp_grade == 2 ? 'selected' : ''}>부장</option>
                                <option value="3" ${empDto.emp_grade == 3 ? 'selected' : ''}>이사</option>
                                <option value="4" ${empDto.emp_grade == 4 ? 'selected' : ''}>사장</option>
                                <option value="5" ${empDto.emp_grade == 5 ? 'selected' : ''}>시스템</option>
                            </select>
                        </div>
                    </div>

                    <!-- 급여 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">급여</label>
                        <div class="col-sm-8">
                            <input type="number" class="form-control" name="emp_sal" value="${empDto.emp_sal}" required />
                        </div>
                    </div>

                    <!-- 이메일 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">이메일</label>
                        <div class="col-sm-8">
                            <input type="email" class="form-control" name="emp_email" value="${empDto.emp_email}" required />
                        </div>
                    </div>

                    <!-- 재직 여부 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">재직 여부</label>
                        <div class="col-sm-8">
                            <select class="form-select" name="emp_isDel">
                                <option value="false" ${!empDto.emp_isDel ? 'selected' : ''}>재직중</option>
                                <option value="true" ${empDto.emp_isDel ? 'selected' : ''}>퇴직</option>
                            </select>
                        </div>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex justify-content-center gap-3 mt-4">
                        <button type="submit" class="btn btn-success btn-custom">수정 완료</button>
                        <button type="button" class="btn btn-secondary btn-custom"
                            onclick="location.href='${pageContext.request.contextPath}/emp/empList'">목록으로</button>
                    </div>
                </form>
            </div>
        </main>
    </div>
    <%@ include file="../footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
