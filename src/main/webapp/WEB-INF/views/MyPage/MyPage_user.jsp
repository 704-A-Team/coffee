<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 마이페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
  :root{
    --main-brown:#6f4e37;
    --dark-brown:#4e342e;
    --soft-brown:#bfa08e;
    --danger-red:#a94442;
  }
  body{
    background:#f9f5f1;
    font-family:'Segoe UI',sans-serif;
  }

  /* 본문 폭/중앙 */
  .form-container{ width:100%; max-width:980px; margin:0 auto; }
  .form-section-title{
    border-left:5px solid var(--main-brown);
    padding-left:12px;
    margin:6px 0 24px;
    font-weight:700;
    font-size:1.8rem;
    color:var(--dark-brown);
  }
  .card-detail{
    background:#fff;
    border:1px solid #e0e0e0;
    box-shadow:0 4px 6px rgba(0,0,0,.05);
  }
  .card-header{
    background:linear-gradient(180deg,#fff,#f4ebe3);
    border-bottom:1px solid #eadfd6;
  }
  .card-header .title{ color:var(--main-brown); font-weight:700; margin:0; }
  .col-form-label{ font-weight:600; color:#555; }
  .form-control[disabled]{ background:#fff; border-color:#e6e0db; color:#333; }

  /* 프로젝트 표준 버튼(목록 스타일에 맞춤) */
  .btn-brown-outline{
    border:1px solid var(--main-brown) !important;
    color:var(--main-brown) !important;
    background-color:#fff !important;
  }
  .btn-brown-outline:hover{
    background-color:#ccc !important;
    color:#333 !important;
    border-color:#ccc !important;
  }

  /* 메인 찌그러짐 방지 */
  .content-row{ min-height:0; }
  .content-main{ min-width:0; }
</style>
</head>
<body class="d-flex flex-column min-vh-100">
  <%@ include file="../header.jsp" %>

  <!-- 사이드바 + 오른쪽 컬럼 -->
  <div class="d-flex flex-grow-1 content-row">
    <%@ include file="../sidebar.jsp" %>
	
    <!-- 본문과 푸터를 같은 컬럼에 배치 -->
    <div class="d-flex flex-column flex-grow-1">
      <main class="flex-grow-1 p-4 content-main">
        <div class="form-container">
          <div class="form-section-title">내 정보</div>

          <div class="card card-detail shadow-sm">
            <div class="card-header">
              <h2 class="h5 title">사원 기본 정보</h2>
            </div>

            <div class="card-body">
              <!-- 사원 번호 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사원 번호</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.emp_code}" disabled>
                </div>
              </div>

              <!-- 사원 이름 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사원 이름</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.emp_name}" disabled>
                </div>
              </div>

              <!-- 전화번호 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">전화번호</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.emp_tel}" disabled>
                </div>
              </div>

		<!-- 소속 부서 -->
				<div class="row mb-3">
				  <label class="col-sm-2 col-form-label">소속 부서</label>
				  <div class="col-sm-8">
				    <input type="text" class="form-control" disabled
				      value="${not empty empDto.dept_code
				               ? empDto.dept_code
				               : (empDto.emp_dept_code == 1000 ? '영업부'
				                 : empDto.emp_dept_code == 1001 ? '생산관리팀'
				                 : empDto.emp_dept_code == 1002 ? '재고팀'
				                 : empDto.emp_dept_code == 1003 ? '인사팀'
				                 : empDto.emp_dept_code == 1004 ? '구매팀'
				                 : empDto.emp_dept_code == 1005 ? '판매팀'
				                 : '경영팀')}">
				  </div>
				</div>
				
				<!-- 직급 -->
				<div class="row mb-3">
				  <label class="col-sm-2 col-form-label">직급</label>
				  <div class="col-sm-8">
				    <input type="text" class="form-control" disabled
				      value="${not empty empDto.emp_grade_detail
				               ? empDto.emp_grade_detail
				               : (empDto.emp_grade == 0 ? '사원'
				                 : empDto.emp_grade == 1 ? '과장'
				                 : empDto.emp_grade == 2 ? '부장'
				                 : empDto.emp_grade == 3 ? '이사'
				                 : empDto.emp_grade == 4 ? '사장'
				                 : '미정')}">
				  </div>
				</div>

              <!-- 급여 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">급여</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.emp_sal}" disabled>
                </div>
              </div>

              <!-- 이메일 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">이메일</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.emp_email}" disabled>
                </div>
              </div>

              <!-- 생년월일 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">생년월일</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.empBirthFormatted}" disabled>
                </div>
              </div>

              <!-- 입사일 -->
              <div class="row mb-3">
                <label class="col-sm-2 col-form-label">입사일</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" value="${empDto.empIpsaDateFormatted}" disabled>
                </div>
              </div>
            </div><!-- /.card-body -->

            <!-- ★ 카드 안쪽 하단: 바로 붙는 버튼들 -->
            <div class="card-footer bg-transparent border-0 py-2">
              <div class="d-flex justify-content-end gap-3">
                <!-- 개인정보 변경: '수정'과 같은 톤 -->
                <a href="${pageContext.request.contextPath}/MyPage/changeInformationUser"
                   class="btn btn-primary">개인정보 변경</a>
                <!-- 비밀번호 변경: '목록'과 같은 톤 -->
                <a href="${pageContext.request.contextPath}/MyPage/changePassword"
                   class="btn btn-brown-outline">비밀번호 변경</a>
              </div>
            </div>
            <!-- /card-footer -->
          </div><!-- /.card -->
        </div><!-- /.form-container -->
      </main>

      <%@ include file="../footer.jsp" %>
    </div>
  </div>
</body>
</html>
