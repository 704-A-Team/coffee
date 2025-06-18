package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberJpaController {
	private final MemberJpaService memberJpaService;
	
	// Mybatis랑 JPA 섞어써도 된다는 소리
		@RequestMapping("/memberJpa/new")
		public String createForm() {
			System.out.println("EmpController memberJpa Start");
			return "memberJpa/createMemberForm";
		}
		
}
