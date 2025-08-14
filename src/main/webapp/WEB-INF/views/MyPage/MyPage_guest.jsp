<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .list-item-row {
            transition: background-color 0.3s ease;
            cursor: pointer;
        }
        .list-item-row:hover {
            background-color: #e2f3ff;
        }
        .data-list-wrapper a:nth-of-type(odd) .list-item-row {
            background-color: #f8f9fa;
        }
        .data-list-wrapper a:nth-of-type(even) .list-item-row {
            background-color: #e9ecef;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="../header.jsp" %>

    <!-- ===== 사이드바 + 메인 (가로 레이아웃) ===== -->
    <div class="d-flex flex-grow-1" style="min-height:0;">
        <%@ include file="../sidebar.jsp" %>

        <!-- ===== 메인 콘텐츠 시작 ===== -->
        <main class="flex-grow-1 p-4" style="background:#f9f5f1; min-width:0;">

            <c:url var="defaultAvatar" value="/images/default-avatar.png"/>

            <!-- 상단: 프로필 요약 -->
            <section class="mb-4">
                <div class="card shadow-sm border-0">
                    <div class="card-body d-flex align-items-center gap-3">
                        <img
                            src="${empty member.profileImageUrl ? defaultAvatar : member.profileImageUrl}"
                            alt="프로필" class="rounded-circle"
                            style="width:64px;height:64px;object-fit:cover;">
                        <div class="flex-grow-1">
                            <div class="d-flex flex-wrap align-items-center gap-2">
                                <h5 class="mb-0"><c:out value="${member.name}" default="홍길동"/></h5>
                                <span class="badge text-bg-secondary">
                                    <c:out value="${member.department}" default="영업팀"/> /
                                    <c:out value="${member.grade}" default="대리"/>
                                </span>
                            </div>
                            <div class="text-muted small mt-1">
                                사번: <c:out value="${member.empCode}" default="E20250001"/> ·
                                최근 로그인: <c:out value="${member.lastLogin}" default="2025-08-14 09:12"/>
                            </div>
                        </div>
                        <div class="d-flex gap-2">
                            <a href="${pageContext.request.contextPath}/mypage/edit" class="btn btn-brown-outline btn-sm">내 정보 수정</a>
                            <a href="${pageContext.request.contextPath}/auth/password" class="btn btn-brown-outline btn-sm">비밀번호 변경</a>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 중단: 빠른 실행 -->
            <section class="mb-4">
                <h6 class="mb-3">빠른 실행</h6>
                <div class="row g-3">
                    <div class="col-12 col-md-6 col-xl-3">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start">
                                    <div>
                                        <div class="fw-semibold">내 발주 현황</div>
                                        <div class="text-muted small">대기/진행/완료</div>
                                    </div>
                                    <span class="badge text-bg-light">
                                        <c:out value="${mypage.orderWaiting}" default="2"/> /
                                        <c:out value="${mypage.orderInProgress}" default="3"/> /
                                        <c:out value="${mypage.orderDone}" default="5"/>
                                    </span>
                                </div>
                                <a href="${pageContext.request.contextPath}/purchase/list?me=true" class="btn btn-brown-outline btn-sm mt-3 w-100">바로가기</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-3">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body">
                                <div class="fw-semibold">승인 요청</div>
                                <div class="text-muted small">내가 승인해야 할 항목</div>
                                <div class="display-6 mt-2">
                                    <c:out value="${mypage.approvalCount}" default="1"/>
                                </div>
                                <a href="${pageContext.request.contextPath}/approval/inbox" class="btn btn-brown-outline btn-sm mt-3 w-100">승인함 열기</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-3">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body">
                                <div class="fw-semibold">재고 체크</div>
                                <div class="text-muted small">임계치 하회 품목</div>
                                <div class="display-6 mt-2">
                                    <c:out value="${mypage.lowStockCount}" default="4"/>
                                </div>
                                <a href="${pageContext.request.contextPath}/inventory/alerts" class="btn btn-brown-outline btn-sm mt-3 w-100">알람 보기</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-3">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body">
                                <div class="fw-semibold">연차/근태</div>
                                <div class="text-muted small">남은 연차</div>
                                <div class="display-6 mt-2">
                                    <c:out value="${mypage.remainingLeaves}" default="7"/>일
                                </div>
                                <a href="${pageContext.request.contextPath}/attendance/mine" class="btn btn-brown-outline btn-sm mt-3 w-100">근태 관리</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 하단 2열: 최근 활동 / 내 알림 -->
            <section class="mb-4">
                <div class="row g-3">
                    <!-- 최근 활동 -->
                    <div class="col-12 col-xl-7">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-white border-0">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h6 class="mb-0">최근 활동</h6>
                                    <a href="${pageContext.request.contextPath}/activity/mine" class="btn btn-brown-outline btn-sm">전체 보기</a>
                                </div>
                            </div>
                            <div class="card-body p-0">
                                <c:choose>
                                    <c:when test="${not empty recentActions}">
                                        <div class="list-group list-group-flush data-list-wrapper">
                                            <c:forEach var="act" items="${recentActions}">
                                                <a class="text-decoration-none" href="<c:out value='${act.link}'/>">
                                                    <div class="list-item-row px-3 py-2 d-flex justify-content-between">
                                                        <div class="me-3">
                                                            <div class="fw-semibold"><c:out value="${act.title}"/></div>
                                                            <div class="text-muted small"><c:out value="${act.subtitle}"/></div>
                                                        </div>
                                                        <div class="text-muted small">
                                                            <c:out value="${act.timeAgo}" default="방금 전"/>
                                                        </div>
                                                    </div>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="p-4 text-center text-muted">최근 활동이 없습니다.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <!-- 내 알림 -->
                    <div class="col-12 col-xl-5">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-white border-0">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h6 class="mb-0">내 알림</h6>
                                    <div class="d-flex gap-2">
                                        <a href="${pageContext.request.contextPath}/notifications" class="btn btn-brown-outline btn-sm">전체</a>
                                        <a href="${pageContext.request.contextPath}/notifications/read-all" class="btn btn-brown-outline btn-sm">모두 읽음</a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body p-0">
                                <c:choose>
                                    <c:when test="${not empty notifications}">
                                        <ul class="list-group list-group-flush">
                                            <c:forEach var="n" items="${notifications}">
                                                <li class="list-group-item d-flex justify-content-between align-items-start">
                                                    <div class="me-2">
                                                        <div class="fw-semibold"><c:out value="${n.title}"/></div>
                                                        <div class="text-muted small"><c:out value="${n.body}"/></div>
                                                    </div>
                                                    <div class="text-muted small ms-2" style="white-space:nowrap;">
                                                        <c:out value="${n.timeAgo}" default="1시간 전"/>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="p-4 text-center text-muted">새 알림이 없습니다.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 보안 & 환경설정 -->
            <section class="mb-5">
                <div class="card shadow-sm border-0">
                    <div class="card-body">
                        <div class="row g-3">
                            <div class="col-12 col-lg-6">
                                <div class="p-3 border rounded-3 h-100 bg-white">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h6 class="mb-0">보안</h6>
                                        <a href="${pageContext.request.contextPath}/auth/sessions" class="btn btn-brown-outline btn-sm">세션 관리</a>
                                    </div>
                                    <ul class="mt-3 mb-0 small text-muted">
                                        <li>마지막 비밀번호 변경: <c:out value="${security.lastPwChange}" default="미변경"/></li>
                                        <li>2단계 인증: <c:out value="${security.mfaEnabled ? '사용중' : '미사용'}" default="미사용"/></li>
                                        <li>활성 세션 수: <c:out value="${security.activeSessions}" default="1"/></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-12 col-lg-6">
                                <div class="p-3 border rounded-3 h-100 bg-white">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h6 class="mb-0">환경설정</h6>
                                        <a href="${pageContext.request.contextPath}/settings" class="btn btn-brown-outline btn-sm">설정 열기</a>
                                    </div>
                                    <div class="mt-3 small text-muted">
                                        <div>알림 수신: <c:out value="${settings.notificationOn ? 'ON' : 'OFF'}" default="ON"/></div>
                                        <div>테마: <c:out value="${settings.theme}" default="라이트"/></div>
                                        <div>언어: <c:out value="${settings.lang}" default="한국어"/></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

        </main>
        <!-- ===== 메인 콘텐츠 끝 ===== -->
    </div>
    <!-- ===== 사이드바+메인 끝 ===== -->

    <%@ include file="../footer.jsp" %>

</body>
</html>
