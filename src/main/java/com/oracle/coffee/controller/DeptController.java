package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.DeptDto;
import com.oracle.coffee.service.DeptService;
import com.oracle.coffee.service.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dept")
public class DeptController {
	
	private final DeptService deptService;

	@GetMapping("/deptList")
	public String deptList(DeptDto deptDto, Model model) {
		//페이징 관련 
		Long totalCountLong = deptService.totalDept();
		int totalCountInt = totalCountLong.intValue();
		Paging page = new Paging(totalCountInt, deptDto.getCurrentPage());

		deptDto.setStart(page.getStart());   
		deptDto.setEnd(page.getEnd());      
		
		//부서 조회 
		List<DeptDto> deptDtoList = deptService.deptList(deptDto);

		model.addAttribute("totalCount", totalCountInt);
		model.addAttribute("deptDtoList" , deptDtoList);
		model.addAttribute("page", page);

		return "dept/deptList";
	}
	
	
	@GetMapping("/deptInForm")
	//부서 등록 
	public String deptInForm() {
		
		return "dept/deptInForm";
	}

	@PostMapping("/saveDept")
	//부서 저장
	public String saveDept(DeptDto deptDto) {
		deptService.deptSave(deptDto);
		
		return "redirect:deptList";
	}
	

	@GetMapping("/deptDetail")
	//부서 상세 
	public String deptDetail (DeptDto deptDto, Model model) {
		DeptDto deptDetail = deptService.getSingleDept(deptDto.getDept_code());
		model.addAttribute("deptDto", deptDetail);

		return "dept/deptDetail";
	}
	
	@GetMapping("/modifyForm")
	//부서 수정 폼 
	public String deptModify (DeptDto deptDto, Model model) {
		DeptDto deptModify = deptService.getSingleDept(deptDto.getDept_code());
		model.addAttribute("deptDto", deptModify);
		
		return "dept/deptModifyForm";
	}
	
	@PostMapping("/deptUpdate")
	//부서 수정 실행 
	public String deptUpdate(DeptDto deptDto) {
		deptService.deptUpdate(deptDto);
		
		return "redirect:deptList";
	}
	
	@GetMapping("/deptDelete")
	//부서 삭제
	public String deptDelete(DeptDto deptDto, Model model) {
		deptService.deptDelete(deptDto.getDept_code());
		
		return "redirect:deptList";
	}
}
