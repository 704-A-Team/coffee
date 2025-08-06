<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 신청</title>

    <!-- 부트스트랩 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <script>
    // 생산 신청 확인/취소 알림
    function handleRequest() {
        if (confirm("생산신청을 하시겠습니까?")) {
            alert("생산신청이 접수되었습니다");
            // TODO: 추후 실제 서버 전송이 필요하면 form.submit() 호출 (수정 될 수 있음)
        } else {
            alert("생산신청이 취소되었습니다");
        }
    }
    </script>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="container mt-4 flex-grow-1 p-4">
            <!-- 화면 제목 -->
            <h1 class="mb-4">생산 신청</h1>

            <!-- 생산 신청 폼 -->
            <form id="productionForm">
                <div class="row mb-3 align-items-center">
                    <label for="productSelect" class="col-sm-2 col-form-label">완제품 목록</label>
                    <div class="col-sm-6">
                        <select id="productSelect" name="productCode" class="form-select">
                            <c:forEach var="prod" items="${inventoryList}">
                                <option value="${prod.product_code}">
                                    ${prod.product_name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-8">
                        <button type="button"
                                class="btn btn-primary"
                                onclick="handleRequest()">
                            생산 신청
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <%@ include file="../footer.jsp" %>
    </div>
</body>
</html>
