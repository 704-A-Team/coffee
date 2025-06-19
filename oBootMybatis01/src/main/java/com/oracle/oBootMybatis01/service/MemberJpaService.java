package com.oracle.oBootMybatis01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.repository.MemberJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberJpaService {

	private final MemberJpaRepository memberJpaRepository;
	
	public void join(Member member) {
		System.out.println("MemberJpaService join Start");
		memberJpaRepository.save(member);
	}

	public List<Member> findAll() {
		System.out.println("MemberJpaService findAll Start");
		List<Member> members = memberJpaRepository.findAll();
		System.out.println("MemberJpaService findAll members.size()->"+members.size());
		return members;
	}

	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaService findById Start");
		Optional<Member> member = memberJpaRepository.findById(memberId);
		return member;
	}

	public void memberUpdate(Member member) {
		System.out.println("MemberJpaService memberUpdate Start");
		memberJpaRepository.updateByMember(member);
		System.out.println("MemberJpaService memberUpdate After");
		return;
	}
		
}
