<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>재고관리</title>
    <!-- 부트스트랩 CSS CDN 링크 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
        let isClosed = false;

        function toggleClose(state) {
            isClosed = state;

            document.querySelectorAll(".action-btn").forEach(btn => {
                if (isClosed) {
                    btn.classList.add("disabled-button");
                } else {
                    btn.classList.remove("disabled-button");
                }
            });
        }
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
                    <th>삭제구분</th>
                    <th>사원코드</th>
                    <th>등록일</th>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${inventoryList}">
                    <tr>
                        <td>${item.productCode}</td>
                        <td>${item.productName}</td>
                        <td>${item.productDesc}</td>
                        <td>${item.unit}</td>
                        <td>${item.productType}</td>
                        <td>${item.yield}</td>
                        <td>${item.baseWeight}</td>
                        <td>${item.deliverable}</td>
                        <td>${item.productionUnit}</td>
                        <td>${item.delFlag}</td>
                        <td>${item.empCode}</td>
                        <td>${item.regDate}</td>
                        <td>
                            <button class="btn btn-success action-btn">수주</button>
                            <button class="btn btn-warning action-btn">발주</button>
                            <button class="btn btn-info action-btn">생산</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 페이징 영역 -->
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <c:if test="${page.startPage > page.pageBlock}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${page.startPage - page.pageBlock}">이전</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                    <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${page.endPage < page.totalPage}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${page.startPage + page.pageBlock}">다음</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</body>
</html>
