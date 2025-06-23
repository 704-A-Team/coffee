<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

    // listEmpAjaxForm3.jsp 에서 수정전
    function getEmpListUpdateTest() {
    	alert("getEmpListUpdateTest Run 폼에서 수정전");
    	// Group 번호 가져오기
    	var arr = new Array();
    	var item;
    	
    	<c:forEach items="${listEmp}" var = "item" >
    		arr.push({
    					empno:"${item.empno}",
    					ename:"${item.ename}",
    					deptno:"${item.deptno}"
    			
    		});
    	</c:forEach>
    	
    	for (var i = 0; i <arr.length; ){
    		alert("arr.length-> " + i +" : " + arr[i].empno + " arr.ename-> " + i + " : " + arr[i].ename);
    		i++;
    		if(i>2) return;
    	}
    }
    	
 	// listEmpAjaxForm3.jsp 에서 수정후
 	// 화면에 있는 모든 사원 정보(input 값들)를 읽어서 → JSON 배열로 묶고 → Ajax로 서버에 보내는 코드
 	function getEmpListUpdate(){
 		alert("getEmpListUpdate RUN");
 		let empList = [];
 		const inputs = document.querySelectorAll('input[name="empno"],input[name="ename"],input[name="deptno"]');
 		for (let i = 0; i<inputs.length; i += 3) {    // i = 0 , 3 , 6,
 			// 3가지를 1묶음으로 보겠다
 			// JSP에서 input의 순서가 deptno, empno, ename으로 작성되어 순서대로 가져옴
 			// -uerySelectorAll 순서대로 가져오는 것이 아니다
 			const empno = inputs[i+1].value;
 			const ename = inputs[i+2].value;
 			// const deptno = inputs[i+3].value;  (x)
 			const deptno = inputs[i+0].value;
 			// 불러온 값들을 JSON 객체 형태로 만든다
 			const empItem = {
 					"empno":empno,
 					"ename":ename,
 					"deptno":deptno
 					};
 			//alert("ename->"+ename);
 			// JSON 객체를 배열 안에 넣어둔다.
 			empList.push(empItem);
 			if(i>10) break;  // 2명만 보겠다
 		}
 		alert("JSON.stringify(empList)->" + JSON.stringify(empList));
 		
 		if(empList.length > 0) {
 			
 			$.ajax({
 				url : "empListUpdate",
 				contentType : "application/json", //  백엔드에서 @RequestBody로 받을 준비해야 함!
 				data : JSON.stringify(empList),	  // JSON 객체를 불어화서 stringify() 함수 안에 배열
 				method : "POST",
 				dataType : "json",
 				success : function(response) {
 					console.log(response.updateResult);
 				}
 			});
 			alert("Ajax empListUpate 수정");
 		}
 	}


</script>
</head>
<body>
	<h2>회원 정보3</h2>
	<table  id="empList">
		<tr><th>번호</th><th>사번</th><th>이름</th><th>업무</th><th>부서</th></tr>
	 	<c:forEach var="emp" items="${listEmp}" varStatus="status">
			<tr id="empListRow"><td>emp${status.index}</td>
		     	
			    <td>
			        <input type="hidden" class="deptno"  id="deptno" name="deptno" value="${emp.deptno }">
			        <input type="text"   class="empno"   id="empno"  name="empno"  value="${emp.empno }">${emp.empno }</td>
			    <td><input type="text"   class="ename"   id="ename"  name="ename"  value="${emp.ename }">${emp.ename }</td>
				<td>${emp.job }</td><td>${emp.deptno } 
				</td>
			</tr>    
	     
	    </c:forEach>
	</table>

   RestController LISTVO3: <input  type="button" id="btn_Dept3"
                                   value="empLISTTest 전송 "  
                                   onclick="getEmpListUpdateTest()"><p>
   RestController LISTVO3: <input  type="button" id="btn_Dept3"
                                   value="empLIST 전송 "  
                                   onclick="getEmpListUpdate()"><p>
                                   

	
	<h1>The End </h1>
</body>
</html>