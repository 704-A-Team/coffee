package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import com.oracle.oBootJpaApi01.domain.Member;

public interface MemberRepository {
	Long			save(Member member);
	List<Member>	findAll();
	Member 			findByMember(Long memberId);
	Member 			findByBeforeMember(Long memberId);
	int     		updateByMember(Member member);
	void 			deleteById(Long id);
	
}
