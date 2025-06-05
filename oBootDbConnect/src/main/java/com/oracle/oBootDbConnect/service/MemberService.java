package com.oracle.oBootDbConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootDbConnect.domain.Member7;
import com.oracle.oBootDbConnect.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Autowired
	// 생성자를 만들어서 memberRepository야 오류 해결 ? -- 11:34 6/5
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 회원가입 
	public Long memberSave(Member7 member7) {
		Member7 member = memberRepository.save(member7);
		System.out.println("MemberService memberSave End");
		// member7.setId(1L); // Dump
		return member.getId();
	}
	
	// 전체 회원 조회
	public List<Member7> findMembers() {
		System.out.println("MemberService findMembers start");
		return memberRepository.findAll();
	}
}
