package com.oracle.coffee.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.service.FindPasswordService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	

	private final FindPasswordService findPasswordService;


	@GetMapping(value = "/login" )
	public String login(
			@RequestParam(value = "error" , required = false) String error,
			@RequestParam(value = "exception" , required = false ) String exception,
			Model model
			) {
	       	model.addAttribute("error",error);
	        model.addAttribute("exception",exception);
	        
	        return "login/loginPage";

	}
	
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy()
        		                                             .getContext()
        		                                             .getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }

    @GetMapping(value="/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, 
    		                   @AuthenticationPrincipal AccountDto accountDto, 
    		                   Model model) {
        model.addAttribute("username", accountDto.getUsername());
        model.addAttribute("exception", exception);

        return "login/denied";
    }
    
    @GetMapping("/login/findPassword")
  	public String  findPassword(){    	
  		return "login/findPassword";
      }
    
    
    @PostMapping("/login/findPassword/request")
    public String requestFindPassword(
            @RequestParam("code")  Integer    emp_code,
            @RequestParam("name")  String emp_name,   
            @RequestParam("phone") String emp_tel,    
            RedirectAttributes ra) {

        var result = findPasswordService.resetEmpPasswordAndSend(emp_code, emp_name, emp_tel);
        if (result.success()) ra.addFlashAttribute("msg", result.message());
        else                  ra.addFlashAttribute("err", result.message());
        return "redirect:/login/findPassword";
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(RedirectAttributes ra) {
        ra.addFlashAttribute("err", "코드번호가 올바르지 않습니다.");
        return "redirect:/login/findPassword";
    }

    
}
