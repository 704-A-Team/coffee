package com.oracle.oBootSecurity01.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootSecurity01.domain.Account;
import com.oracle.oBootSecurity01.model.AccountDTO;
import com.oracle.oBootSecurity01.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;  // 암호화 처리에 사용한다
	
	@PostMapping(value = "/signup")
	public String signUp(AccountDTO accountDTO) {
		System.out.println("UserController signUp Start");
		System.out.println("UserController signUp  accountDTO->"+accountDTO);
		ModelMapper mapper = new ModelMapper();
		Account account = mapper.map(accountDTO, Account.class);
		// accountDto password Encoder
		// 									encode-> spring 내부에서 해시 암호화 해준다
		account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
		System.out.println("UserController signUp accountDTO 2->  "+accountDTO);
		userService.createUser(account);
		
		return "redirect:/";
	}
	
}
