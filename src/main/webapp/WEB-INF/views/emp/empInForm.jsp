<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container">
                    <div class="form-container bg-primary bg-opacity-25">
                        <h2 class="text-center mb-4">사원 등록</h2>
                        <form action="${pageContext.request.contextPath}/emp/saveEmp" method="post">
                            <!-- 사원명 -->
                            <div class="mb-3">
                                <label for="emp_name" class="form-label">이름</label>
                                <input type="text" class="form-control" id="emp_name" name="emp_name" required>
                            </div>

                            <!-- 전화번호 -->
                            <div class="mb-3">
                                <label for="emp_tel" class="form-label">전화번호</label>
                                <input type="text" class="form-control" id="emp_tel" name="emp_tel" required>
                            </div>

                            <!-- 부서코드 -->
                            <div class="mb-3">
                                <label for="emp_dept_code" class="form-label">소속 부서</label>
                                <select class="form-select" id="emp_dept_code" name="emp_dept_code">
                                    <option value="1000">영업팀</option>
                                    <option value="1001">생산관리팀</option>
                                    <option value="1002">재고팀</option>
                                    <option value="1003">인사팀</option>
                                    <option value="1004">구매팀</option>
                                    <option value="1005">판매팀</option>
                                    <option value="1006">경영팀</option>
                                </select>
                            </div>

                            <!-- 직급 -->
                            <div class="mb-3">
                                <label for="emp_grade" class="form-label">직급</label>
                                <select class="form-select" id="emp_grade" name="emp_grade">
                                    <option value="0">사원</option>
                                    <option value="1">과장</option>
                                    <option value="2">부장</option>
                                    <option value="3">이사</option>
                                    <option value="4">사장</option>
                                </select>
                            </div>

                            <!-- 급여 -->
                            <div class="mb-3">
                                <label for="emp_sal" class="form-label">급여</label>
                                <input type="number" class="form-control" id="emp_sal" name="emp_sal" required>
                            </div>

                            <!-- 이메일 -->
                            <div class="mb-3">
                                <label for="emp_email" class="form-label">이메일</label>
                                <input type="email" class="form-control" id="emp_email" name="emp_email" required>
                            </div>
							
							<!-- 입사일 -->
							<div class="mb-3">
						    <label for="emp_ipsa_date" class="form-label">입사일</label>
						    <input type="date" class="form-control" id="emp_ipsa_date" name="emp_ipsa_date" required>
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

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

   
</body>
</html>
