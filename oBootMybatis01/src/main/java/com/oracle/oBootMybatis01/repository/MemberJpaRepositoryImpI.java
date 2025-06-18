package com.oracle.oBootMybatis01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpI implements MemberJpaRepository {
	
	private final EntityManager em;
	
	@Override
	public Member save(Member member) {
		System.out.println("MemberJpaRepositoryImpI save Start");
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpI findAll Start");
		List<Member> memberList = em.createQuery("Select m From Member m", Member.class).getResultList();
		return memberList;
	}

}
