package com.oracle.oBootJpa02.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {
	
	private final EntityManager em;
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	
	@Override
	public Member memberSave(Member member) {
		// 1.팀 저장 ( parent 먼저 )
		Team team = new Team();                         // 새 Team 객체를 만들고
		team.setName(member.getTeamname());				// Form에서 온 "신라" 세팅
		em.persist(team);								// → DB에 저장 → team_id = 1(새로운 시퀀스 값)
		// 2.회원 저장
		member.setTeam(team);							// ★ 이 새로 만든 팀 객체를 Member에 연결
		em.persist(member);								// Member 저장 (team_id = 1)
		return member;
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m From Member m", Member.class).getResultList();
		return memberList;
	}


	@Override
	public List<Member> findByNames(String searchName) {
		String search = searchName + '%';
		List<Member> memberList = em.createQuery("select m from Member m Where name Like :name",Member.class).setParameter("name", search).getResultList();
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
		System.out.println("JpaMemberRepository updateByMember 1 member->"+member);
		Member member3 = em.find(Member.class, member.getId());
		// 존재하면 수정
		if(member3 != null) {
			// 팀 저장
			System.out.println("JpaMemberRepository updateByMember member.getTeamid()->"+member.getTeamid());
			Team team = em.find(Team.class, member.getTeamid());
			if(team != null) {
				team.setName(member.getTeamname());
				em.persist(team);
			}
			// 회원 저장
			member3.setTeam(team);
			member3.setName(member.getName());
			em.persist(member3);
			result = 1;
		} else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist");
		}
		return result;
	}


	@Override
	public List<Member> findByMembers(Long pid, Long psal) {
		System.out.println("JpaMemberRepository findByMembers pid->"+pid);
		System.out.println("JpaMemberRepository findByMembers psal->"+psal);
		List<Member> memberList = em.createQuery("select m from Member m where id > :id and sal > :sal",Member.class).setParameter("id", pid).setParameter("sal", psal).getResultList();
		System.out.println("JpaMemberRepository memberList.size()"+memberList.size());
		return memberList;
	}

}
