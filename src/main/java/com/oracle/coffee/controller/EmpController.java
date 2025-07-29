package com.oracle.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.EmpDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {
	
	
	
	@GetMapping("/empInForm")
	public String empInForm() {
		System.out.println("empInForm 시작");
		return "emp/empInForm";
	}

	/*
	 * @RequestMapping("/saveEmp") public String SaveEmp(EmpDto empDto,Model model)
	 * { System.out.println("saveEmp file start..."); }
	 */
	

	@GetMapping("/empList")
	public String empList(EmpDto empDto, Model model) {
		System.out.println("empList 시작");
		
		return "emp/empList";
	}
	
	
}
