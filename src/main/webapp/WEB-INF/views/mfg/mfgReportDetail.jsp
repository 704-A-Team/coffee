<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>생산보고서</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        @page {
            size: A4;
            margin: 20mm;
        }
        body {
            font-family: '맑은 고딕', sans-serif;
            margin: 0;
            padding: 0;
        }
        .a4page {
            width: 210mm;
            min-height: 297mm;
            margin: 10mm auto;
            padding: 10mm 15mm;
            background: white;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
            box-sizing: border-box;
        }
        .title {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        /* 테이블 전체 선 표시 */
        table {
            width: 100%;
            border-collapse: collapse;  /* 안쪽 선까지 보이도록 */
            margin-bottom: 15px;
        }
        th, td {
            border: 1px solid #666;  /* 안쪽 선 또렷하게 */
            padding: 6px 8px;
            font-size: 13px;
            text-align: center;
            vertical-align: middle;
        }
        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        .left-align { text-align: left; }

        .footer {
            text-align: right;
            margin-top: 20px;
        }

        @media print {
            body * { visibility: hidden; }
            .a4page, .a4page * { visibility: visible; }
            .a4page { position: absolute; left: 0; top: 0; margin: 0; padding: 0; box-shadow: none; }
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="../header.jsp" %>
    <div class="d-flex flex-grow-1">
        <%@ include file="../sidebar.jsp" %>
        <div class="d-flex flex-column flex-grow-1">
            <div class="a4page">
                <main class="flex-grow-1 p-4">
                    <div class="title">생산보고서</div>

                    <c:forEach var="report" items="${mfgRpDetailDTO}">
                        <!-- 상단 요약 -->
                        <table>
                            <tr>
                                <th>생산신청 코드</th>
                                <td>${report.mfg_code}</td>
                                <th>완제품 코드</th>
                                <td>${report.product_code}</td>
                                <th>완제품명</th>
                                <td class="left-align">${report.product_name}</td>
                            </tr>
                            <tr>
                                <th>담당자</th>
                                <td>${report.emp_name}</td>
                                <th>연락처</th>
                                <td>${report.emp_tel}</td>
                                <th>부서</th>
                                <td>${report.dept_name}</td>
                            </tr>
                            <tr>
                                <th>총투입중량(g)</th>
                                <td>${report.mfg_mat} g</td>
                                <th>총완제품중량(g)</th>
                                <td>${report.mfg_end} g</td>
                                <th>생산상태</th>
							    <td>
							        <c:choose>
							            <c:when test="${report.mfg_status == 5}">생산완료</c:when>
							            <c:when test="${report.mfg_status == 6}">검수완료</c:when>
							            <c:otherwise>-</c:otherwise>
							        </c:choose>
							    </td>
                            </tr>
                        </table>

                        <!-- 완제품 생산 정보 -->
                        <table>
                            <thead>
                                <tr>
                                    <th>생산완료개수</th>
                                    <th>생산완료일</th>
                                    <th>수율(%)</th>
                                    <th>편차(%)</th>
                                    <th>비고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${report.mfg_qty}</td>
                                    <td>${report.mfgEndDateStr}</td>
                                    <td>${report.yield}</td>
                                    <td>${report.pct}</td>
                                    <td class="left-align">${report.note}</td>
                                </tr>
                            </tbody>
                        </table>

                        <!-- 원재료 상세 -->
                        <table>
                            <thead>
                                <tr>
                                    <th>원재료코드</th>
                                    <th>원재료명</th>
                                    <th>단위</th>
                                    <th>투입수량</th>
                                    <th>폐기수량</th>
                                    <th>폐기율(%)</th>
                                    <th>비고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail" items="${report.rp_detail}">
                                    <tr>
                                        <td>${detail.product_won_code}</td>
                                        <td class="left-align">${detail.product_won_name}</td>
                                        <td>${detail.cd_contents}</td>
                                        <td>${detail.real_amount}</td>
                                        <td>${detail.trash_amount}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${detail.real_amount > 0}">
                                                    <c:set var="pct" value="${(detail.trash_amount * 100.0) / detail.real_amount}"/>
                                                    <fmt:formatNumber value="${pct}" type="number" maxFractionDigits="2"/>
                                                </c:when>
                                                <c:otherwise>0</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="left-align">${detail.trash_contents}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <hr/>
                    </c:forEach>

					<div class="footer">
					    <c:if test="${mfgRpDetailDTO[0].mfg_status == 5}">
					        <form action="/km/mfgRpUpdateForm" method="get" style="display:inline-block;">
					            <input type="hidden" name="mfg_code" value="${mfgRpDetailDTO[0].mfg_code}" />
					            <input type="hidden" name="product_code" value="${mfgRpDetailDTO[0].product_code}" />
					            <button type="submit" class="btn btn-primary btn-sm">수정</button>
					        </form>
					    </c:if>
					    <button type="button" class="btn btn-secondary btn-sm" onclick="location.href='${pageContext.request.contextPath}/km/mfgReportList'">목록</button>
					</div>
                </main>
            </div>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>
