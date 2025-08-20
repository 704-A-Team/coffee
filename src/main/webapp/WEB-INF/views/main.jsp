<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Coffee Yummy</title>
    <style>
	    /* 한 줄 말줄임 */
	    .text-ellipsis {
	      display:inline-block; max-width:200px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; vertical-align:middle;
	    }
	    /* 랭킹 보드 행 기본 */
	    .rank-row{
	      position:relative; border-radius:12px; padding:14px 16px 14px 70px; 
	      background:#fff; box-shadow:0 2px 8px rgba(0,0,0,.05); 
	    }
	    .rank-row + .rank-row{ margin-top:12px; }
	
	    /* 리본 공통 */
	    .rank-ribbon{
	      position:absolute; top:-6px; left:-6px;
	      padding:10px 18px 10px 16px; font-weight:800; color:#343a40;
	      transform:rotate(-8deg);
	      clip-path: polygon(0 0, 100% 0, 88% 100%, 0% 100%); /* 꼬리 */
	      box-shadow:0 4px 10px rgba(0,0,0,.15);
	      border-top-left-radius:8px; border-bottom-left-radius:8px;
	    }
	    .rank-1{ background:linear-gradient(180deg,#ffd34d,#ffb300); color:#3b2f00; } /* Gold */
	    .rank-2{ background:linear-gradient(180deg,#e0e0e0,#bdbdbd); color:#2f2f2f; } /* Silver */
	    .rank-3{ background:linear-gradient(180deg,#f0b27a,#d98850); color:#3b200c; } /* Bronze */
	
	    /* 리본 안의 숫자 원형 */
	    .rank-badge{
	      display:inline-flex; align-items:center; justify-content:center;
	      width:38px; height:38px; border-radius:50%;
	      background:rgba(255,255,255,.9); font-size:1.1rem; margin-right:8px;
	      box-shadow: inset 0 1px 2px rgba(0,0,0,.08);
	    }
	
	    /* 행 안 텍스트 정리 */
	    .rank-title{ font-weight:700; font-size:1.05rem; }
	    .rank-sub  { color:#6c757d; font-size:.85rem; }
	
	    /* 프로젝트 표준 버튼(목록) 컬러 유지 - 기억된 규칙 */
	    .btn-brown-outline {
	      border: 1px solid #6f4e37 !important;
	      color: #6f4e37 !important;
	      background-color: #fff !important;
	    }
	    .btn-brown-outline:hover {
	      background-color: #ccc !important;
	      color: #333 !important;
	      border-color: #ccc !important;
	    }
	
	    /* 부드러운 하이라이트 */
	    .bg-warning-subtle{ background: #fff7e0 !important; }

        /* 코너 대각선 리본 */
		.corner-ribbon{
		  position:absolute; top:6px; left:-22px;
		  transform:rotate(-45deg);
		  width:110px; text-align:center; padding:6px 0;
		  font-weight:800; font-size:.95rem; letter-spacing:.3px;
		  box-shadow:0 3px 10px rgba(0,0,0,.15);
		  border-radius:5px;
		}
		
		/* 본문이 가리지 않도록 */
		.corner-pad{ padding-left:80px; padding-top:16px; }
		
		@media (min-width: 992px){
		  /* 공지사항 카드 조절 */
		  .card-notice { min-height: 260px; } 
		  /* 신규 제품 카드 위아래 조절 */
		  .card-new-products { min-height: 225px; }
		}

        
	  </style>
</head>
<body class="d-flex flex-column min-vh-100">
	<!-- main.jsp의 컨텐츠 영역 예시 -->
		
	<!-- HEADER -->
	<%@ include file="header.jsp" %>
	
	<div class="d-flex flex-grow-1">
		<!-- SIDEBAR -->
		<%@ include file="sidebar.jsp" %>
		
		<div class="d-flex flex-column flex-grow-1">
			
			<!-- 본문 -->
			<main class="flex-grow-1 p-4">
			  <div class="container-fluid">
				<!-- 상단+중단 합치기: 왼쪽(환영+차트), 오른쪽(공지+신규) -->
				<div class="row g-3">
				  <!-- 좌: 환영/로그 + 차트 (세로 스택) -->
				  <div class="col-lg-8 d-flex flex-column gap-3">
				    <!-- 환영/로그인 카드 -->
				    <div class="card shadow-sm">
				      <div class="card-body">
				        <h5 class="card-title mb-3">환영합니다 👋</h5>
				        <sec:authorize access="isAuthenticated()">
				          <p class="mb-1"><strong><c:out value="${displayName}"/></strong> 님, 반갑습니다.</p>
				          <p class="text-muted small mb-0">오늘도 좋은 하루 되세요.</p>
				        </sec:authorize>
				        <sec:authorize access="isAnonymous()">
				          <p class="mb-1">로그인이 필요합니다.</p>
				          <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-sm">로그인</a>
				        </sec:authorize>
				      </div>
				    </div>
				
				    <!-- 차트 카드 -->
				    <div class="card shadow-sm">
				      <div class="card-header fw-semibold">이번달 제품별 매출액 TOP 5</div>
				      <div class="card-body" style="height: 320px;">
				        <div class="h-100">
				          <canvas id="chartTopProducts" class="w-100 h-100"></canvas>
				        </div>
				      </div>
				    </div>
				  </div>
				
				  <!-- 우: 공지 + 신규 제품 (세로 스택) -->
				  <div class="col-lg-4 d-flex flex-column gap-3">
				    <!-- 공지사항 카드(더 길게) -->
				    <div class="card shadow-sm card-notice">
				      <div class="card-header d-flex justify-content-between align-items-center">
				        <span class="fw-semibold">공지사항</span>
				        <sec:authorize access="hasAnyRole('MANAGER','ADMIN','USER','CLIENT','CLIENT2','GUEST')">
				          <a href="${pageContext.request.contextPath}/board/list" class="btn btn-sm btn-outline-secondary" title="게시판">＋</a>
				        </sec:authorize>
				      </div>
				      <div class="card-body">
				        <ul class="list-unstyled mb-0">
				          <c:forEach var="n" items="${noticeTop5}">
				            <li class="mb-2">
				              <a href="${pageContext.request.contextPath}/notice/${n.id}" class="link-primary text-decoration-none">
				                ${n.title}
				              </a>
				              <div class="text-muted small"><fmt:formatDate value="${n.createdAt}" pattern="yyyy-MM-dd"/></div>
				            </li>
				          </c:forEach>
				          <c:if test="${empty noticeTop5}">
				            <div class="text-muted small">등록된 공지가 없습니다.</div>
				          </c:if>
				        </ul>
				      </div>
				    </div>
				
				    <!-- 신규 제품 카드(더 작게) -->
				    <div class="card shadow-sm card-new-products">
				      <div class="card-header fw-semibold">NEW! 제품!</div>
				      <div class="card-body">
				        <ul class="list-group list-group-flush">
				          <c:forEach var="product" items="${newProduct}">
				            <li class="list-group-item d-flex justify-content-between align-items-center">
				              <strong><span>${product.product_name}</span></strong>
				              <span class="text-ellipsis">${product.product_contents}</span>
				            </li>
				          </c:forEach>
				          <c:if test="${empty newProduct}">
				            <li class="list-group-item text-muted small">최근 등록된 제품이 없습니다.</li>
				          </c:if>
				        </ul>
				      </div>
				    </div>
				  </div>
				</div>

			
			    <!-- 하단: 좌(이번달 우수 점포 TOP3), 우(최근 입/출고 내역: 한 칸 + 가운데 구분선) -->
				<div class="row g-3 mt-1">
				<!-- 좌: 우수 점포 TOP3 (리본 스타일 랭킹 보드) -->
	            <div class="col-lg-6">
	              <div class="card shadow-sm h-100">
	                <div class="card-header fw-semibold">이번달 우수 점포 TOP3</div>
	                <div class="card-body">
	
	                  <c:if test="${not empty excellentClient}">
	                    <c:forEach var="Client" items="${excellentClient}" varStatus="status">
	                      <div class="rank-row corner-pad">
	                        <!-- 리본 (코너 대각선, 금/은/동) -->
	                        <div class="corner-ribbon ${status.index==0?'rank-1':(status.index==1?'rank-2':'rank-3')}">
	                          TOP ${status.index + 1}
	                        </div>
	
	                        <!-- 본문 -->
	                        <div class="d-flex align-items-center">
	                          <div class="flex-grow-1">
	                            <div class="rank-title">${Client.clientName}</div>
	                          </div>
	                          <div class="fs-5 fw-bold text-end">
	                            <fmt:formatNumber value="${Client.month_total_price}" type="number"/> 원
	                          </div>
	                        </div>
	                      </div>
	                    </c:forEach>
	                  </c:if>
	                  <c:if test="${empty excellentClient}">
	                    <div class="text-muted small">데이터가 없습니다.</div>
	                  </c:if>
	                </div>
	              </div>
	            </div>
				
				  <!-- 우: 최근 입/출고 (한 카드, 가운데 세로선) -->
				  <div class="col-lg-6">
				    <div class="card shadow-sm h-100">
				      <div class="card-header fw-semibold">최근 입/출고 내역</div>
				      <div class="card-body p-0">
				        <div class="row g-0 align-items-stretch">
					        <!-- 입고 -->
							<div class="col-12 col-md-6 p-3 border-end">
							  <div class="fw-semibold mb-2">입고</div>
							
							  <ul class="list-group list-group-flush">
							    <c:if test="${empty currentPurchase}">
							      <li class="list-group-item text-muted small">최근 입고 내역이 없습니다.</li>
							    </c:if>
							
							    <c:forEach var="purchase" items="${currentPurchase}">
							      <li class="list-group-item">
							        <div class="d-flex justify-content-between">
							          <span>
							            ${purchase.productCnt == 1 
							              ? purchase.productName 
							              : purchase.productName += ' 외 ' += (purchase.productCnt - 1)}
							          </span>
							          <small class="text-muted">
							            <fmt:formatDate value="${purchase.purchase_ipgo_date}" pattern="MM-dd"/>
							          </small>
							        </div>
							      </li>
							    </c:forEach>
							  </ul>
							</div>
	
					          
					        <!-- 출고 -->
							<div class="col-12 col-md-6 p-3">
							  <div class="fw-semibold mb-2">출고</div>
							  <ul class="list-group list-group-flush">
							    <c:if test="${empty currentOrder}">
							      <li class="list-group-item text-muted small">최근 출고 내역이 없습니다.</li>
							    </c:if>
							    <c:forEach var="order" items="${currentOrder}">
							      <li class="list-group-item">
							        <div class="d-flex justify-content-between">
							          <span>
							            ${order.productCnt == 1 
							              ? order.productName 
							              : order.productName += ' 외 ' += (order.productCnt - 1)}
							          </span>
							          <small class="text-muted">
							            ${fn:substring(order.order_ddate, 4, 6)}-${fn:substring(order.order_ddate, 6, 8)}
							          </small>
							        </div>
							      </li>
							    </c:forEach>
							  </ul>
							</div>
				        </div>
				      </div>
				    </div>
				  </div>
				</div>

			
			    <!-- 하단 여백 -->
			    <div class="my-3"></div>
			  </div>
			</main>
			
			<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1"></script>
			<script>
			  (function () {
			    const cv = document.getElementById('chartTopProducts');
			    if (!cv) return; // 카드가 렌더되지 않으면 종료
			
			    // JSON
			    var labels = ${empty topSalesLabels ? "[]" : topSalesLabels};
			    var data   = ${empty topSalesData   ? "[]" : topSalesData};
			
			    if (!Array.isArray(labels) || labels.length === 0 ||
			        !Array.isArray(data)   || data.length   === 0) {
			      // 데이터 없으면 아무것도 그리지 않음
			      // 원하면 카드도 제거하려면 아래 주석 해제:
			      // cv.closest('.card')?.remove();
			      return;
			    }
			
			    var nums = data.map(Number);
			
			    // 중복 생성 방지
			    if (window.__topChart) { try { window.__topChart.destroy(); } catch(e) {} }
			
			    window.__topChart = new Chart(cv, {
			      type: 'bar',
			      data: { labels: labels, datasets: [{ label: '매출액(원)', data: nums }] },
			      options: {
			        responsive: true,
			        maintainAspectRatio: false,
			        scales: { y: { ticks: { callback: function(v){ return Number(v).toLocaleString(); } } } },
			        plugins: {
			          legend: { display: true },
			          tooltip: {
			            callbacks: {
			              label: function (ctx) {
			                return ctx.dataset.label + ': ' + Number(ctx.raw).toLocaleString() + ' 원';
			              }
			            }
			          }
			        }
			      }
			    });
			  })();
			</script>

			<!-- FOOTER -->
			<%@ include file="footer.jsp" %>
		</div>
	</div>
</body>
</html>
