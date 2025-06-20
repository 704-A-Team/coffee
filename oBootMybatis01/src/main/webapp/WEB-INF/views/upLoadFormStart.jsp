<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Upload Image : <img alt="Upload Image 1단계" src="${pageContext.request.contextPath }/upload/${savedName}">
	
	<form action="uploadForm" id="form1" method="post" enctype="multipart/form-data">
		파일 선택 : <input type="file" name="file1"> <p>
		제목 작성 : <input type="text" name="title"> <p>
		<input type="submit">
	</form>
</body>
</html>