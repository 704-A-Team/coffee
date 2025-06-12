package com.oracle.oBootJpa02.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.service.MemberService;

@Controller
public class MemberController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/members/new")
	public String createForm() {
		System.out.println("MemberController /members/new start");
		return "members/createMemberForm";
	}
	
	@PostMapping("/memberSave")
	public String memberSave(Member member) {
		System.out.println("MemberController create start");
		System.out.println("member->"+member);
		System.out.println("member.getName()-> "+member.getName());
		memberService.memberSave(member);
		return "redirect:/";
	}
	
	
	@GetMapping("/members")
	public String listMember(Model model) {
		List<Member> memberList = memberService.getListAllMember();
		System.out.println("memberList.get(0).getName()-> "+memberList.get(0).getName());
		
		model.addAttribute("memberList", memberList);
		return "members/memberList";
		
	}
	
	@GetMapping("/members/search")
	public String search(Member member, Model model) {
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		//memberRepository.findByNames(searchName);
		System.out.println("MemberController /members/search memberList.size()-> "+memberList.size());
		model.addAttribute(memberList);
		return "members/memberList";
	}
	
	@GetMapping("/memberModifyForm")
	public String memberModify(Member member, Model model) {
		System.out.println("MemberController memberModifyForm id->"+member.getId());
		Member member3 = memberService.findByMember(member.getId());
		
		System.out.println("member3-> "+member3);
		model.addAttribute("member", member3);
		
		return "members/memberModify";
	}
	
	@PostMapping("/members/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberController memberUpdate member->"+member);
		memberService.memberUpdate(member);
		System.out.println("MemberController memberUpdate memberService.memberUpdate after");
		return "redirect:/members";
	}
	
	@GetMapping("/findByListMembers")
	public String findByListMembers(Member member, Model model) {
		List<Member> memberList = memberService.getListFindByMembers(member);
		System.out.println("memberList.get(0).getName()->"+memberList.get(0).getName());
		System.out.println("memberList.get(0).getTeam().getName()->"+memberList.get(0).getTeam().getName());
		model.addAttribute("memberList", memberList);
		return "members/memberList";
	}
	
}
