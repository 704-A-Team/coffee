package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

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
		System.out.println("MemberJpaRepositoryImpI findAll memberList->"+memberList);
		
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		// 6/19 11:22
		Member member = em.find(Member.class, memberId);
		return Optional.ofNullable(member);
	}

	@Override
	public void updateByMember(Member member) {
		// 1. update --> Merge  12:11
		//               현재 세팅된 값만 수정 , 나머지는 Null
		// em.merge(member);
		
		// 2. update --> Setter
		//               세팅값 수정, 기존 데이터 유지
		Member member3 = em.find(Member.class, member.getId());
		member3.setName(member.getName());  // 더티 체킹 ,
		System.out.println("MemberJpaRepositoryImpI updateByMember After");
	}

}
