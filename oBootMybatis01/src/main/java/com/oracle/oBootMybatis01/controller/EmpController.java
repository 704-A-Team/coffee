package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.DeptVO;
import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.dto.EmpDept;
import com.oracle.oBootMybatis01.dto.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.MemberJpaService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {
	private final EmpService es;
	
	// 메일을 쓰기 위함
	// MIME(마임 / 영어: Multipurpose Internet Mail Extensions)는 전자 우편을 위한 인터넷 표준 포맷
	// 			  다목적의 인터넷 메일 확장
	private final JavaMailSender mailSender;
	
	// Member 조인 , JPA 사용 가능 여부 알아보기
	private final MemberJpaService js;
	
	
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
	
	/////////////////////////////////////////////////////////  6/18
	@GetMapping(value = "listEmpDept")
	public String listEmpDept (Model model) {
		System.out.println("EmpController listEmpDept Start");
		// 활동 : Emp  / DEPT 테이블 조인  -->> EmpDept DTO
		// Service ,DAO -> listEmpDept
		// Mapper만 ->EmpDept.xml(tkListEmpDept)   맵퍼 분리
		
		List<EmpDept> listEmpDept = es.listEmpDept();
		
		model.addAttribute("listEmpDept", listEmpDept);
	
		return "listEmpDept";
	}
	
	@RequestMapping("/mailTransport")
	public String mailTransport(HttpServletRequest request, Model model)  {
		System.out.println("EmpController mailTransport Sending");
		String tomail = "nogangsss@naver.com";    // 받는 사람 이메일
		System.out.println(tomail);
		String setfrom = "nogangsss@gmail.com";	  // 이메일 주소 바꿔도 야물 세팅으로 간다
		String title = "mailTransport 입니다";		  // 제목 (없으면 스팸메일됨)
		try {
			// Mime 전자우편 Internet 표준 Format
			MimeMessage message = mailSender.createMimeMessage();
				// 빈 편지지 준비
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				// 편지지 객체, multipart 허용(첨부파일 허융), 인코딩 방식
			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail);	// 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			String tempPassword = (int)(Math.random() *999999) + 1 + "";
			messageHelper.setText("임시 비밀번호입니다 : " + tempPassword);	 // 메일 내용
			System.out.println("임시 비밀번호입니다 : " + tempPassword);
				// 편지지 작성
			mailSender.send(message);       // 편지지 발송
			model.addAttribute("check", 1); // 정상 전달
			// DB Logic
			
		} catch (Exception e) {
			System.out.println("mailTransport e.getMessage()"+e.getMessage());
			model.addAttribute("check", 2); // 메일 정달 실패
		}
		
		
		return "maliResult";
	}
	
	
	/////////////////////////////////////////////////////////////////////
	
	// Procedure Test 입력화면
	@RequestMapping("/writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("EmpController writeDeptIn Start");
		return "writeDept3";
	}
	
	@PostMapping("/writeDept")
	public String writeDept(DeptVO deptVO , Model model) {
		es.insertDept(deptVO);
		if( deptVO == null ) {
			System.out.println("deptVO Null");
		} else {
			System.out.println("deptVO.getOdeptno()->"+deptVO.getOdeptno());
			System.out.println("deptVO.getOdname() ->"+deptVO.getOdname());
			System.out.println("deptVO.getOloc()   ->"+deptVO.getOloc());
			model.addAttribute("msg", "정상 입력 되었습니다 ^^");
			model.addAttribute("dept",deptVO);
		}
		return "writeDept3";
	}
	
	///////////////////////////////////////////////////////////////////
	
	@RequestMapping("/writeDeptCursor")
	public String writeDeptCursor(Model model)  {
		System.out.println("EmpController writeDeptCursor Start");
		// Map 적용 ( 보통 dto 지만)
		// 부서범위 조회  3:23
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sDeptno", 10);
		map.put("eDeptno", 55);
		
		es.selListDept(map);
		List<Dept> deptLists = (List<Dept>) map.get("dept");
		for(Dept dept : deptLists) {
			System.out.println("writeDeptCursor dept->"+dept);
		}
		System.out.println("deptLists.size()->"+deptLists.size());
		model.addAttribute("deptList", deptLists);
		
		return "writeDeptCursor";
	}
	
	///////////////////////////////////////////////////////////////
	
	// Mybatis랑 JPA 섞어써도 된다 -> MemberJpaContoller에서 시작
	
	//////////////////////////////////////////////////////////////
	
	// 6/19 12:30 interCeptor  	
	
	// interCeptor 시작화면
	@RequestMapping("/interCeptorForm")
	public String interCeptorForm(Model model) {
		System.out.println("EmpController interCeptorForm Start");
		return "interCeptorForm";
	}
	
	// interCeptor 2
	@RequestMapping("/interCeptor")
	public String interCeptor(Member1 member1, Model model) {
		
		System.out.println("EmpController interCeptor Test START");
		System.out.println("EmpController interCeptor  id->"+member1.getId());
		// 존재 : 1, 비존재 : 0
		int memCnt = es.memCount(member1.getId());
		
		System.out.println("EmpController interCeptor  memCnt->"+memCnt);
		
		model.addAttribute("id", member1.getId());
		model.addAttribute("memCnt", memCnt);
		System.out.println("interCeptor Test End");
		// 3번 실행
		
		return "interCeptor"; // User 존재하면 User 이용 조회 Page
	}
	
	// SampleInterCeptor 내용을 받아 처리 Case 1
	@RequestMapping("/doMemberWrite")
	public String doMemberWrite(Model model , HttpServletRequest request ) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite 부터 하세요");
		model.addAttribute("id", ID);
		return "doMemberWrite";
	}
	
	// SampleInterCeptor 내용을 받아 처리 Case 2
	@RequestMapping("/doMemberList")
	public String doMemberList(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite Test Start ID->"+ID);
		Member1 member1 = null;
		// Member1 List Get Service
		// Service	--> listMem (EmpService es)
		// DAO 		--> listMem	(Member1DaoImpl)
		// Mapper 	--> "listMember1"(Member1)
		// Member1 모든 Row Get
		List<Member1> listMem = es.listMem();
		model.addAttribute("ID", ID);
		model.addAttribute("listMem", listMem);
		return "doMemberList"; // User 존재하면 User 이용 조회 Page
	}
	
	
}
