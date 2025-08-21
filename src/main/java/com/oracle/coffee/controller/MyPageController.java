package com.oracle.coffee.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.security.user.service.UserService;
import com.oracle.coffee.service.ClientService;
import com.oracle.coffee.service.EmpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/MyPage")
@RequiredArgsConstructor
public class MyPageController {

    private final EmpService empService;
    private final ClientService clientService;
    private final UserService userService;

    //일반 사원 마이페이지 
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")      
    public String mypageUser(Model model,	
                             @AuthenticationPrincipal AccountDto accountDto) {
        int empCode = accountDto.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        
        return "MyPage/MyPage_user";
    }
    
    //관리자(부장 이상) 마이페이지 
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")    
    public String mypageManager(Model model,	
                             @AuthenticationPrincipal AccountDto accountDto) {
        int empCode = accountDto.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        
        return "MyPage/MyPage_manager";
    }
    
    //공급처 마이페이지
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/client")     
    public String mypageClient(Model model,	@AuthenticationPrincipal AccountDto accountDto) {
        int clientCode = accountDto.getClient_code();
        ClientDto clientDto = clientService.getSingleClient(clientCode);
        model.addAttribute("clientDto", clientDto);
        
        return "MyPage/MyPage_client";
    }
    
    //가맹점 마이페이지
    @PreAuthorize("hasRole('ROLE_CLIENT2')")
    @GetMapping("/client2")     
    public String mypageClient2(Model model,	@AuthenticationPrincipal AccountDto accountDto) {
        int clientCode = accountDto.getClient_code();
        ClientDto clientDto = clientService.getSingleClient(clientCode);
        model.addAttribute("clientDto", clientDto);
        
        return "MyPage/MyPage_client2";
    }
   
    //일반 사원 개인정보변경 페이지
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changeInformationUser")
    public String mypagechangeEmp(Model model, @AuthenticationPrincipal AccountDto accountDto) {
        int empCode = accountDto.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        
    	return "MyPage/changeInformation_user"; 	
    }
 
    //일반 사원 개인정보 수정
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/UserUpdate")
	public String empUpdate(EmpDto empDto) {
		empService.empUpdate(empDto);
		
		return "MyPage/MyPage_user";
	}
    
    //관리자 개인정보변경 페이지
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/changeInformationManager")
    public String mypagechangeEmp2(Model model, @AuthenticationPrincipal AccountDto accountDto) {
        int empCode = accountDto.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        
    	return "MyPage/changeInformation_manager"; 	
    }
 
    //관리자 개인정보 수정
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/ManagerUpdate")
	public String empUpdate2(EmpDto empDto) {
		empService.empUpdate(empDto);
		
		return "MyPage/MyPage_manager";
    }
    
    //공통 비밀번호 찾기 페이지
    @GetMapping("/findPassword")
	public String  findPassword(){
    	
		return "MyPage/findPassword";
    }
    
    //비밀번호 변경 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/changePassword")
    public String mypagePassword(Model model, @AuthenticationPrincipal AccountDto accountDto) {
    	model.addAttribute("AccountDto", accountDto);
    	
    	return "MyPage/changePassword";    	
    }


    //비밀번호 변경 
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/PasswordChanged")
    public String changePassword(
            @AuthenticationPrincipal AccountDto accountDto,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword, 
            RedirectAttributes ra) {

    	//비밀번호 확인
        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "새 비밀번호와 확인이 일치하지 않습니다.");
            
            return "redirect:/MyPage/changePassword";
        }

        try {
            userService.changePassword(accountDto.getUsername(), currentPassword, newPassword);
            ra.addFlashAttribute("success", "비밀번호가 변경되었습니다. 다시 로그인해주세요.");
            //세션을 위해 비밀번호 변경되었을 경우 로그아웃 
            
            return "redirect:/logout";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            
            return "redirect:/MyPage/changePassword";
        }
    }


}
