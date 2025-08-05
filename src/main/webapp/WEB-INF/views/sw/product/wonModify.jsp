<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품(원재료) 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-section-title {
            border-left: 4px solid #0d6efd;
            padding-left: 10px;
            margin-bottom: 20px;
            font-weight: 600;
            font-size: 2rem;
        }
        .image-thumb {
            width: 100px;
            height: auto;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
    </style>
    <script>
    document.addEventListener("DOMContentLoaded", () => {
        const fileInput = document.querySelector("#newFiles");
        const existingFilesContainer = document.querySelector("#existingFiles");
        const form = document.querySelector("form");

        existingFilesContainer.addEventListener("click", function (e) {
            if (e.target.classList.contains("remove-existing")) {
                const fileDiv = e.target.closest(".existing-file");
                fileDiv.remove();
            }
        });

        fileInput.addEventListener("change", function () {
            const existingCount = document.querySelectorAll("#existingFiles .existing-file").length;
            const newCount = this.files.length;
            if (existingCount + newCount > 3) {
                alert("기존 이미지와 새 이미지 합산 최대 3개까지만 가능합니다.");
                this.value = "";
            }
        });

        form.addEventListener("submit", function () {
            document.querySelectorAll("input[name='uploadFileNames']").forEach(e => e.remove());

            document.querySelectorAll(".existing-file").forEach(fileDiv => {
                const filename = fileDiv.getAttribute("data-filename");
                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "uploadFileNames";
                input.value = filename;
                form.appendChild(input);
                console.log("✅ 유지 이미지 추가됨:", filename);
            });
        });
    });
    </script>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="../../header.jsp" %>
<div class="d-flex flex-grow-1">
    <%@ include file="../../sidebar.jsp" %>
    <div class="d-flex flex-column flex-grow-1">
        <main class="flex-grow-1 p-4">
            <div class="container mt-3">
                <div class="form-section-title">제품(원재료) 수정</div>
                <form action="${pageContext.request.contextPath}/sw/wonProductModify" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="product_code" value="${wonProductDetail.product_code}" />

                    <div class="mb-3">
                        <label for="product_name" class="form-label">제품명</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" value="${wonProductDetail.product_name}" required>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">단위</label>
                            <select name="product_unit" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_unit == 0}">selected</c:if>>ea</option>
                                <option value="1" <c:if test="${wonProductDetail.product_unit == 1}">selected</c:if>>g</option>
                                <option value="2" <c:if test="${wonProductDetail.product_unit == 2}">selected</c:if>>ml</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">제품유형</label>
                            <select name="product_type" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_type == 0}">selected</c:if>>원재료</option>
                                <option value="1" <c:if test="${wonProductDetail.product_type == 1}">selected</c:if>>완제품</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">납품 유무</label>
                            <select name="product_isorder" class="form-select" required>
                                <option value="0" <c:if test="${wonProductDetail.product_isorder == 0}">selected</c:if>>납품</option>
                                <option value="1" <c:if test="${wonProductDetail.product_isorder == 1}">selected</c:if>>비납품</option>
                            </select>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">기본 중량 (g)</label>
                        <input type="number" class="form-control" name="product_weight" value="${wonProductDetail.product_weight}" required>
                    </div>

                    <div class="mb-4">
                        <label class="form-label">삭제 구분</label>
                        <select name="product_isdel" class="form-select">
                            <option value="0" <c:if test="${wonProductDetail.product_isdel == 0}">selected</c:if>>0</option>
                            <option value="1" <c:if test="${wonProductDetail.product_isdel == 1}">selected</c:if>>1</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">제품 이미지 ( 기존 + 새규 합쳐 최대 3개)</label>
                        <div id="existingFiles" class="mb-3 d-flex flex-wrap gap-3">
                            <c:forEach var="img" items="${wonProductDetail.wonImgList}" varStatus="status">
                                <div class="d-flex flex-column align-items-center existing-file" data-filename="${img.file_name}">
                                    <img src="${pageContext.request.contextPath}/upload/${img.file_name}" alt="img${status.index}" class="image-thumb mb-1">
                                    <button type="button" class="btn btn-sm btn-outline-danger remove-existing">삭제</button>
                                </div>
                            </c:forEach>
                        </div>
                        <input type="file" id="newFiles" name="files" multiple class="form-control" accept=".jpg,.jpeg,.png">
                        <div class="form-text text-danger mt-1">※ 기존 + 새 이미지 포함 최대 3개까지 등록 가능합니다.</div>
                    </div>
                    
                    <c:forEach var="img" items="${wonProductDetail.wonImgList}">
					  <input type="hidden" name="uploadFileNames" value="${img.file_name}" />
					</c:forEach>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/sw/wonProductDetail?product_code=${wonProductDetail.product_code}" class="btn btn-secondary">취소</a>
                    </div>
                </form>
            </div>
        </main>
        <%@ include file="../../footer.jsp" %>
    </div>
</div>
</body>
</html>
