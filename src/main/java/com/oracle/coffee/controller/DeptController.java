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
		System.out.println("deptList 시작");
		Long totalCountLong = deptService.totalDept();
		int totalCountInt = totalCountLong.intValue();
		Paging page = new Paging(totalCountInt, deptDto.getCurrentPage());

		deptDto.setStart(page.getStart());   
		deptDto.setEnd(page.getEnd());      
		
		List<DeptDto> deptDtoList = deptService.deptList(deptDto);

		model.addAttribute("totalCount", totalCountInt);
		model.addAttribute("deptDtoList" , deptDtoList);
		model.addAttribute("page", page);

		return "dept/deptList";
	}
	
	
	@GetMapping("/deptInForm")
	public String deptInForm() {
		System.out.println("deptInForm 시작");
		return "dept/deptInForm";
	}

	@PostMapping("/saveDept")
	public String saveDept(DeptDto deptDto) {
		deptService.deptSave(deptDto);
		return "redirect:deptList";
	}
	

	@GetMapping("/deptDetail")
	public String deptDetail (DeptDto deptDto, Model model) {
		DeptDto deptDetail = deptService.getSingleDept(deptDto.getDept_code());
		model.addAttribute("deptDto", deptDetail);

		return "dept/deptDetail";
	}
	
	@GetMapping("/modifyForm")
	public String deptModify (DeptDto deptDto, Model model) {
		DeptDto deptModify = deptService.getSingleDept(deptDto.getDept_code());
		model.addAttribute("deptDto", deptModify);
		
		return "dept/deptModifyForm";
	}
	
	@PostMapping("/deptUpdate")
	public String deptUpdate(DeptDto deptDto) {
		DeptDto deptUpdateDto = deptService.deptUpdate(deptDto);
		return "redirect:deptList";
	}
	
	@GetMapping("/deptDelete")
	public String deptDelete(DeptDto deptDto, Model model) {
		deptService.deptDelete(deptDto.getDept_code());
		
		return "redirect:deptList";
	}
}
