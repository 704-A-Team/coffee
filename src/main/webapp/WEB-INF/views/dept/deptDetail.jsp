<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부서 상세 조회</title>

    <!-- Bootstrap CSS -->
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

        <!-- 거래처 목록 페이지와 동일한 구조: 본문+푸터 래퍼 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="form-container bg-primary bg-opacity-25">
                    <h2 class="text-center mb-4">부서 상세 조회</h2>

                    <form>
                        <!-- 부서 코드 -->
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">부서 코드</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${deptDto.dept_code}" disabled>
                            </div>
                        </div>

                        <!-- 부서 이름 -->
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">부서 이름</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${deptDto.dept_name}" disabled>
                            </div>
                        </div>

                        <!-- 부서 대표 전화 -->
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">부서 대표 전화</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${deptDto.dept_tel}" disabled>
                            </div>
                        </div>

                        <!-- 등록일 -->
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">등록일</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${deptDto.deptRegDateFormatted}" disabled>
                            </div>
                        </div>

                        <!-- 버튼 -->
                        <div class="d-flex gap-2 mt-4 justify-content-center">
                            <button type="button" class="btn btn-primary btn-lg"
                                onclick="location.href='${pageContext.request.contextPath}/dept/modifyForm?dept_code=${deptDto.dept_code}'">
                                부서정보 수정
                            </button>

                            <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
                                부서 삭제
                            </button>

                            <button type="button" class="btn btn-secondary btn-lg"
                                onclick="location.href='${pageContext.request.contextPath}/dept/deptList'">
                                부서 목록으로
                            </button>
                        </div>

                        <!-- 삭제 모달 -->
                        <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="deleteConfirmModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="deleteConfirmModalLabel">삭제 확인</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        이 부서를 삭제하시겠습니까? 삭제된 정보는 되돌릴 수 없습니다.
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                        <button type="button" class="btn btn-danger"
                                            onclick="location.href='${pageContext.request.contextPath}/dept/deptDelete?dept_code=${deptDto.dept_code}'">
                                            삭제
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
        <!-- /본문+푸터 래퍼 -->
    </div>

</body>
</html>
