package com.oracle.coffee.security.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.service.EmpService;
import com.oracle.coffee.service.ClientService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserViewAdvice {

    private final EmpService empService;
    private final ClientService clientService;

    @ModelAttribute("displayName")
    public String displayName(@AuthenticationPrincipal AccountDto p) {
        if (p == null) return null;

        // 사원인 경우
        if (p.getEmp_code() != 0) {
            EmpDto emp = empService.getSingleEmp(p.getEmp_code());
            return (emp != null && emp.getEmp_name() != null && !emp.getEmp_name().isBlank())
                    ? emp.getEmp_name()
                    : p.getUsername();
        }

        // 거래처인 경우
        if (p.getClient_code() != 0) {
            ClientDto client = clientService.getSingleClient(p.getClient_code());
            return (client != null && client.getClient_name() != null && !client.getClient_name().isBlank())
                    ? client.getClient_name()
                    : p.getUsername();
        }

        // 둘 다 아니면 username 사용
        return p.getUsername();
    }
}
