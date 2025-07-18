package com.oracle.oBootSecurity01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String home() {
		System.out.println("HomeController home start");
		return "main";
	}
	
	@GetMapping(value = "/user")
	public String user() {
		System.out.println("HomeController user start");
		return "user";
	}
	
	@GetMapping(value = "/manager")
	public String manager() {
		System.out.println("HomeController manager start");
		return "manager";
	}
	
	@GetMapping(value = "/admin")
	public String admin() {
		System.out.println("HomeController admin start");
		return "admin";
	}
	
	@GetMapping(value = "/admanager")
	public String admanager() {
		System.out.println("HomeController admanager start");
		return "admanager";
	}
	
}
