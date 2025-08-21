package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.service.EmpService;
import com.oracle.coffee.service.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {
	
	private final EmpService empService;
	
	//사원 목록
    @GetMapping("/empList")
    public String empList(EmpDto empDto, Model model) {

        // 총건수(검색 조건 반영)
        Long totalCountLong = empService.totalEmp(empDto);
        int totalCountInt = totalCountLong.intValue();
        // 페이징 관련
        Paging page = new Paging(totalCountInt, empDto.getCurrentPage());
        empDto.setStart(page.getStart());
        empDto.setEnd(page.getEnd());
        // 사원 목록 조회(검색+페이징 반영)
        List<EmpDto> empDtoList = empService.empList(empDto);
        model.addAttribute("totalCount", totalCountInt);
        model.addAttribute("empDtoList", empDtoList);
        model.addAttribute("page", page);
        //검색 파라미터 유지용
        model.addAttribute("searchType",   empDto.getSearchType());
        model.addAttribute("searchKeyword",empDto.getSearchKeyword());
        model.addAttribute("deptName",     empDto.getDeptName());
        model.addAttribute("gradeName",    empDto.getGradeName());

        return "emp/empList";
    }
	
    //사원 등록 폼 
	@GetMapping("/empInForm")
	public String empInForm() {
		
		return "emp/empInForm";
	}

    //사원 등록 
	@PostMapping("/saveEmp")
	public String saveEmp(EmpDto empDto) {
		empService.empSave(empDto);
		
		return "redirect:empList";
	}
	

    //사원 상세
	@GetMapping("/empDetail")
	public String empDetail (EmpDto empDto, Model model) {
		EmpDto empDetail = empService.getSingleEmp(empDto.getEmp_code());
		model.addAttribute("empDto", empDetail);

		return "emp/empDetail";
	}
    
    //사원 수정 폼
	@GetMapping("/modifyForm")
	public String empModify (EmpDto empDto, Model model) {
		EmpDto empModify = empService.getSingleEmp(empDto.getEmp_code());
		model.addAttribute("empDto", empModify);
		
		return "emp/empModifyForm";
	}
	
	//사원 수정
	@PostMapping("/empUpdate")
	public String empUpdate(EmpDto empDto) {
		empService.empUpdate(empDto);
		
		return "redirect:empList";
	}
	
	//퇴사 처리
	@GetMapping("/empDelete")
	public String empDelete(EmpDto empDto, Model model) {
		empService.empDelete(empDto.getEmp_code());
		
		return "redirect:empList";
	}
	
	
}
