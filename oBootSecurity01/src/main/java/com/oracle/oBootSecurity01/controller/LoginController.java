package com.oracle.oBootSecurity01.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.oBootSecurity01.model.AccountDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	
	@GetMapping(value = "/signup")
	public String singUp() {
		return "login/signup";
	}
	
	@GetMapping(value = "/login")
	public String login(@RequestParam(value = "error" ,required = false) String error,
						@RequestParam(value = "exception" ,required = false) String exception,
						Model model
						) {
		System.out.println("LoginController login start");
		model.addAttribute("error",error);
		model.addAttribute("exception", exception);
		
		return "login/loginPage";
	}
	
	@GetMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContextHolderStrategy()
															 .getContext()
															 .getAuthentication();
		System.out.println("LoginController logout authentication->" +authentication);
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		return "redirect:/login";
	}
	
	@GetMapping(value = "/denied")
	public String accessDenied( @RequestParam(value = "exception", required = false) String exception,
								@AuthenticationPrincipal AccountDTO accountDTO,
								Model model
								) {
		
		System.out.println("LoginController accessDenied accountDTO->" +accountDTO);
		System.out.println("LoginController accessDenied exception->" +exception);
		
		model.addAttribute("username", accountDTO.getUsername());
		model.addAttribute("exception", exception);
		
		return "login/denied";
	}
	
}
