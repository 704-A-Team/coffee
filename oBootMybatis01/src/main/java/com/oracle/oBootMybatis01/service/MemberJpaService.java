package com.oracle.oBootMybatis01.service;

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
		
}
