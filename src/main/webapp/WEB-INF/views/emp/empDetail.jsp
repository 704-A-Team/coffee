<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 상세 조회</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    :root{
        --main-brown:#6f4e37;	
        --dark-brown:#4e342e;
        --soft-brown:#bfa08e;
        --danger-red:#a94442;
    }
    body{
        background:#f9f5f1;
        font-family:'Segoe UI',sans-serif;
    }
    .form-container{ width:100%; max-width:980px; margin:0 auto; }

    /* 섹션 타이틀 */
    .form-section-title{
        border-left:5px solid var(--main-brown);
        padding-left:12px;
        margin:6px 0 24px;
        font-weight:700;
        font-size:1.8rem;
        color:var(--dark-brown);
    }

    /* 카드 */
    .card-detail{
        background:#fff;
        border:1px solid #e0e0e0;
        box-shadow:0 4px 6px rgba(0,0,0,.05);
    }
    .card-header{
        background:linear-gradient(180deg, #fff, #f4ebe3);
        border-bottom:1px solid #eadfd6;
    }
    .card-header .title{
        color:var(--main-brown);
        font-weight:700;
        margin:0;
    }

    /* 라벨/인풋 간격 살짝 조정 */
    .col-form-label{ font-weight:600; color:#555; }
    .form-control[disabled]{ background:#fff; border-color:#e6e0db; color:#333; }

    /* 버튼 표준 */
    .btn-brown-outline{
        border:1px solid var(--main-brown) !important;
        color:var(--main-brown) !important;
        background:#fff !important;
    }
    .btn-brown-outline:hover{
        background:#ccc !important;
        color:#333 !important;
        border-color:#ccc !important;
    }
    .btn-soft-danger{
        background:var(--danger-red) !important;
        color:#fff !important;
        border:1px solid var(--danger-red) !important;
    }
    .btn-soft-danger:hover{
        background:#922d2b !important;
        border-color:#922d2b !important;
    }

    /* 버튼 영역 우측 정렬 (모바일은 가운데) */
    .btn-area{ display:flex; justify-content:end; gap:.75rem; margin-top:1.25rem; }
    @media (max-width: 768px){
        .btn-area{ justify-content:center; }
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="form-container">
                    <div class="form-section-title">사원 상세보기</div>

                    <div class="card card-detail shadow-sm">
                        <div class="card-header">
                            <h2 class="h5 title">사원 상세 조회</h2>
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

                                <!-- 버튼: 우측 정렬, 기능 동일 -->
                                <div class="btn-area">
                                    <button type="button" class="btn btn-primary"
                                        onclick="location.href='${pageContext.request.contextPath}/emp/modifyForm?emp_code=${empDto.emp_code}'">
                                        사원정보 수정
                                    </button>

                                    <button type="button" class="btn btn-soft-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
                                        퇴직 처리
                                    </button>

                                    <button type="button" class="btn btn-brown-outline"
                                        onclick="location.href='${pageContext.request.contextPath}/emp/empList'">
                                        사원 목록으로
                                    </button>
                                </div>

                                <!-- 삭제 모달 (동일 기능) -->
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
                                                <button type="button" class="btn btn-brown-outline" data-bs-dismiss="modal">취소</button>
                                                <button type="button" class="btn btn-soft-danger"
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
