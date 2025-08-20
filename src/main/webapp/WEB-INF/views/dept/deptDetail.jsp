<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부서 상세 조회</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --main-brown: #6f4e37;
            --light-brown: #e6d3c1;
            --dark-brown: #4e342e;
            --soft-brown: #bfa08e;
            --danger-red: #a94442;
        }

        body {
            background-color: #f9f5f1;
            font-family: 'Segoe UI', sans-serif;
        }

        .form-section-title {
            border-left: 5px solid var(--main-brown);
            padding-left: 12px;
            margin-bottom: 24px;
            font-weight: 700;
            font-size: 1.8rem;
            color: var(--dark-brown);
        }

        .card-detail {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            padding: 24px;
            position: relative;
        }

        .table th {
            color: #555;
            width: 170px;
            font-weight: 600;
        }
        .table td { color: #333; }

        /* 프로젝트 표준 버튼 */
        .btn-brown-outline {
            border: 1px solid var(--main-brown) !important;
            color: var(--main-brown) !important;
            background-color: #fff !important;
        }
        .btn-brown-outline:hover {
            background-color: #ccc !important; /* 회색 배경 */
            color: #333 !important;            /* 진회색 글자 */
            border-color: #ccc !important;     /* 회색 테두리 */
        }
        .btn-soft-danger {
            background-color: var(--danger-red) !important;
            color: #fff !important;
            border: 1px solid var(--danger-red) !important;
        }
        .btn-soft-danger:hover {
            background-color: #922d2b !important;
            border-color: #922d2b !important;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 + 푸터 래퍼 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3">
                    <div class="form-section-title">부서 상세보기</div>

                    <div class="card card-detail">
                        <table class="table table-borderless mb-0">
                            <tbody>
                                <tr>
                                    <th>부서 코드</th>
                                    <td>${deptDto.dept_code}</td>
                                </tr>
                                <tr>
                                    <th>부서 이름</th>
                                    <td class="fw-bold" style="color: var(--main-brown);">
                                        ${deptDto.dept_name}
                                    </td>
                                </tr>
                                <tr>
                                    <th>부서 대표 전화</th>
                                    <td>${deptDto.dept_tel}</td>
                                </tr>
                                <tr>
                                    <th>등록일</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty deptDto.deptRegDateFormatted}">
                                                ${deptDto.deptRegDateFormatted}
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${deptDto.dept_reg_date}" pattern="yyyy-MM-dd"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- 버튼: 카드 밖, 오른쪽 정렬 -->
                    <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                        <a href="${pageContext.request.contextPath}/dept/modifyForm?dept_code=${deptDto.dept_code}"
                           class="btn btn-primary">수정</a>

                        <!-- 삭제 모달 트리거 -->
                        <button type="button" class="btn btn-soft-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">
                            삭제
                        </button>

                        <!-- 목록 버튼: 프로젝트 표준 -->
                        <a href="${pageContext.request.contextPath}/dept/deptList"
                           class="btn btn-brown-outline">목록</a>
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
        <!-- /본문+푸터 래퍼 -->
    </div>

    <!-- 삭제 모달 (기능 유지, 버튼 테마만 적용) -->
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="deleteConfirmModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteConfirmModalLabel">삭제 확인</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                </div>
                <div class="modal-body">
                    이 부서를 삭제하시겠습니까? 삭제된 정보는 되돌릴 수 없습니다.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-brown-outline" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-soft-danger"
                            onclick="location.href='${pageContext.request.contextPath}/dept/deptDelete?dept_code=${deptDto.dept_code}'">
                        삭제
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS (모달 동작용) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
