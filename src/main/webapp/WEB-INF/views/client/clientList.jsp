<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래처 목록</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .clickable-row { cursor: pointer; }
        .form-section-title { font-weight: 600; font-size: 1.5rem; }

        /* 프로젝트 표준 버튼 (brown-outline 유지) */
        .btn-brown-outline{
          background-color: transparent !important;
          color:#6f4e37 !important; border:1px solid #6f4e37 !important;
        }
        .btn-brown-outline:hover{ background:#ccc !important; color:#333 !important; border-color:#ccc !important; }
        .btn-brown{ background:#6f4e37 !important; color:#fff !important; border:1px solid #6f4e37 !important; }
        .btn-brown:hover{ background:#5f4231 !important; border-color:#5f4231 !important; }

        /* 페이지 폭 살짝 줄이기 (가운데 정렬) */
        .page-wrap { max-width: 1080px; margin: 0 auto; }

        /* 검색 영역과 표를 살짝 아래로 */
        .pushed-down { margin-top: 14px; }

        .search-inline .form-select,
        .search-inline .form-control { min-height: 38px; }

        @media (min-width: 768px){
          .search-inline .kw-col { min-width: 320px; }
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>

    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>

        <div class="d-flex flex-column flex-grow-1">
            <main class="flex-grow-1 p-4">
                <div class="container p-0 page-wrap pushed-down">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="form-section-title m-0">거래처 목록</div>
                        <a href="${pageContext.request.contextPath}/client/clientInForm" class="btn btn-brown-outline">거래처 등록</a>
                    </div>

                    <!-- 검색바 (한 줄 배치) -->
                    <form action="${pageContext.request.contextPath}/client/clientList" method="get"
                          class="row gx-2 gy-2 align-items-center mb-3 search-inline">
                      <div class="col-auto">
                        <label for="searchType" class="visually-hidden">검색항목</label>
                        <select id="searchType" name="searchType" class="form-select">
                          <option value="all"        ${empty param.searchType || param.searchType=='all' ? 'selected' : ''}>전체</option>
                          <option value="clientName" ${param.searchType=='clientName' ? 'selected' : ''}>거래처명</option>
                          <option value="bossName"   ${param.searchType=='bossName' ? 'selected' : ''}>대표자명</option>
                        </select>
                      </div>

                      <div class="col-auto">
                        <label for="status" class="visually-hidden">상태</label>
                        <select id="status" name="status" class="form-select">
                          <option value=""  ${empty param.status ? 'selected' : ''}>상태</option>
                          <option value="0" ${param.status=='0' ? 'selected' : ''}>영업중</option>
                          <option value="1" ${param.status=='1' ? 'selected' : ''}>휴업중</option>
                          <option value="2" ${param.status=='2' ? 'selected' : ''}>폐업</option>
                        </select>
                      </div>

                      <div class="col kw-col flex-grow-1">
                        <label for="searchKeyword" class="visually-hidden">검색어</label>
                        <input type="text" id="searchKeyword" name="searchKeyword"
                               value="${param.searchKeyword}" class="form-control" placeholder="검색어를 입력하세요">
                      </div>

                      <div class="col-auto d-flex gap-2">
                        <button type="submit" class="btn btn-brown">검색</button>
                        <a href="${pageContext.request.contextPath}/client/clientList" class="btn btn-brown-outline">초기화</a>
                      </div>
                    </form>

                    <!-- 표 -->
                    <div class="table-responsive pushed-down">
                      <table class="table table-bordered table-hover text-center mb-0">
                          <thead class="table-secondary">
                              <tr>
                                  <th>유형</th>
                                  <th>거래처명</th>
                                  <th>상태</th>
                                  <th>대표자명</th>
                                  <th>전화번호</th>
                                  <th>등록일</th>
                              </tr>
                          </thead>
                          <tbody>
                              <c:forEach var="clientDto" items="${clientDtoList}">
                                  <tr class="clickable-row"
                                      onclick="location.href='${pageContext.request.contextPath}/client/clientDetail?client_code=${clientDto.client_code}'">
                                      <td>${clientDto.client_type_br}</td>
                                      <td>${clientDto.client_name}</td>
                                      <td>
                                          <c:choose>
                                              <c:when test="${clientDto.client_status == 0}">영업중</c:when>
                                              <c:when test="${clientDto.client_status == 1}">휴업중</c:when>
                                              <c:when test="${clientDto.client_status == 2}">폐업</c:when>
                                              <c:otherwise>기타</c:otherwise>
                                          </c:choose>
                                      </td>
                                      <td>${clientDto.boss_name}</td>
                                      <td>${clientDto.client_tel}</td>
                                      <td>${clientDto.clientRegDateFormatted}</td>
                                  </tr>
                              </c:forEach>

                              <c:if test="${empty clientDtoList}">
                                  <tr><td colspan="6" class="text-muted">등록된 거래처가 없습니다.</td></tr>
                              </c:if>
                          </tbody>
                      </table>
                    </div>

                    <%-- ✅ 페이징: c:url로 안전하게 생성 (HTML 주석에 <c:url> 쓰지 말 것) --%>
                    <nav class="mt-4" aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:if test="${page.startPage > page.pageBlock}">
                                <c:url var="prevUrl" value="/client/clientList">
                                    <c:param name="currentPage" value="${page.startPage - page.pageBlock}" />
                                    <c:param name="searchType" value="${param.searchType}" />
                                    <c:param name="searchKeyword" value="${param.searchKeyword}" />
                                    <c:param name="status" value="${param.status}" />
                                </c:url>
                                <li class="page-item"><a class="page-link" href="${prevUrl}">이전</a></li>
                            </c:if>

                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <c:url var="numUrl" value="/client/clientList">
                                    <c:param name="currentPage" value="${i}" />
                                    <c:param name="searchType" value="${param.searchType}" />
                                    <c:param name="searchKeyword" value="${param.searchKeyword}" />
                                    <c:param name="status" value="${param.status}" />
                                </c:url>
                                <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${numUrl}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${page.endPage < page.totalPage}">
                                <c:url var="nextUrl" value="/client/clientList">
                                    <c:param name="currentPage" value="${page.startPage + page.pageBlock}" />
                                    <c:param name="searchType" value="${param.searchType}" />
                                    <c:param name="searchKeyword" value="${param.searchKeyword}" />
                                    <c:param name="status" value="${param.status}" />
                                </c:url>
                                <li class="page-item"><a class="page-link" href="${nextUrl}">다음</a></li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </main>

            <%@ include file="../footer.jsp" %>
        </div>
    </div>
</body>
</html>
