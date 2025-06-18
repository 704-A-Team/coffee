package com.oracle.oBootMybatis01.repository;

import java.util.List;

import com.oracle.oBootMybatis01.domain.Member;

public interface MemberJpaRepository {
	Member			save(Member member);
	List<Member> 	findAll();
}
