package com.oracle.oBootJpa01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa01.domain.Member;
import com.oracle.oBootJpa01.repository.MemberRepository;

@Service
@Transactional
// @Transactional 은 서비스에 걸어야 한다
// DB 작업을 할 때 -->> 트랜젝션을 걸고하라 -->> 무결성 유지 
public class MemberService {
	// 서비스에서
	// DI -=> MemberRepository 연결
	private final MemberRepository memberRepository;
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 회원가입
	public Long memberSave(Member member) {
		System.out.println("MemberService memberSave member->"+member);
		Member member3 = memberRepository.memberSave(member);
		System.out.println("MemberService memberSave After");
		return member3.getId();
	}

	public List<Member> getListAllMember() {
		List<Member> listMember = memberRepository.findAllMember();
		System.out.println("MemberService getListAllMember listMember.size()-> "+listMember.size());
		return listMember;
	}

	public List<Member> getListSearchMember(String searchName) {
		System.out.println("MemberService getListSearchMember start");
		System.out.println("MemberService getListSearchMember searchName->"+searchName);
		List<Member> listMember = memberRepository.findByNames(searchName);
		System.out.println("MemberService getListSearchMember listMember.size()-> "+listMember.size());
		return listMember;
	}
}
