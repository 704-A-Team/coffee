package com.oracle.coffee.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.service.ClientService;
import com.oracle.coffee.service.EmpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/MyPage")
@RequiredArgsConstructor
public class MyPageController {

    private final EmpService empService;
    private final ClientService clientService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")      // 최종 URL 바로 명시
    public String mypageUser(Model model,	
                             @AuthenticationPrincipal AccountDto account) {
        int empCode = account.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        return "MyPage/MyPage_user";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")      // 최종 URL 바로 명시
    public String mypageManager(Model model,	
                             @AuthenticationPrincipal AccountDto account) {
        int empCode = account.getEmp_code();
        EmpDto empDto = empService.getSingleEmp(empCode);
        model.addAttribute("empDto", empDto);
        return "MyPage/MyPage_manager";
    }

    
}
