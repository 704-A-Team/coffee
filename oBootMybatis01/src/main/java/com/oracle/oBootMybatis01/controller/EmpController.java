package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.validation.Valid;
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
	
	@PostMapping("/wirteEmp")
	public String wirteEmp(Emp emp, Model model)  {
		System.out.println("EmpController wirteEmp Start");
		
		// Service, Dao , Mapper명[insertEmp] 까지 -> insert
		// int insertResult 결과받기
		int insertRsult = es.insertEmp(emp);
		
		if(insertRsult > 0) return "redirect:listEmp";
		else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp";
		}
		
	}
	
	// Validation시 참조
	@RequestMapping("/writeFormEmp3")
	public String writeFormEmp3(Model model) {
		System.out.println("EmpController writeFormEmp3 Start");
		// 관리자 사번 만 Get
		// 1. service -> listManager
				// 2. Dao     -> listManager
				// 3. mapper  -> tkSelectManager
		List<Emp> empList = es.listManager();
		model.addAttribute("empMngList", empList);
		System.out.println("EmpController writeFormEmp3 empList.size()->"+empList.size());
		
		// 부서(코드,부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp3 deptList.size()->"+deptList.size());
		
		return "writeFormEmp3"; 
	}
	
	// 바인딩-> 요청에서 넘어온 값을 객체에 자동으로 채워주는 것
	// @ModelAttribute("emp")는 폼에서 넘어온 데이터를 자동으로 Emp 객체에 바인딩해서 Model에도 "emp"라는 이름으로 담아주는 어노테이션
	// model.addAttribute("emp", emp) 한 것처럼 모델에도 담아줘서 view에서 사용 가능
	//
	//                                                   6/17 2:15   / 2:25 전체 설명
	@PostMapping("/wirteEmp3")
	public String wirteEmp3(@ModelAttribute("emp") @Valid Emp emp, BindingResult result,  Model model)  {
		//                                         				   BindingResult는 @Valid 바로 다음에 위치해야 함!!
		System.out.println("EmpController wirteEmp3 Start");
		
		// Validation 오류시 Result
		if(result.hasErrors()) {
			// 에러가 있냐? 물어보고 BindingResult result에 오류 넣어둠
			System.out.println("EmpController wirteEmp3  hasErrors");
			model.addAttribute("msg", "BindingRsult 입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		
		// Service, Dao , Mapper명[insertEmp] 까지 -> insert
		// int insertResult 결과받기
		int insertRsult = es.insertEmp(emp);
		
		if(insertRsult > 0) return "redirect:listEmp";
		else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		
	}
	
	// 글쓰기 empno 사번 -> 중복확인 -> empno 파라메타 데려온다!
	@GetMapping("/confirm")
	public String confirm(Emp emp1, Model model) {
		System.out.println("EmpController confirm Start");
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("empno", emp1.getEmpno());
		if(emp != null) {
			System.out.println("EmpController confirm 중복");
			model.addAttribute("msg","중복된 사번입니다");
			//return "forward:writeFormEmp";
		} else {
			System.out.println("EmpController confirm 사용가능");
			model.addAttribute("msg","사용 가능한 사번입니다");
			//return "forward:writeFormEmp";
		}
		return "forward:writeFormEmp";
	}
	
	// detailEmp -> 삭제 -> empno 파라메타 가지고 온다
	@GetMapping("/deleteEmp")
	public String deleteEmp(Emp emp, Model model)  {
		System.out.println("EmpController deleteEmp Start");
		// Controller -->  deleteEmp    1.parameter : empno
		// name -> Service, dao , mapper
		int result = es.deleteEmp(emp.getEmpno());
		return "redirect:listEmp";
	}
	
	// search  3:00
	// Emp 에는 search , keyword 파라메타
	// 3:37분 페이징 설명
	@RequestMapping("/listSearch3")
	public String listSearch3(Emp emp, Model model)  {
		System.out.println("EmpController listSearch3 Start");
		System.out.println("EmpController listSearch3 emp-> "+emp);
		// 1번 작업 --> > EMP 검색해서 나온 개수(int)
		int condEmp = es.condTotalEmp(emp);
		System.out.println("EmpController listSearch3 condEmp-> " + condEmp);
		// 2번 작업 --> > paging 작업
		Paging page = new Paging(condEmp, emp.getCurrentPage());
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());
		System.out.println("EmpController listSearch3 emp->" + emp);
		System.out.println("EmpController listSearch3 page->" + page);
		// 3번 작업 --> > listSearchEmp
		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		
		model.addAttribute("totalEmp", condEmp);
		model.addAttribute("listEmp", listSearchEmp);
		model.addAttribute("page", page);
		
		return "list3";
	}
	
}
