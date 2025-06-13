package com.oracle.oBootJpaApi01.repository;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

	@Override
	public int updateByMember(Member member) {
		int result = 0;
		Member member3 = em.find(Member.class, member.getId());
		
		if(member3 != null) {
			// 회원 저장
			member3.setName(member.getName());
			member3.setSal(member.getSal());
			result = 1;
			System.out.println("JpaMemberRepository updateByMember update");
		} else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist");
		}
		
		return result;
	}

	@Override
	public void deleteById(Long id) {
		System.out.println("JpaMemberRepository deleteById before");
		Member member3 = em.find(Member.class, id);
		// remove(Object) 이기 때문에 객체를 만들어서 전달
		// remove(id) -> X
		em.remove(member3);
	}

	// Optional 문법 설명용 12:37 6/13
	@Override
	public Member findByBeforeMember(Long pid) {
		System.out.println("JpaMemberRepository findByBeforeMember before...");
		System.out.println("JpaMemberRepository findByBeforeMember pid->"+pid);
		Member member = null;
		Optional<Member> maybeMember = null;
		
		// Member member = em.find(Member.class, pid);
		try {
			maybeMember =  Optional.ofNullable(
					 em.createQuery("	select m from Member m"
								  + "	where m.id = (select max(m2.id) "
							 	  + "	              from   Member m2 "
								  + "	              where id < :pid) ", Member.class)
				       .setParameter("pid",pid)
				       .getSingleResult()
		     )
			 ;
			
		} catch (Exception e) {
			System.out.println("JpaMemberRepository findByBeforeMember maybeMember is null");

		}
		// 현재의 member id가 가장 적은 id(최소의 id)
		if (maybeMember != null ) {
			member = maybeMember.get();
		} else {
			member = findByMember(pid);
		}
	
  	    System.out.println("JpaMemberRepository findByBeforeMember member->"+member);

		return member;
	}

}
