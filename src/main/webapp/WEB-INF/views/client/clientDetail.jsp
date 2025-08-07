<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 상세 조회</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
    <%@ include file="../sidebar.jsp" %>

    <main class="flex-grow-1 p-4">
        <div class="form-container bg-primary bg-opacity-25">
            <h2 class="text-center mb-4">거래처 상세 조회</h2>

            <form>
                <!-- 거래처 코드 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">거래처 코드</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.client_code}" disabled>
                    </div>
                </div>

                <!-- 거래처명 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">거래처명</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.client_name}" disabled>
                    </div>
                </div>

                <!-- 사업자등록번호 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">사업자등록번호</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.saup_num}" disabled>
                    </div>
                </div>

                <!-- 대표자명 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">대표자명</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.boss_name}" disabled>
                    </div>
                </div>
							<div class="row mb-3">
				    <label class="col-sm-2 col-form-label">거래처 유형</label>
				    <div class="col-sm-8">
				        <input type="text" class="form-control" value="${clientDto.client_type_br}" disabled>
				    </div>
				</div>
											
                <!-- 주소 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">주소</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.client_address}" disabled>
                    </div>
                </div>

                <!-- 전화번호 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">전화번호</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.client_tel}" disabled>
                    </div>
                </div>

                <!-- 담당 사원명 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">담당 사원</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.client_emp_name}" disabled>
                    </div>
                </div>

              <!-- 상태 -->
				<c:set var="statusText">
				    <c:choose>
				        <c:when test="${clientDto.client_status == 0}">영업</c:when>
				        <c:when test="${clientDto.client_status == 1}">휴업</c:when>
				        <c:when test="${clientDto.client_status == 2}">폐점</c:when>
				    </c:choose>
				</c:set>
				<div class="row mb-3">
				    <label class="col-sm-2 col-form-label">상태</label>
				    <div class="col-sm-8">
				        <input type="text" class="form-control" value="${statusText}" disabled />
				    </div>
				</div>


                <!-- 등록일 -->
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">등록일</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" value="${clientDto.clientRegDateFormatted}" disabled>
                    </div>
                </div>

                <!-- 버튼 영역 -->
                <div class="d-flex gap-2 mt-4 justify-content-center">
                    <button type="button" class="btn btn-primary btn-lg"
                        onclick="location.href='${pageContext.request.contextPath}/client/modifyForm?client_code=${clientDto.client_code}'">
                        거래처 수정
                    </button>

                    <button type="button" class="btn btn-secondary btn-lg"
                        onclick="location.href='${pageContext.request.contextPath}/client/clientList'">
                        거래처 목록으로
                    </button>
                </div>
            </form>
        </div>
    </main>
</div>

<%@ include file="../footer.jsp" %>
</body>
</html>
