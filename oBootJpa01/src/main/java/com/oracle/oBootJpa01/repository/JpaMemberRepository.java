package com.oracle.oBootJpa01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {
	// JPA DML 작업을 위해 --> EntityManager 필수  ***
	//                        |-> DataSource와 비슷한 역할을 한다 (JDBC)
	// EntityManager 는 @Entity를 찾는다
	private final EntityManager em;
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Member> findAllMember() {
		//           createQuery 직접 쿼리를 쓸 수 있다             여기 Member 인 이유 (Member1 아닌 이유) -->
		//                                                      Class Member이다 ORM에서 O(bject 를 써야 한다!
		//                                            Member -> @Entity 클래스의 이름
		List<Member> memberList = em.createQuery("Select m from Member m",Member.class).getResultList();
		System.out.println("JpaMemberRepository findAllMember memberList.size()->"+memberList.size());
		return memberList;
	}

	@Override
	public Member memberSave(Member member) {
		// persist()는 JPA에서 "insert" 역할
		em.persist(member);  
		
		return member;
	}

	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + '%';
		System.out.println("JpaMemberRepository findByNames pname->"+pname);
		// JPQL
		List<Member> memberList = em.createQuery("select m from Member m Where name Like :name", Member.class).setParameter("name", pname).getResultList();
		System.out.println("JpaMemberRepository memberList.size()-> "+ memberList.size());
		return memberList;
	}

}
