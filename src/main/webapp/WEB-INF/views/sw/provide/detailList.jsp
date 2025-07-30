<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>원재료 상세 정보</title>
    <style>
        table th {
            width: 150px;
            white-space: nowrap;
        }
        table {
            width: 70%;
            margin: 0 auto;
        }
        .btn-wrapper {
            width: 70%;
            margin: 30px auto 0 auto;
            text-align: center;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- HEADER -->
    <%@ include file="../../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <!-- SIDEBAR -->
        <%@ include file="../../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <!-- 본문 -->
            <main class="flex-grow-1 p-4">
                <div class="container mt-4">
                    <h2 class="text-center mb-4">원재료 상세 정보</h2>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th scope="row">원재료 코드</th>
                                <td>${provideDetail.provide_code}</td>
                            </tr>
                            <tr>
                                <th scope="row">제품명</th>
                                <td>${provideDetail.productName}</td>
                            </tr>
                            <tr>
                                <th scope="row">거래처명</th>
                                <td>${provideDetail.clientName}</td>
                            </tr>
                            <tr>
                                <th scope="row">공급단위</th>
                                <td>${provideDetail.provide_amount}</td>
                            </tr>
                            <tr>
                                <th scope="row">단위</th>
                                <td>${provideDetail.unitName}</td>
                            </tr>
                            <tr>
                                <th scope="row">단가</th>
                                <td>${provideDetail.current_danga}</td>
                            </tr>
                            <tr>
                                <th scope="row">삭제구분</th>
                                <td>${provideDetail.provide_isdel}</td>
                            </tr>
                            <tr>
                                <th scope="row">등록일</th>
                                <td>${provideDetail.provide_reg_date}</td>
                            </tr>
                        </tbody>
                    </table>

                    <!-- 버튼 영역 -->
                    <div class="btn-wrapper">
                        <a href="${pageContext.request.contextPath}/provide/provideModifyForm?provide_code=${provideDetail.provide_code}" class="btn btn-primary me-3">
                            수정
                        </a>
                        <form action="${pageContext.request.contextPath}/provide/provideDelete" method="post" style="display:inline;">
					        <input type="hidden" name="provide_code" value="${provideDetail.provide_code}">
					        <button type="submit" class="btn btn-danger"
					                onclick="return confirm('정말 삭제하시겠습니까?');">
					            삭제
					        </button>
    					</form>
                    </div>
                </div>
            </main>

            <!-- FOOTER -->
            <%@ include file="../../footer.jsp" %>
        </div>
    </div>

</body>
</html>
