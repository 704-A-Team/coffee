<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주 등록</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Select2 -->
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<style>
	:root {
	  --main-brown: #6f4e37;
	  --light-brown: #e6d3c1;
	  --dark-brown: #4e342e;
	  --soft-brown: #bfa08e;
	}
	body { background-color:#f9f5f1; }
	.form-section-title {
	  border-left: 5px solid var(--main-brown);
	  padding-left: 12px; margin-bottom:16px;
	  font-weight:700; font-size:1.6rem; color:var(--dark-brown);
	}
	.card-form { background:#fff; padding:16px; border:1px solid #e0e0e0; border-radius:8px; box-shadow:0 4px 8px rgba(0,0,0,.05); height:100%; }
	.btn-brown { background:#fff!important; color:var(--main-brown)!important; border:1px solid var(--main-brown)!important; transition:.2s; }
	.btn-brown:hover { background:var(--main-brown)!important; color:#fff!important; }
	.btn-brown-outline { background:transparent!important; color:var(--main-brown)!important; border:1px solid var(--main-brown)!important; transition:.2s; }
	.btn-brown-outline:hover { background:var(--main-brown)!important; color:#fff!important; }
	.btn-secondary-custom { background:#eee!important; color:#333!important; border:1px solid #ccc!important; }
	.btn-secondary-custom:hover { background:#ccc!important; }
	
	/* Select2 */
	.select2-results__options { max-height: 400px !important; }
	.select2-container--default .select2-results__option--highlighted[aria-selected] {
	  background-color: var(--soft-brown) !important; color:#fff !important;
	}
	
	/* 레이아웃 */
	.pane-top { height:35vh; overflow:auto; }
	.pane-bottom { height:65vh; overflow:auto; }
	#provider-list { max-height:170px; overflow-y:auto; }
	.pane-bottom { height:auto; overflow:visible; }
	
	.btn-brown-outline:hover {
	    background: #ccc !important;  /* 진한 회색 */
	    color: #333 !important;        /* 글자색 진회색 */
	    border-color: #ccc !important; /* 테두리색도 회색 */
	    
	}
</style>
</head>
<body class="d-flex flex-column min-vh-100">

  <%@ include file="../../header.jsp" %>

  <div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
      <main class="flex-grow-1 p-3">
        <div class="container-fluid">
          <form action="${pageContext.request.contextPath}/sw/purchaseSave" method="post" id="purchaseForm">

            <!-- 상단: 35% -->
            <div class="pane-top mb-2">
              <div class="card-form">
                <div class="form-section-title">제품 검색</div>

                <select id="product_code" name="product_code" class="form-select">
                  <option value="">-- 제품을 선택하세요 --</option>
                  <c:forEach var="product" items="${productIsList}">
                    <option value="${product.product_code}">${product.product_name}</option>
                  </c:forEach>
                </select>

                <div class="mt-2">
                  <div class="small text-muted">이 제품을 공급하는 거래처</div>
                  <ul id="provider-list" class="list-group mt-1" style="max-height:170px; overflow-y:auto;"></ul>
                </div>
              </div>
            </div>

            <!-- 하단: 65% -->
            <div class="pane-bottom">
              <!-- 발주 등록 카드폼 -->
              <div class="card-form d-flex flex-column">
                <div class="form-section-title">발주 등록</div>

                <!-- 발주 행들이 들어갈 영역 -->
                <div id="purchase-rows">
                  <div class="row g-3 align-items-end mb-2 purchase-row">
                    <!-- 거래처 선택 -->
                    <div class="col-md-2">
                      <label class="form-label">거래처</label>
                      <select name="purchase_client_code[]" class="form-select js-client" required>
                        <option value="">-- 선택 --</option>
                        <c:forEach var="client" items="${clientIsList}">
                          <option value="${client.client_code}">${client.client_name}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <!-- 제품 선택(해당 거래처가 공급하는 제품) -->
                    <div class="col-md-2">
                      <label class="form-label">제품</label>
                      <select name="product_won_code[]" class="form-select js-product" required>
                        <option value="">-- 선택 --</option>
                      </select>
                    </div>

                    <!-- 공급 단위(표시만) -->
                    <div class="col-md-1">
                      <label class="form-label">공급 단위</label>
                      <input type="text" class="form-control js-provide-amount-view" placeholder="자동 입력" readonly>
                    </div>

                    <!-- 단가(표시 + hidden 전송) -->
                    <div class="col-md-1">
                      <label class="form-label">단가</label>
                      <input type="text" class="form-control js-current-danga-view" placeholder="자동 입력" readonly>
                      <input type="hidden" name="purchase_danga[]" class="js-purchase-danga">
                    </div>

                    <!-- 수량(배수) -->
                    <div class="col-md-2">
                      <label class="form-label">수량</label>
                      <select name="purchase_amount[]" class="form-select js-qty" required>
                        <option value="">-- 선택 --</option>
                      </select>
                    </div>

                    <!-- 총 금액 -->
                    <div class="col-md-2">
                      <label class="form-label">총 금액 (₩)</label>
                      <input type="text" class="form-control js-total" name="total_price[]" readonly>
                    </div>

                    <!-- 행 삭제 -->
                    <div class="col-md-2 d-grid">
                      <button type="button" class="btn btn-outline-danger btn-remove">삭제</button>
                    </div>
                  </div>
                </div>

                <!-- 카드폼 안 왼쪽 하단 +추가 버튼 -->
                <div class="mt-2">
                  <button type="button" id="btn-add-row" class="btn btn-success">+ 추가</button>
                </div>
              </div>

              <!-- 카드폼 밖 버튼 -->
              <!-- 카드폼 밖 버튼 -->
			<div class="d-flex justify-content-end gap-2 mt-3">
			  <button type="submit" class="btn btn-primary">등록</button>
			  <button type="reset" class="btn btn-secondary-custom">초기화</button>
			  <a href="${pageContext.request.contextPath}/sw/purchaseList" class="btn btn-brown-outline">목록</a>
			</div>
            </div>
          </form>
        </div>
      </main>

      <%@ include file="../../footer.jsp" %>
    </div>
  </div>

  <script>
    $(function () {
      /* ====== 상단 제품 → 공급 거래처 목록 ====== */
      $('#product_code').select2({
        placeholder: '제품명을 선택하거나 검색하세요',
        minimumInputLength: 0,
        width: '100%',
        language: { noResults: () => "일치하는 제품이 없습니다" }
      });

      $('#product_code').on('change', function () {
        const ajax_product_code = $(this).val();
        const $list = $('#provider-list').empty();
        if (!ajax_product_code) return;

        $.getJSON(
          '${pageContext.request.contextPath}/sw/getClientsByProduct',
          { product_code: ajax_product_code }
        )
        .done(list => {
          if (!list || list.length === 0) {
            $list.append('<li class="list-group-item text-muted">거래처가 없습니다</li>');
            return;
          }
          list.forEach(c => $list.append('<li class="list-group-item">' + c.client_name + '</li>'));
        })
        .fail(xhr => {
          console.error(xhr.responseText);
          $list.append('<li class="list-group-item text-danger">조회 실패</li>');
        });
      });

      /* ====== 제한/유틸 ====== */
      const fmt = n => n.toLocaleString();

      // 현재 선택된 거래처가 공급하는 제품 개수
      let currentClientProductCount = null;

      function updateAddButtonState() {
        const rowCount = $('.purchase-row').length;
        if (currentClientProductCount == null) {
          $('#btn-add-row').prop('disabled', true).attr('title', '먼저 거래처를 선택하세요.');
          return;
        }
        if (rowCount >= currentClientProductCount) {
          $('#btn-add-row').prop('disabled', true)
            .attr('title', `이 거래처의 공급 제품은 총 ${currentClientProductCount}개입니다.`);
        } else {
          $('#btn-add-row').prop('disabled', false).removeAttr('title');
        }
      }

      function resetRow($row) {
        $row.find('.js-product').empty().append('<option value="">-- 선택 --</option>');
        $row.find('.js-provide-amount-view').val('').removeData('amount');
        $row.find('.js-current-danga-view').val('');
        $row.find('.js-purchase-danga').val('');
        $row.find('.js-qty').empty().append('<option value="">-- 선택 --</option>');
        $row.find('.js-total').val('');
      }

      function fillQty($row, provideAmount, max = 50) {
        const $qty = $row.find('.js-qty').empty().append('<option value="">-- 선택 --</option>');
        for (let i = 1; i <= max; i++) {
          const totalAmount = provideAmount * i * 10;
          $qty.append('<option value="' + totalAmount + '">' + totalAmount + '</option>');
        }
      }

      function recalcTotal($row) {
        const qty = Number($row.find('.js-qty').val() || 0);
        const danga = Number($row.find('.js-purchase-danga').val() || 0);
        const provideAmount = Number($row.find('.js-provide-amount-view').data('amount') || 0);

        if (!qty || !danga || !provideAmount) {
          $row.find('.js-total').val('');
          return;
        }
        const total = (danga / provideAmount) * qty;
        $row.find('.js-total').val(fmt(total));
      }

      // 중복 선택 방지: 이미 선택된 제품은 다른 행에서 비활성화
      function updateProductOptions() {
        const selectedProducts = $('.js-product').map(function () {
          return $(this).val() || '';
        }).get().filter(v => v !== '');

        $('.js-product').each(function () {
          const currentVal = $(this).val();
          $(this).find('option').each(function () {
            const val = $(this).val();
            if (val !== '' && val !== currentVal && selectedProducts.includes(val)) {
              $(this).prop('disabled', true);
            } else {
              $(this).prop('disabled', false);
            }
          });
        });
      }

      // 거래처별 공급제품 목록 로드 + 제품개수 기억
      function loadProductsForClient($row, clientCode, onDone) {
        resetRow($row);

        if (!clientCode) {
          currentClientProductCount = null;
          if (onDone) onDone([]);
          updateProductOptions();
          updateAddButtonState();
          return;
        }

        $.getJSON(
          '${pageContext.request.contextPath}/sw/getProductsByClient',
          { provide_client_code: clientCode }
        )
        .done(list => {
          const $prod = $row.find('.js-product').empty().append('<option value="">-- 선택 --</option>');
          if (!list || list.length === 0) {
            $prod.append('<option value="">-- 공급 제품 없음 --</option>');
          } else {
            list.forEach(item => {
              $prod.append($('<option>').val(item.product_won_code).text(item.productName));
            });
          }

          currentClientProductCount = (list && Array.isArray(list)) ? list.length : 0;

          if (onDone) onDone(list);
          updateProductOptions();
          updateAddButtonState();
        })
        .fail(xhr => {
          console.error('getProductsByClient:', xhr.responseText);
          $row.find('.js-product').append('<option value="">-- 조회 실패 --</option>');
          currentClientProductCount = null;
          if (onDone) onDone(null);
          updateProductOptions();
          updateAddButtonState();
        });
      }

      /* ====== 거래처 선택 → 제품목록 채우기 ====== */
	  $(document).on('change', '.js-client', function () {
	    const $row = $(this).closest('.purchase-row');
	    const clientCode = $(this).val();
	
	    if ($row.is('.purchase-row:first')) {
	      $('#purchase-rows .purchase-row').not(':first').remove();
	    }
	
	    // 첫째 행 또는 일반 행 모두: 자신의 제품 목록 로드 및 초기화
	    loadProductsForClient($row, clientCode, function () {
	      // (이전 로직에서 나머지 행을 같은 거래처로 맞추던 부분은 제거)
	      updateProductOptions();
	      updateAddButtonState();
	    });
	  });

      /* ====== 제품 선택 → 공급정보/수량/총액 ====== */
      $(document).on('change', '.js-product', function () {
        const $row = $(this).closest('.purchase-row');
        const ajax_product_code = $(this).val();
        const ajax_client_code = $row.find('.js-client').val();

        $row.find('.js-provide-amount-view').val('').removeData('amount');
        $row.find('.js-current-danga-view').val('');
        $row.find('.js-purchase-danga').val('');
        $row.find('.js-qty').empty().append('<option value="">-- 선택 --</option>');
        $row.find('.js-total').val('');

        updateProductOptions();

        if (!ajax_product_code || !ajax_client_code) return;

        $.getJSON(
          '${pageContext.request.contextPath}/sw/getProvideInfo',
          { product_won_code: ajax_product_code, provide_client_code: ajax_client_code }
        )
        .done(data => {
          const provide_amount = Number(data.provide_amount);
          const current_danga = Number(data.current_danga);
          const unitName = data.unitName || '';

          $row.find('.js-provide-amount-view')
            .val(unitName ? `${provide_amount} ${unitName}` : provide_amount)
            .data('amount', provide_amount);

          $row.find('.js-current-danga-view').val(current_danga.toLocaleString());
          $row.find('.js-purchase-danga').val(current_danga);

          fillQty($row, provide_amount, 50);
          recalcTotal($row);

          updateProductOptions();
        })
        .fail(xhr => console.error('getProvideInfo:', xhr.responseText));
      });

      /* ====== 수량 변경 → 총액 재계산 ====== */
      $(document).on('change', '.js-qty', function () {
        recalcTotal($(this).closest('.purchase-row'));
      });

      /* ====== +추가 (제한 적용) ====== */
      $('#btn-add-row').on('click', function () {
        if (currentClientProductCount == null) {
          alert('먼저 첫 번째 행에서 거래처를 선택하세요.');
          return;
        }
        const rowCount = $('.purchase-row').length;
        if (rowCount >= currentClientProductCount) {
          alert(`이 거래처는 제품이 ${currentClientProductCount}개 등록되어 있습니다. 더 이상 추가할 수 없습니다.`);
          updateAddButtonState();
          return;
        }

        const $first = $('.purchase-row:first');
        const clientCode = $first.find('.js-client').val();

        if (!clientCode) {
          alert('먼저 첫 번째 행에서 거래처를 선택하세요.');
          return;
        }

        const $new = $first.clone(true, true);

        // 새 행 초기화
        $new.find('label').remove();
        $new.find('.js-product').empty().append('<option value="">-- 선택 --</option>');
        $new.find('.js-provide-amount-view').val('').removeData('amount');
        $new.find('.js-current-danga-view').val('');
        $new.find('.js-purchase-danga').val('');
        $new.find('.js-qty').empty().append('<option value="">-- 선택 --</option>');
        $new.find('.js-total').val('');

        // 거래처 고정 + 숨김
        $new.find('.js-client')
          .val(clientCode)
          .addClass('invisible')
          .attr({ 'tabindex': '-1', 'aria-hidden': 'true' });

        $('#purchase-rows').append($new);

        updateProductOptions();
        loadProductsForClient($new, clientCode, function () {
          updateProductOptions();
          updateAddButtonState(); // 추가 후 상태 갱신
        });
      });

      /* ====== 행 삭제 ====== */
      $(document).on('click', '.btn-remove', function () {
        if ($('.purchase-row').length > 1) {
          $(this).closest('.purchase-row').remove();
          updateProductOptions();
          updateAddButtonState(); // 삭제 후 상태 갱신
        } else {
          alert('최소 1개 행은 있어야 합니다.');
        }
      });

      // 초기 상태
      updateProductOptions();
      updateAddButtonState(); // 거래처 미선택 상태에서 +추가 비활성화
    });
  </script>
</body>
</html>
