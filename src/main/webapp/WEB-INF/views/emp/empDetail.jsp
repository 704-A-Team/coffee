<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 상세 조회</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            width: 100%;
            max-width: 980px;
            margin: 0 auto;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 + 푸터 같은 컬럼 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="form-container">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="h5 mb-0">사원 상세 조회</h2>
                        </div>
                        <div class="card-body">
                            <form>
                                <!-- 사원 번호 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">사원 번호</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_code}" disabled>
                                    </div>
                                </div>

                                <!-- 사원 이름 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">사원 이름</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_name}" disabled>
                                    </div>
                                </div>

                                <!-- 사원 전화번호 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">사원 전화번호</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_tel}" disabled>
                                    </div>
                                </div>

                                <!-- 소속 부서 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">소속 부서</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.dept_code}" disabled>
                                    </div>
                                </div>

                                <!-- 직급 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">직급</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_grade_detail}" disabled>
                                    </div>
                                </div>

                                <!-- 급여 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">급여</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_sal}" disabled>
                                    </div>
                                </div>

                                <!-- 이메일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">이메일</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_email}" disabled>
                                    </div>
                                </div>
                                
                                 <!-- 생년월일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">생년월일</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_birth}" disabled>
                                    </div>
                                </div>

                                <!-- 등록일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">등록일</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.empRegDateFormatted}" disabled>
                                    </div>
                                </div>

                                <!-- 입사일 -->
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">입사일</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" value="${empDto.emp_ipsa_date}" disabled>
                                    </div>
                                </div>

                                <!-- 버튼 -->
                                <div class="d-flex gap-2 mt-4 justify-content-center">
                                    <button type="button" class="btn btn-primary btn-lg"
                                        onclick="location.href='${pageContext.request.contextPath}/emp/modifyForm?emp_code=${empDto.emp_code}'">
                                        사원정보 수정
                                    </button>

                                    <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
                                        퇴직 처리
                                    </button>

                                    <button type="button" class="btn btn-secondary btn-lg"
                                        onclick="location.href='${pageContext.request.contextPath}/emp/empList'">
                                        사원 목록으로
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
                                                사원 정보를 삭제하시겠습니까? 삭제된 정보는 되돌릴 수 없습니다.
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                                <button type="button" class="btn btn-danger"
                                                    onclick="location.href='${pageContext.request.contextPath}/emp/empDelete?emp_code=${empDto.emp_code}'">
                                                    삭제
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div><!-- card-body -->
                    </div><!-- card -->
                </div><!-- form-container -->
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>
</body>
</html>
