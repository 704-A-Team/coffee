package com.oracle.oBootJpa02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;
import com.oracle.oBootJpa02.service.MemberService;

//  9:32 AM
// 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
@SpringBootTest
@Transactional
public class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Rollback(value = true)
	// 저장 TEST
	public void memberSave() {
		// 1. 조건
		Member member = new Member();
		member.setTeamname("고구려3");
		member.setName("강이식3");
		member.setSal(7000L);
		// 2. 행위
		Member member3 = memberService.memberSave(member);
		// 3. 결과
		System.out.println("MemberServiceTest memberSave member3->"+member3);
	}
	
	@BeforeEach
	public void before() {
		System.out.println("MemberRepository before");
	}
	@Test
	public void memberFind() {
		// 1. 조건
		// 회원조회 --> 강감찬
		Long findId = 3L;
		// 2. 행위
		Member member = memberService.findByMember(findId);
		// 3. 결과
		System.out.println("MemberServiceTest memberFind member->"+member);
	}
}
