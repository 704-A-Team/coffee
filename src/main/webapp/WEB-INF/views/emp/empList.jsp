<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>.clickable-row{cursor:pointer;}</style>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="../header.jsp" %>

<div class="d-flex flex-grow-1">
  <%@ include file="../sidebar.jsp" %>

  <div class="d-flex flex-column flex-grow-1">
    <main class="flex-grow-1 p-4">
      <div class="container p-0">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h5 class="m-0">사원 목록</h5>
          <a href="${pageContext.request.contextPath}/emp/empInForm" class="btn btn-success">사원 등록</a>
        </div>

        <!-- 검색바 -->
        <form action="${pageContext.request.contextPath}/emp/empList" method="get"
              class="row gx-2 gy-2 align-items-center mb-3">

          <!-- 전체/사원번호 -->
          <div class="col-auto">
            <label for="searchType" class="visually-hidden">검색항목</label>
            <select id="searchType" name="searchType" class="form-select">
              <option value="all"     ${empty param.searchType || param.searchType=='all' ? 'selected' : ''}>전체</option>
              <option value="empCode" ${param.searchType=='empCode' ? 'selected' : ''}>사원번호</option>
            </select>
          </div>

          <!-- 부서 -->
          <div class="col-auto">
            <label for="deptName" class="visually-hidden">부서</label>
            <select id="deptName" name="deptName" class="form-select">
              <option value=""        ${empty param.deptName ? 'selected' : ''}>부서(전체)</option>
              <option value="영업팀"   ${param.deptName=='영업팀' ? 'selected' : ''}>영업팀</option>
              <option value="생산관리팀" ${param.deptName=='생산관리팀' ? 'selected' : ''}>생산관리팀</option>
              <option value="재고팀"   ${param.deptName=='재고팀' ? 'selected' : ''}>재고팀</option>
              <option value="인사팀"   ${param.deptName=='인사팀' ? 'selected' : ''}>인사팀</option>
              <option value="구매팀"   ${param.deptName=='구매팀' ? 'selected' : ''}>구매팀</option>
              <option value="판매팀"   ${param.deptName=='판매팀' ? 'selected' : ''}>판매팀</option>
            </select>
          </div>

          <!-- 직급 -->
          <div class="col-auto">
            <label for="gradeName" class="visually-hidden">직급</label>
            <select id="gradeName" name="gradeName" class="form-select">
              <option value=""    ${empty param.gradeName ? 'selected' : ''}>직급(전체)</option>
              <option value="사원" ${param.gradeName=='사원' ? 'selected' : ''}>사원</option>
              <option value="과장" ${param.gradeName=='과장' ? 'selected' : ''}>과장</option>
              <option value="부장" ${param.gradeName=='부장' ? 'selected' : ''}>부장</option>
            </select>
          </div>

           <div class="col kw-col flex-grow-1">
            <label for="searchKeyword" class="visually-hidden">검색어</label>
            <input type="text" id="searchKeyword" name="searchKeyword"
                   value="${fn:escapeXml(param.searchKeyword)}" class="form-control" placeholder="검색어를 입력하세요">
          </div>
          
          
        <div class="col-auto d-flex gap-2">
            <button type="submit" class="btn btn-primary">검색</button>
            <a href="${pageContext.request.contextPath}/emp/empList" class="btn btn-outline-secondary">초기화</a>
          </div>
        </form>

        <!-- 목록 -->
        <table class="table table-bordered table-hover text-center mb-0">
          <thead class="table-secondary">
            <tr>
              <th>사원 번호</th>
              <th>이름</th>
              <th>소속 부서</th>
              <th>직급</th>          
            </tr>
          </thead>
          <tbody>
            <c:forEach var="empDto" items="${empDtoList}">
              <tr class="clickable-row"
                  onclick="location.href='${pageContext.request.contextPath}/emp/empDetail?emp_code=${empDto.emp_code}'">
                <td>${empDto.emp_code}</td>
                <td>${empDto.emp_name}</td>
                <td><c:out value="${empty empDto.dept_code ? empDto.emp_dept_code : empDto.dept_code}"/></td>
                <td><c:out value="${empty empDto.emp_grade_detail ? empDto.emp_grade : empDto.emp_grade_detail}"/></td>
              </tr>
            </c:forEach>
            <c:if test="${empty empDtoList}">
              <tr><td colspan="5" class="text-muted">등록된 사원이 없습니다.</td></tr>
            </c:if>
          </tbody>
        </table>

        <!-- 페이징 (검색 파라미터 유지) -->
        <nav class="mt-4" aria-label="Page navigation">
          <ul class="pagination justify-content-center">
            <c:if test="${page.startPage > page.pageBlock}">
              <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage - page.pageBlock}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}&deptName=${param.deptName}&gradeName=${param.gradeName}">이전</a>
              </li>
            </c:if>

            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
              <li class="page-item ${i == page.currentPage ? 'active' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/emp/empList?currentPage=${i}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}&deptName=${param.deptName}&gradeName=${param.gradeName}">${i}</a>
              </li>
            </c:forEach>

            <c:if test="${page.endPage < page.totalPage}">
              <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/emp/empList?currentPage=${page.startPage + page.pageBlock}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}&deptName=${param.deptName}&gradeName=${param.gradeName}">다음</a>
              </li>
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
