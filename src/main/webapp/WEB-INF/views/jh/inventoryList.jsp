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
        .disabled-button {
            pointer-events: none;
            opacity: 0.5;
        }
        .button-right {
            float: right;
            margin-left: 10px;
        }
        
        .action-column {
      		width: 270px;  /* 원하는 너비(px, %, rem 등으로 조정) */
    	}
    	
    	 .fixed-btn {
  			display: inline-flex;            /* flex로 중앙정렬 */
  			align-items: center;             /* 수직 중앙정렬 */
  			justify-content: center;         /* 수평 중앙정렬 */
  			height: 40px;                    /* 원하는 고정 높이 */
  			padding: 0 .75rem;               /* 좌우 패딩 */
  			box-sizing: border-box;          /* 패딩/보더 포함해서 높이 계산 */
		}
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
    
 // 2) pagination 링크의 href 파라미터도 갱신
    document.querySelectorAll('.pagination a').forEach(link => {
        const linkUrl = new URL(link.href, window.location.origin);
        linkUrl.searchParams.set('isClosed', isClosed);
        link.href = linkUrl.toString();
    });
  }

  // 23시 59분 자동 마감 로직
  function checkTimeAndClose() {
    const now = new Date();
    if (now.getHours() === 23 && now.getMinutes() >= 59) {
      toggleClose(true);
    }
  }

  // 페이지 로드 시 한 번만 실행
  window.addEventListener('load', () => {
    // ① 서버에서 넘어온 상태 반영
    toggleClose(isClosed);
    // ② 자동 마감 체크
    checkTimeAndClose();
  });
</script>


</head>

<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>
    <div class="container mt-4 flex-grow-1 p-4">
    
        <!-- 버튼 그룹 통합 -->
<div class="d-flex justify-content-between align-items-center mb-3">
  <!-- 왼쪽 그룹: 수주/발주/생산 -->
  <div class="d-flex align-items-center">
    <button type="button"
            class="btn btn-secondary fixed-btn action-btn ${isClosed ? 'disabled-button' : ''} me-2">
      수주
    </button>
    <button type="button"
            class="btn btn-secondary fixed-btn action-btn ${isClosed ? 'disabled-button' : ''} me-2">
      발주
    </button>
    <a href="${pageContext.request.contextPath}/jh/mfgRequest"
       class="btn btn-secondary fixed-btn action-btn ${isClosed ? 'disabled-button' : ''}">
      생산
    </a>
  </div>

  <!-- 오른쪽 그룹: 마감/해제 -->
  <div class="d-flex align-items-center">
    <button class="btn btn-danger fixed-btn me-2" onclick="toggleClose(true)">마감</button>
    <button class="btn btn-secondary fixed-btn"   onclick="toggleClose(false)">마감해제</button>
  </div>
</div>

        <table class="table table-bordered text-center">
            <thead class="table-primary">
                <tr>
                    <th>제품코드</th>
                    <th>제품명</th>
                    <th>제품설명</th>
                    <th>단위</th>
                    <th>제품유형</th>
                    <th>납품여부</th>
                    <th class="action-column">기초재고량</th>				   <!-- 클래스 추가 -->
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
                                <td>${item.isorderName}</td>		<!-- 클래스 추가 -->
                                <td class="action-column"></td>       
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="12">표시할 데이터가 없습니다.</td>
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
        <%@ include file="../footer.jsp" %>
    </div>
    </div>
</body>
</html>
