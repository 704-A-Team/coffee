package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
	private final EntityManager em;
	
//	@Autowired
//	public JpaMemberRepository(EntityManager em) {
//		this.em = em;
//	}    12:35  ---  >  @RequiredArgsConstructor를 사용 시!!
//                         private final 선언만 하여도 기본 생성자를 만들어 준다
//                      final 이란 필수로 요구하기 때문에 이름에 Required를 쓴다
	
	
	@Override
	public Long save(Member member) {
		System.out.println("JpaMemberRepository save before");
		em.persist(member);
		return member.getId();
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
				                    .getResultList();
		System.out.println("JpaMemberRepository findAll memberList.size()"+memberList.size());
		return memberList;
	}

}
