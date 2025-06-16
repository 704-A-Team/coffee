package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {
	private final EmpService es;
	
	@RequestMapping(value = "/listEmpStart")
	public String listEmpStart(Emp emp, Model model) {
		System.out.println("EmpController listEmpStart start");
		// 23
		int totalEmp = es.totalEmp();
		String currentPage = "1";
		// Paging 작업
		Paging page = new Paging(totalEmp, currentPage);
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart());		// 시작시 1
		emp.setEnd(page.getEnd());			// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpStart listEmp.size()-> " + listEmp.size());
	
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp",listEmp);
		model.addAttribute("page",page);
		return "list";
	}
	
	@RequestMapping(value = "/listEmp")
	public String listEmp(Emp emp, Model model) {
		System.out.println("EmpController listEmp start");
		// 23
		int totalEmp = es.totalEmp();

		// Paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart());		// 시작시 1
		emp.setEnd(page.getEnd());			// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmp listEmp.size()-> " + listEmp.size());
	
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp",listEmp);
		model.addAttribute("page",page);
		return "list";
	}
	
	// 이름을 클릭하면 empno를 가지고 FORM 화면 보여주기
	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp, Model model) {
		System.out.println("EmpController detailEmp Start");
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//
//		2. EmpDao   detailEmp method 선언 
////		                    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
		
		//                      (emp)를 넣어도 실행 된다
		Emp emp1 = es.detailEmp(emp.getEmpno());
		model.addAttribute("emp",emp1);
		return "detailEmp";
	}
	
	// detailEmp 화면에서 수정 버튼 -->> empno 가지고 가기
	// input 태그들로 바꾸려 detailEmp() 다시 쓰기
	@GetMapping("/updateFormEmp")
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController updateFormEmp Start");
		Emp emp = es.detailEmp(emp1.getEmpno());
		System.out.println("EmpController updateFormEmp emp->"+emp);
		// hiredate=1981-02-20 00:00:00
		// 문제 
		// 1. DTO  String hiredate 선언
		// 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
		// 3.해결책  : 년월일만 짤라 넣어 주어야 함
		String hiredate="";
		if(emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
			
		}
		System.out.println("hiredate->"+hiredate);
		
		model.addAttribute("emp",emp);
		return "updateFormEmp";
	}
	
	@PostMapping("/updateEmp")
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp Start");
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   	2. EmpDao updateEmp method 선언
////                              		mapper ID   ,    Parameter
//   	updateCount = session.update("tkEmpUpdate"	,		emp);
		
		int updateCount = es.updateEmp(emp);
		model.addAttribute("uptCnt",updateCount); 		// Test Controller Data 전달
		model.addAttribute("kk3", "Message Test)"); 	// Test Controller Data 전달
		
			// listpage , page관련은 emp에 있으니까 listEmp로 이동하여도 가능하다  -->> X 아님
			// redirect로 listEmp 가면 그 메소드를 다시 실행하면서 초기 페이지인 1페이지로 이동한다
			// 즉, 3페이지에서 수정하여 확인 버튼 누르면 1페이지로 이동
		return "redirect:listEmp";
			// controller 내의 listEmp를 찾으러 간다
			// redirect Vs forward
		//return "forward:listEmp";
	}
	
	@RequestMapping("/writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("EmpController writeFormEmp Start");
		// 관리자 사번 만 Get
		// 1. service -> listManager
				// 2. Dao     -> listManager
				// 3. mapper  -> tkSelectManager
		List<Emp> empList = es.listManager();
		model.addAttribute("empMngList", empList);
		
		// 부서(코드,부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp deptList.size()->"+deptList.size());
		
		return "writeFormEmp"; 
	}
	
}
