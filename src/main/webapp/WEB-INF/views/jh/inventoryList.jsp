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
        /* ===== 레이아웃 기본 ===== */
        html, body { height: 100%; }
        body {
          display: flex;
          flex-direction: column;
          min-height: 100vh;
          margin: 0;
        }

        /* 사이드바 + 메인 래퍼 (남은 공간 전부 차지) */
        .layout-wrapper {
          display: flex;
          flex: 1 1 auto;
          min-height: 0;   /* 내부 스크롤 위해 중요 */
        }

        /* 사이드바: 고정폭 + 자체 스크롤 */
        .sidebar-wrap {
          flex: 0 0 190px;     /* sidebar.jsp 내부 width:190px과 맞춤 */
          min-width: 190px;
          max-width: 190px;
          overflow-y: auto;    /* 하단 잘림 방지 (자체 스크롤) */
        }

        /* 메인: 남은 공간 + 자체 스크롤 */
        .main-wrap {
          flex: 1 1 auto;
          min-width: 0;        /* 내용 넘침 방지 */
          min-height: 0;       /* 내부 스크롤 위해 중요 */
          display: flex;
          flex-direction: column;
          overflow: hidden;    /* 안쪽 컨테이너에만 스크롤 주려면 hidden */
        }

        /* 실제 스크롤 되는 컨테이너 */
        .main-scroll {
          flex: 1 1 auto;
          overflow: auto;
        }

        /* ===== 기존 버튼/테이블 스타일 ===== */
        .disabled-button { pointer-events: none; opacity: 0.5; }
        .button-right { float: right; margin-left: 10px; }
        .action-column { width: 270px; }
        .fixed-btn {
          display: inline-flex; align-items: center; justify-content: center;
          height: 40px; padding: 0 .75rem; box-sizing: border-box;
        }

        /* 푸터: 전체 가로 */
        footer { width: 100%; }
    </style>

    <script>
      // 서버에서 Boolean으로 받은 값
      let isClosed = ${isClosed};

      // 버튼 활성/비활성 토글
      function toggleClose(state) {
        isClosed = state;
        document.querySelectorAll(".action-btn").forEach(btn => {
          if (state) btn.classList.add("disabled-button");
          else       btn.classList.remove("disabled-button");
        });

        // URL 파라미터 동기화
        const url = new URL(window.location.href);
        url.searchParams.set('isClosed', isClosed);
        window.history.replaceState({}, '', url);

        // pagination 링크 파라미터도 갱신
        document.querySelectorAll('.pagination a').forEach(link => {
          const linkUrl = new URL(link.href, window.location.origin);
          linkUrl.searchParams.set('isClosed', isClosed);
          link.href = linkUrl.toString();
        });
      }

      // 23시 59분 자동 마감
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
      
      // 하루에 1번만 마감되게 하기
      const MAGAM_KEY = 'magamClosedDate';

      function todayStr() {
        const now = new Date();
        const y = now.getFullYear();
        const m = String(now.getMonth() + 1).padStart(2, '0');
        const d = String(now.getDate()).padStart(2, '0');
        return `${y}-${m}-${d}`;
      }

      function initMagamOncePerDay() {
        const btn = document.getElementById('btnMagam');
        if (!btn) return;

        const saved = localStorage.getItem(MAGAM_KEY);
        const today = todayStr();

        // 이미 오늘 눌렀으면 비활성화
        if (saved === today) {
          btn.classList.add('disabled-button');
          btn.setAttribute('disabled', 'disabled');
          btn.title = '오늘은 이미 마감했습니다.';
        }

        // 클릭 시 하루 1회 제한
        btn.addEventListener('click', (e) => {
          const saved2 = localStorage.getItem(MAGAM_KEY);
          const today2 = todayStr();

          if (saved2 === today2) {
            e.preventDefault();
            alert('오늘은 이미 마감했습니다.');
            return;
          }

          // 실제 마감 동작
          toggleClose(true);

          // 오늘 마감으로 기록 + 버튼 비활성화
          localStorage.setItem(MAGAM_KEY, today2);
          btn.classList.add('disabled-button');
          btn.setAttribute('disabled', 'disabled');
        });
      }

      // ---- 기존 load 핸들러에 한 줄만 추가 ----
      window.addEventListener('load', () => {
        toggleClose(isClosed);
        checkTimeAndClose();
        initMagamOncePerDay(); // << 이 줄 추가
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
  				<button id="btnMagam" class="btn btn-danger fixed-btn me-2">마감</button>
  				<!-- isMagam == true 인 경우에만 마감해제 노출 -->
  				<c:if test="${isMagam}">
    				<button class="btn btn-secondary fixed-btn" onclick="toggleClose(false)">마감해제</button>
  				</c:if>
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
                                        <c:param name="isMagam" value="${isMagam}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${prevUrl}">이전</a>
                                    </li>
                                </c:if>

                                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                    <c:url var="pageUrl" value="${pageContext.request.contextPath}/jh/inventoryList">
                                        <c:param name="page" value="${i}" />
                                        <c:param name="isClosed" value="${isClosed}" />
                                        <c:param name="isMagam" value="${isMagam}" />
                                    </c:url>
                                    <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageUrl}">${i}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${page.endPage < page.totalPage}">
                                    <c:url var="nextUrl" value="${pageContext.request.contextPath}/jh/inventoryList">
                                        <c:param name="page" value="${page.startPage + page.pageBlock}" />
                                        <c:param name="isClosed" value="${isClosed}" />
                                        <c:param name="isMagam" value="${isMagam}" />
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
