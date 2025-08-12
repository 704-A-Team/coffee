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
            width: 100%;
            max-width: 980px;           /* 목록 페이지랑 비슷한 폭 느낌 */
            margin: 0 auto;
        }
        .form-label { font-weight: 600; }
        .btn-custom { width: 120px; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 + 푸터를 같은 컬럼에 넣어 footer가 하단에 고정되도록 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="form-container">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="h5 mb-0">거래처 정보 수정</h2>
                        </div>
                        <div class="card-body">
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
                                            <option value="2" <c:if test="${clientDto.client_type == 2}">selected</c:if>>공급처</option>
                                            <option value="3" <c:if test="${clientDto.client_type == 3}">selected</c:if>>가맹점</option>
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
                                                    <option value="${emp.emp_code}" <c:if test="${clientDto.client_emp_code == emp.emp_code}">selected</c:if>>
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
                                    <div class="col-sm-8 pt-2">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="client_status" id="status0" value="0"
                                                   <c:if test="${clientDto.client_status == 0}">checked</c:if>>
                                            <label class="form-check-label" for="status0">영업중</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="client_status" id="status1" value="1"
                                                   <c:if test="${clientDto.client_status == 1}">checked</c:if>>
                                            <label class="form-check-label" for="status1">휴업중</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="client_status" id="status2" value="2"
                                                   <c:if test="${clientDto.client_status == 2}">checked</c:if>>
                                            <label class="form-check-label" for="status2">폐점</label>
                                        </div>
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
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>
</body>
</html>
