package com.oracle.oBootBoard03.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard03.dto.DeptDTO;
import com.oracle.oBootBoard03.service.DeptService;
import com.oracle.oBootBoard03.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/dept")
public class DeptController {
	
	private final DeptService deptService;

	@GetMapping("/list1")
	public String mainPage1() {
		System.out.println("/dept/list start");
		
		return "dept/list";
	}
	
	@GetMapping("/deptInForm")
	public String deptInForm() {
		System.out.println("/dept/deptInForm start");
		
		return "dept/deptInForm";
	}
	
	@PostMapping("/saveDept")
	public String saveDept(DeptDTO deptDTO, Model model) {
		System.out.println("/dept/saveDept start");
		System.out.println("/dept/saveDept deptDTO->" + deptDTO);
		deptService.deptSave(deptDTO);
		return "dept/list";
	}
	
	@GetMapping("/list")
	public String mainPage(DeptDTO deptDTO, Model model) {
		System.out.println("/dept/list start");
		
		//Paging 작업
		int total = deptService.totalDept();
		Paging page = new Paging(total, deptDTO.getCurrentPage());
		deptDTO.setStart(page.getStart());
		deptDTO.setEnd(page.getEnd());
		
		List<DeptDTO> dept = deptService.deptList(deptDTO);
		model.addAttribute("deptList",dept);
		model.addAttribute("page",page);
		return "dept/list";
	}
	// 목록/삭제/수정 화면
	@GetMapping("/deptModify")
	public String deptModify(DeptDTO deptDTO, Model model) {
		log.info("deptModify Start with "+ deptDTO);
		DeptDTO dept = deptService.deptModify(deptDTO);
		log.info("Jpa after dept: "+dept);
		model.addAttribute("dept",dept);
		return "dept/deptModify";
	}
	// 수정 작업
	@PostMapping("/deptUpdate")
	public String deptUpdate(DeptDTO deptDTO) {
		log.info("deptUpdate deptDTO: "+deptDTO);
		deptService.deptUpdate(deptDTO);
		// redirect -> 컨트롤러 간다( redirect 없으면 : jsp로 이동 )
		// /dept/list: 절대 경로  ||| dept/list: 상대경료
		return "redirect:/dept/list";
	}
	// 삭제(업데이트,1) 작업
	@GetMapping("/deptDelete")
	public String deptDelete(int dept_code) {
		log.info("deptDelete Start");
		deptService.deptDelete(dept_code);
		return "redirect:/dept/list";
	}

}
