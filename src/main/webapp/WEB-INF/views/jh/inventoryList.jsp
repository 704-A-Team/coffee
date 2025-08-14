<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>재고관리</title>

    <!-- 부트스트랩 및 아이콘 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .disabled-button { pointer-events: none; opacity: 0.5; }
        .action-column { width: 270px; }
        .fixed-btn {
            display: inline-flex; align-items: center; justify-content: center;
            height: 40px; padding: 0 .75rem; box-sizing: border-box;
        }
    </style>

    <script>
      // 서버 값이 null일 때도 안전하게 처리
      let isClosed = ${empty isClosed ? false : isClosed};

      function toggleClose(state) {
        isClosed = !!state;
        document.querySelectorAll(".action-btn").forEach(btn => {
          btn.classList.toggle("disabled-button", isClosed);
        });

        // URL 파라미터 동기화
        const url = new URL(window.location.href);
        url.searchParams.set('isClosed', isClosed);
        window.history.replaceState({}, '', url);

        // 페이징 링크 href 갱신
        document.querySelectorAll('.pagination a').forEach(link => {
          const linkUrl = new URL(link.href, window.location.origin);
          linkUrl.searchParams.set('isClosed', isClosed);
          link.href = linkUrl.toString();
        });
      }

      function checkTimeAndClose() {
        const now = new Date();
        if (now.getHours() === 23 && now.getMinutes() >= 59) {
          toggleClose(true);
        }
      }

      window.addEventListener('load', () => {
        toggleClose(isClosed);
        checkTimeAndClose();
      });
    </script>
</head>

<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1"><!-- 좌/우 영역 -->
        <%@ include file="../sidebar.jsp" %>

        <!-- 본문 래퍼: 푸터 하단 고정용 -->
        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1">
                <div class="container mt-4 p-4">

                    <!-- 버튼 그룹 -->
			<div class="d-flex justify-content-between align-items-center mb-3">
			  <!-- 왼쪽: 수주/발주/생산 -->
			  <div class="d-flex align-items-center">
			    <!-- 수주 버튼 -->
			    <button type="button"
			            class="btn btn-secondary fixed-btn action-btn me-2"
			            onclick="location.href='${pageContext.request.contextPath}/order/new'">
			      수주
			    </button>
			
			    <!-- 발주 버튼 -->
			    <button type="button"
			            class="btn btn-secondary fixed-btn action-btn me-2"
			            onclick="location.href='${pageContext.request.contextPath}/sw/purchaseInForm'">
			      발주
			    </button>
			
			    <!-- 생산 버튼 -->
			    <a href="${pageContext.request.contextPath}/jh/mfgRequest"
			       class="btn btn-secondary fixed-btn action-btn">
			      생산
			    </a>
			  </div>
			
			  <!-- 오른쪽: 마감/해제 -->
			  <div class="d-flex align-items-center">
			    <button class="btn btn-danger fixed-btn me-2" onclick="toggleClose(true)">마감</button>
			    <button class="btn btn-secondary fixed-btn"   onclick="toggleClose(false)">마감해제</button>
			  </div>
			</div>
                    <!-- 테이블 -->
                    <table class="table table-bordered text-center">
                        <thead class="table-primary">
                            <tr>
                                <th>제품코드</th>
                                <th>제품명</th>
                                <th>제품설명</th>
                                <th>단위</th>
                                <th>제품유형</th>
                                <th>납품여부</th>
                                <th class="action-column">기초재고량</th>
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
                                            <td>${item.unitName}</td>
                                            <td>${item.typeName}</td>
                                            <td>${item.isorderName}</td>
                                            <td class="action-column"></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7">표시할 데이터가 없습니다.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- 페이징 -->
                    <c:if test="${not empty page}">
                        <nav aria-label="Page navigation" class="mt-4">
                            <ul class="pagination justify-content-center">
                                <c:if test="${page.startPage > page.pageBlock}">
                                    <c:url var="prevUrl" value="${pageContext.request.contextPath}/jh/inventoryList">
                                        <c:param name="page" value="${page.startPage - page.pageBlock}" />
                                        <c:param name="isClosed" value="${isClosed}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${prevUrl}">이전</a>
                                    </li>
                                </c:if>

                                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                    <c:url var="pageUrl" value="${pageContext.request.contextPath}/jh/inventoryList">
                                        <c:param name="page" value="${i}" />
                                        <c:param name="isClosed" value="${isClosed}" />
                                    </c:url>
                                    <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageUrl}">${i}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${page.endPage < page.totalPage}">
                                    <c:url var="nextUrl" value="${pageContext.request.contextPath}/jh/inventoryList">
                                        <c:param name="page" value="${page.startPage + page.pageBlock}" />
                                        <c:param name="isClosed" value="${isClosed}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${nextUrl}">다음</a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>

                </div><!-- /container -->
            </main>

            <%@ include file="../footer.jsp" %>
        </div><!-- /본문 래퍼 -->
    </div><!-- /좌우 영역 -->
</body>
</html>
