package com.oracle.oBootBoard03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {
	
	@GetMapping("/list")
	public String mainPage() {
		System.out.println("EmpController list start");
		
		return "emp/list";
	}

}
