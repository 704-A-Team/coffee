package com.oracle.oBootJpa01.repository;

import java.util.List;

import com.oracle.oBootJpa01.domain.Member;

public interface MemberRepository {
	// save 와 select(조회
	
	List<Member>    findAllMember();

	Member memberSave(Member member);

	List<Member> findByNames(String searchName);
}
