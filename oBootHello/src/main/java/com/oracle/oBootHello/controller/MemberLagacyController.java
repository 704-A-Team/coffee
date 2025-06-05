package com.oracle.oBootHello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootHello.dto.Member1;
import com.oracle.oBootHello.service.MemberLegacyService;

@Controller
public class MemberLagacyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberLagacyController.class);
	
	// 1. 전통적인 방식
	MemberLegacyService memberLegacyService = new MemberLegacyService();
	
	@GetMapping(value = "memberLsave")
	public String save(Member1 member1)  {
		System.out.println("MemberLagacyController save start");
		System.out.println("MemberLagacyController member1-> "+ member1);
		Long id = memberLegacyService.memberSave(member1);
		// 스프링 부트에서는 redirect:/ -> static의 index를 찾기
		return "redirect:/";
	}

}
