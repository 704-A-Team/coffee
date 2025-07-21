package com.oracle.oBootSecurity01.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@Secured("ROLE_GUEST")
	@GetMapping(value = "/guest1")
	public String guest1() {
		System.out.println("HomeController guest1 start");
		return "guest";
	} // guest는 내부적으로 모든 role이 접근 가능한 것으로 확인됨
	
	@Secured({"ROLE_GUEST","ROLE_ADMIN","ROLE_MANAGER"})
	@GetMapping(value = "/guest3")
	public String guest3() {
		System.out.println("HomeController guest3 start");
		return "guest";
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	@GetMapping(value = "/guest5")
	public String guest5() {
		System.out.println("HomeController guest5 start");
		return "guest";
	} // 이건 접근권한 된다
	
}
