<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 상세 조회</title>

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
        .btn-brown {
            background-color: var(--soft-brown) !important;
            color: #fff !important;
            border: 1px solid var(--soft-brown) !important;
        }
        .btn-brown:hover {
            background-color: var(--main-brown) !important;
            border-color: var(--main-brown) !important;
            color: #fff !important;
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

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container mt-3">
                    <div class="form-section-title">거래처 상세보기</div>

                    <div class="card card-detail">
                        <table class="table table-borderless mb-0">
                            <tbody>
                                <tr>
                                    <th>거래처 코드</th>
                                    <td>${clientDto.client_code}</td>
                                </tr>
                                <tr>
                                    <th>거래처명</th>
                                    <td class="fw-bold" style="color: var(--main-brown);">
                                        ${clientDto.client_name}
                                    </td>
                                </tr>
                                <tr>
                                    <th>사업자등록번호</th>
                                    <td>${clientDto.saup_num}</td>
                                </tr>
                                <tr>
                                    <th>대표자명</th>
                                    <td>${clientDto.boss_name}</td>
                                </tr>
                                <tr>
                                    <th>거래처 유형</th>
                                    <td>${clientDto.client_type_br}</td>
                                </tr>
                                <tr>
                                    <th>주소</th>
                                    <td>${clientDto.client_address}</td>
                                </tr>
                                <tr>
                                    <th>전화번호</th>
                                    <td>${clientDto.client_tel}</td>
                                </tr>
                                <tr>
                                    <th>담당 사원</th>
                                    <td>${clientDto.client_emp_name}</td>
                                </tr>
                                <c:set var="statusText">
                                    <c:choose>
                                        <c:when test="${clientDto.client_status == 0}">영업</c:when>
                                        <c:when test="${clientDto.client_status == 1}">휴업</c:when>
                                        <c:when test="${clientDto.client_status == 2}">폐점</c:when>
                                        <c:otherwise>기타</c:otherwise>
                                    </c:choose>
                                </c:set>
                                <tr>
                                    <th>상태</th>
                                    <td>${statusText}</td>
                                </tr>
                                <tr>
                                    <th>등록일</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty clientDto.clientRegDateFormatted}">
                                                ${clientDto.clientRegDateFormatted}
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${clientDto.client_reg_date}" pattern="yyyy-MM-dd"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- 버튼: 카드 밖, 오른쪽 정렬 -->
                    <div class="d-flex justify-content-end gap-3 mt-4 mb-5">
                        <a href="${pageContext.request.contextPath}/client/modifyForm?client_code=${clientDto.client_code}"
                           class="btn btn-primary">수정</a>

                        <!-- 필요 시 삭제 버튼 켜기
                        <form action="${pageContext.request.contextPath}/client/delete" method="post"
                              onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <input type="hidden" name="client_code" value="${clientDto.client_code}">
                            <button type="submit" class="btn btn-soft-danger">삭제</button>
                        </form>
                        -->

                        <!-- 목록 버튼: 프로젝트 표준 -->
                        <a href="${pageContext.request.contextPath}/client/clientList"
                           class="btn btn-brown-outline">목록</a>
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>
