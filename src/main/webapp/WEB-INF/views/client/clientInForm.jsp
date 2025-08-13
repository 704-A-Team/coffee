<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .form-container {
        max-width: 70%;
        margin: 0 auto;
        padding: 20px;
        background-color: #FFBB00;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
</style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container">
                    <div class="form-container bg-primary bg-opacity-25">
                        <h2 class="text-center mb-4">거래처 등록</h2>
                        <form action="${pageContext.request.contextPath}/client/saveClient" method="post">
                            
                            <!-- 거래처명 -->
                            <div class="mb-3">
                                <label class="form-label">거래처명</label>
                                <input type="text" name="client_name" class="form-control" required>
                            </div>

                            <!-- 사업자등록번호 -->
                            <div class="mb-3">
                                <label class="form-label">사업자등록번호</label>
                                <input type="text" name="saup_num" class="form-control" required>
                            </div>

                            <!-- 대표자명 -->
                            <div class="mb-3">
                                <label class="form-label">대표자명</label>
                                <input type="text" name="boss_name" class="form-control" required>
                            </div>

                            <!-- 거래처 유형 -->
                            <div class="mb-3">
                                <label class="form-label">거래처 유형</label>
                                <select name="client_type" class="form-select" required>
                                    <option value="2">공급처</option>
                                    <option value="3">가맹점</option>
                                </select>
                            </div>

                            <!-- 주소 -->
                            <div class="mb-3">
                                <label class="form-label">주소</label>
                                <input type="text" name="client_address" class="form-control" required>
                            </div>

                            <!-- 전화번호 -->
                            <div class="mb-3">
                          		<label for="client_tel" class="form-label">전화번호</label>
								<input type="text" class="form-control" id="client_tel" name="client_tel" maxlength="13" required>
					  </div>
                <script>
				document.addEventListener("DOMContentLoaded", function () {
				    const telInput = document.getElementById("client_tel");
				
				    telInput.addEventListener("input", function () {
				        let value = telInput.value.replace(/[^0-9]/g, ""); // 숫자만
				
				        if (value.length === 9) {
				            value = value.replace(/(\d{2})(\d{3})(\d{4})/, "$1-$2-$3");
				        } else if (value.length === 11) {
				            value = value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
				        } else if (value.length === 10) {
				            if (value.startsWith("02")) {
				                value = value.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
				            } else {
				                value = value.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
				            }
				        }
				        telInput.value = value;
				    });
				});
				</script>


                            <!-- 담당 사원 -->
                            <div class="mb-3">
                                <label class="form-label">담당 사원</label>
                                <select name="client_emp_code" class="form-select" required>
                                 <c:forEach var="emp" items="${empList}">
							    <c:if test="${emp.emp_dept_code == 1000}">
							        <option value="${emp.emp_code}">[${emp.emp_code}] ${emp.emp_name}</option>
							    </c:if>
							</c:forEach>
                                </select>
                            </div>

                            <!-- 버튼 -->
                            <div class="d-grid gap-2 mt-4">
                                <button type="submit" class="btn btn-success btn-lg">등록</button>
                                <button type="reset" class="btn btn-secondary btn-lg">초기화</button>
                                <button type="button" class="btn btn-secondary btn-lg"
                                        onclick="location.href='${pageContext.request.contextPath}/client/clientList'">
                                    목록으로
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>

</body>
</html>
