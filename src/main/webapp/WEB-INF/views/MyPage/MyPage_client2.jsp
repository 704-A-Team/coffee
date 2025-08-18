<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가맹점 정보</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
  :root{ --main-brown:#6f4e37; --dark-brown:#4e342e; --soft-brown:#bfa08e; }
  body{ background:#f9f5f1; font-family:'Segoe UI',sans-serif; }
  .form-container{ width:100%; max-width:980px; margin:0 auto; }
  .form-section-title{ border-left:5px solid var(--main-brown); padding-left:12px; margin:6px 0 24px; font-weight:700; font-size:1.8rem; color:var(--dark-brown); }
  .card-detail{ background:#fff; border:1px solid #e0e0e0; box-shadow:0 4px 6px rgba(0,0,0,.05); }
  .card-header{ background:linear-gradient(180deg,#fff,#f4ebe3); border-bottom:1px solid #eadfd6; }
  .card-header .title{ color:var(--main-brown); font-weight:700; margin:0; }
  .table-detail th{ color:#555; width:170px; font-weight:600; vertical-align:middle; }
  .table-detail td{ color:#333; vertical-align:middle; }
  .badge-status { min-width:56px; }
  .content-row{ min-height:0; }
  .content-main{ min-width:0; }
  .btn-brown-outline{ border:1px solid var(--main-brown)!important; color:var(--main-brown)!important; background:#fff!important; }
  .btn-brown-outline:hover{ background:#ccc!important; color:#333!important; border-color:#ccc!important; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">
  <%@ include file="../header.jsp" %>

  <div class="d-flex flex-grow-1 content-row">
    <%@ include file="../sidebar.jsp" %>

    <div class="d-flex flex-column flex-grow-1">
      <main class="flex-grow-1 p-4 content-main">
        <div class="form-container">
          <div class="form-section-title">우리 매장 정보</div>

          <div class="card card-detail shadow-sm">
            <div class="card-header">
              <h2 class="h5 title">기본 정보</h2>
            </div>

            <div class="card-body">
              <table class="table table-borderless table-detail mb-0">
                <tbody>
                  <tr>
                    <th>거래처 코드</th>
                    <td><c:out value="${clientDto.client_code}"/></td>
                  </tr>
                  <tr>
                    <th>거래처명</th>
                    <td class="fw-bold" style="color: var(--main-brown);">
                      <c:out value="${clientDto.client_name}"/>
                    </td>
                  </tr>
                  <tr>
                    <th>사업자등록번호</th>
                    <td><c:out value="${clientDto.saup_num}"/></td>
                  </tr>
                  <tr>
                    <th>대표자명</th>
                    <td><c:out value="${clientDto.boss_name}"/></td>
                  </tr>
                  <tr>
                    <th>거래처 유형</th>
                    <td><c:out value="${clientDto.client_type_br}"/></td>
                  </tr>
                  <tr>
                    <th>주소</th>
                    <td><c:out value="${clientDto.client_address}"/></td>
                  </tr>
                  <tr>
                    <th>전화번호</th>
                    <td><c:out value="${clientDto.client_tel}"/></td>
                  </tr>
                  <tr>
                    <th>담당 사원명</th>
                    <td><c:out value="${clientDto.client_emp_name}"/></td>
                  </tr>
                  <tr>
                    <th>담당 사원 연락처</th>
                    <td><c:out value="${clientDto.client_emp_tel}"/></td>
                  </tr>
                  <tr>
                    <th>영업 상태</th>
                    <td>
                      <c:choose>
                        <c:when test="${clientDto.client_status == 0}">
                          <span class="badge bg-success-subtle text-success badge-status">영업중</span>
                        </c:when>
                        <c:when test="${clientDto.client_status == 1}">
                          <span class="badge bg-warning-subtle text-warning badge-status">휴업중</span>
                        </c:when>
                        <c:when test="${clientDto.client_status == 2}">
                          <span class="badge bg-danger-subtle text-danger badge-status">폐점</span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge bg-secondary-subtle text-dark badge-status">기타</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                  <tr>
                    <th>등록일</th>
                    <td>${clientDto.clientRegDateFormatted}</td>
                  </tr>
                </tbody>
              </table>
            </div><!-- /.card-body -->

            <div class="card-footer bg-transparent border-0 py-2">
              <div class="d-flex justify-content-end gap-3">
                <sec:authorize access="hasRole('CLIENT2')">
                <a href="${pageContext.request.contextPath}/MyPage/changePassword" class="btn btn-brown-outline">
                  비밀번호 변경
                </a>
                </sec:authorize>
              </div>
            </div>
          </div><!-- /.card -->
        </div><!-- /.form-container -->
      </main>

      <%@ include file="../footer.jsp" %>
    </div>
  </div>
</body>
</html>
