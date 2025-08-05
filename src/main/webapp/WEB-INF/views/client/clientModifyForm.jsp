<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 정보 수정</title>
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
                <h2 class="text-center mb-4">거래처 정보 수정</h2>
                <form action="${pageContext.request.contextPath}/client/clientUpdate" method="post">
                    <input type="hidden" name="client_code" value="${clientDto.client_code}" />

                    <!-- 거래처명 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">거래처명</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="client_name" value="${clientDto.client_name}" required />
                        </div>
                    </div>

                    <!-- 사업자등록번호 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">사업자등록번호</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="saup_num" value="${clientDto.saup_num}" required />
                        </div>
                    </div>

                    <!-- 대표자명 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">대표자명</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="boss_name" value="${clientDto.boss_name}" required />
                        </div>
                    </div>

                    <!-- 거래처 유형 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">거래처 유형</label>
                        <div class="col-sm-8">
                            <select class="form-select" name="client_type" required>
                                <option value="1" ${clientDto.client_type == 1 ? 'selected' : ''}>공급처</option>
                                <option value="2" ${clientDto.client_type == 2 ? 'selected' : ''}>가맹점</option>
                            </select>
                        </div>
                    </div>

                    <!-- 주소 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">주소</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="client_address" value="${clientDto.client_address}" required />
                        </div>
                    </div>

                    <!-- 전화번호 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">전화번호</label>
                        <div class="col-sm-8">
                            <input type="tel" class="form-control" name="client_tel" value="${clientDto.client_tel}" required />
                        </div>
                    </div>
      				
      				 <!-- 담당 사원 선택 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">담당 사원</label>
                        <div class="col-sm-8">
                            <select name="client_emp_code" class="form-select" required>
                         <c:forEach var="emp" items="${empList}">
						    <c:if test="${emp.emp_dept_code == 1000}">
						        <option value="${emp.emp_code}"
						            ${clientDto.client_emp_code == emp.emp_code ? 'selected' : ''}>
						            [${emp.emp_code}] ${emp.emp_name}
						        </option>
						    </c:if>
						</c:forEach>
						
						                            </select>
                        </div>
                    </div>

                    <!-- 상태 -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">상태</label>
                        <div class="col-sm-8">
                            <select class="form-select" name="client_status">
                                <option value="0" ${clientDto.client_status == 0 ? 'selected' : ''}>영업중</option>
                                <option value="1" ${clientDto.client_status == 1 ? 'selected' : ''}>휴업중</option>
                                <option value="2" ${clientDto.client_status == 2 ? 'selected' : ''}>폐점</option>
                            </select>
                        </div>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex justify-content-center gap-3 mt-4">
                        <button type="submit" class="btn btn-success btn-custom">수정 완료</button>
                        <button type="button" class="btn btn-secondary btn-custom"
                            onclick="location.href='${pageContext.request.contextPath}/client/clientList'">목록으로</button>
                    </div>
                </form>
            </div>
        </main>
    </div>
    <%@ include file="../footer.jsp" %>
   
</body>
</html>
