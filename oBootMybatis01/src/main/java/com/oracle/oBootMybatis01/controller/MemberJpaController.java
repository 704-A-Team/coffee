package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/memberJpa")
public class MemberJpaController {
	private final MemberJpaService memberJpaService;
	
	// Mybatis랑 JPA 섞어써도 된다는 소리
	@GetMapping("/new")
	public String createForm() {
		System.out.println("MemberJpaController memberJpa Start");
		return "memberJpa/createMemberForm";
	}
		
	@PostMapping("/save")
	public String create(Member member) {
		System.out.println("MemberJpaController create Start");
		System.out.println("MemberJpaController create member->"+member);
		memberJpaService.join(member);
		return "memberJpa/createMemberForm";
	}
	
	@GetMapping("/members")
	public String listMember (Model model) {
		System.out.println("MemberJpaController listMember Start");
		List<Member> members = memberJpaService.findAll();
		System.out.println("MemberJpaController listMember members"+members);
		
		
		model.addAttribute("members", members);
		
		return "memberJpa/memberList";
	}
	
	@GetMapping("/memberUpdateForm")
	public String memberUpdateForm(Member member1, Model model)  {
		// 6/19 11:22
		String rtnJsp="";
		Member member = null;
		System.out.println("MemberJpaController memberUpdateForm id->"+member1.getId());
		Optional<Member> maybeMember = memberJpaService.findById(member1.getId());
		// Member member = memberJpaService.findById(member1.getId());  --> Null:오류
		// Optional 을 쓰면 null 값이 넘어와도 오류나지 않는다
		if(maybeMember.isPresent()) {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is Not NUll");
			member = maybeMember.get();  // 	Optional에서 값을 꺼냄
			model.addAttribute("member", member);
			rtnJsp = "memberJpa/memberModify";
		} else {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is NUll");
			model.addAttribute("message", "member가 존재하지 않으니 입력부터 수행해 주세요");
			rtnJsp = "forward:/memberJpa/members";
		}
		return rtnJsp;
	}
	
	@GetMapping("/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberJpaController memberUpdate member->"+member);
		memberJpaService.memberUpdate(member);
		System.out.println("MemberJpaController memberUpdate After");
		// redirect는 루트부터 찾아가니까 /memberJpa/ 이것도 넣는다
		return "redirect:/memberJpa/members";
	}
		
}
