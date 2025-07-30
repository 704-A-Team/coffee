<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>재고관리</title>

    <!-- 부트스트랩 및 아이콘 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .disabled-button {
            pointer-events: none;
            opacity: 0.5;
        }
        .button-right {
            float: right;
            margin-left: 10px;
        }
    </style>

    <script>
        let isClosed = ${isClosed == true ? 'true' : 'false'};

        function toggleClose(state) {
            isClosed = state;

            document.querySelectorAll(".action-btn").forEach(btn => {
                if (isClosed) {
                    btn.classList.add("disabled-button");
                } else {
                    btn.classList.remove("disabled-button");
                }
            });

            // 히스토리 조작으로 상태 유지
            const url = new URL(window.location.href);
            url.searchParams.set('isClosed', isClosed);
            window.history.replaceState({}, '', url);
        }

        window.onload = function () {
            toggleClose(isClosed); // 초기 마감 상태 반영
        };
    </script>
</head>

<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-end mb-3">
            <button class="btn btn-danger me-2" onclick="toggleClose(true)">마감</button>
            <button class="btn btn-secondary" onclick="toggleClose(false)">마감해제</button>
        </div>

        <table class="table table-bordered text-center">
            <thead class="table-primary">
                <tr>
                    <th>제품코드</th>
                    <th>제품명</th>
                    <th>제품설명</th>
                    <th>단위</th>
                    <th>제품유형</th>
                    <th>예상 수율</th>
                    <th>기본 중량</th>
                    <th>납품여부</th>
                    <th>생산단위</th>
                    <th>사원코드</th>
                    <th>등록일</th>
                                  </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty inventoryList}">
                        <c:forEach var="item" items="${inventoryList}">
                            <tr>
                                <td>${item.product_code}</td>
                                <td>${item.product_name}</td>
                                <td>${item.product_contents}</td>
                                <td>${item.product_unit}</td>
                                <td>${item.product_type}</td>
                                <td>${item.product_yield}</td>
                                <td>${item.product_weight}</td>
                                <td>${item.product_isorder}</td>
                                <td>${item.product_pack}</td>
                                <td>${item.product_reg_code}</td>
                                <td>${item.product_reg_date}</td>
                                <td>
                                    <button class="btn btn-success action-btn">수주</button>
                                    <button class="btn btn-warning action-btn">발주</button>
                                    <button class="btn btn-info action-btn">생산</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="13">표시할 데이터가 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <!-- 페이징 영역 -->
        <c:if test="${not empty page}">
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <c:if test="${page.startPage > page.pageBlock}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${page.startPage - page.pageBlock}&isClosed=${isClosed}">이전</a>
                        </li>
                    </c:if>

                    <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                        <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&isClosed=${isClosed}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${page.endPage < page.totalPage}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${page.startPage + page.pageBlock}&isClosed=${isClosed}">다음</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>
</body>
</html>
