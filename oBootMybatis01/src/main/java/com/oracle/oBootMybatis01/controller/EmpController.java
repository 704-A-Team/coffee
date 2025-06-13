package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.service.EmpService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmpController {
	private final EmpService es;
	
	@RequestMapping(value = "/listEmpStart")
	public String listEmpStart(Emp emp, Model model) {
		System.out.println("EmpController listEmpStart start");
		// 21
		int totalEmp = es.totalEmp();
		
		model.addAttribute("totalEmp", totalEmp);
		
		return "list";
	}
}
