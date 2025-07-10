package com.oracle.oBootBoard03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	// Main화면, Login, Find  PW(비번 찾기)
	
	@GetMapping("/")
	public String home() {
	    return "main";
	}
	
	@GetMapping("/main")
	public String mainPage() {
		System.out.println("MainController mainPage start");
		
		return "main";
	}
}
