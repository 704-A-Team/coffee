package com.oracle.oBootJpa01.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootJpa01.domain.Member;
import com.oracle.oBootJpa01.service.MemberService;

@Controller
public class MemberController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
	
	// DI -> 서비스
	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	@GetMapping(value = "/members/new")
	public String createForm() {
		System.out.println("MemberController /members/new start");
		return "members/createMemberForm";
	}
	
	@PostMapping(value = "/members/save")
	public String memberSave(Member member) {
		System.out.println("MemberController memberSave start");
		System.out.println("MemberController memberSave memeber->"+member);
		System.out.println("MemberController memberSave memeber.getId()->"+member.getId());
		System.out.println("MemberController memberSave memeber.getName()->"+member.getName());
		Long id = memberService.memberSave(member);
		System.out.println("MemberController memberSave After id->"+id);
		return "redirect:/";
	}
	
	// get은 조회한다
	@GetMapping(value = "/members")
	public String listMember (Model model)  {
		List<Member> memberList = memberService.getListAllMember();
		LOGGER.info("memberList.size{}-> "+memberList.size());
		model.addAttribute("members", memberList);
		return "members/memberList";
	}
	
	@PostMapping(value = "/members/search")
	// 			<input> 태그로 받아도 Member로 받는 이유 :
	//                         Member에 name(받을 파라메타 존재)이(가) 있으면 된다 
	public String membersSearch(Member member, Model model) {
		System.out.println("/members/search member.getName()-> "+member.getName());
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		System.out.println("/members/search memberList.size()-> " +memberList.size());
		model.addAttribute("members", memberList);
		return "members/memberList";
	}
}
