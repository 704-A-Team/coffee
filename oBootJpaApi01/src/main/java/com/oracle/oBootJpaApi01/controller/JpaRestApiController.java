package com.oracle.oBootJpaApi01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.spi.DirStateFactory.Result;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Controller
//@ResponseBody
// 위에 합친게 -> @RestController : rest api 일때는 rest컨트롤러 쓴다
@RestController
// logger 선언 같다
@Slf4j
//private static final Logger logger = LoggerFactory.getLogger(JpaRestApiController.class);
@RequiredArgsConstructor
public class JpaRestApiController {
	private final MemberService memberService;
	
	// Test용도
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("JpaRestApiController start");
		String hello = "안녕";
		log.info("log.info-> {}.",hello);
		//         StringConverter
		return hello;
	}
	//  Bad API
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1() {
		System.out.println("JpaRestApiController /restApi/v1/members membersVer1 start");
		List<Member> listMember = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v1/members listMember.size()->"+listMember.size());
		return listMember;
	}
	// {"id": 1,"name": "홍길동", "sal": 3000,"team": {"teamId": 1,"name": "율도국"}}
	/*
	우리가 컨트롤러에서 직접 Member 같은 엔티티 그대로 반환하면 위험할 수도 있거든 
	그래서 필요한 데이터만 추려서 DTO에 담고, 그걸 JSON으로 바꿔서 응답하는 거야~
	JSON : 	데이터를 주고받을 때 쓰는 간단한 텍스트 형식
	*/
	
	// Good  API
	// 목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v21/members")
	public Result membersVer21() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v21/members findMembers.size()"+findMembers.size());
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();
		// 이전 목적 : 반드시 필요한 Data 만 보여준다(외부 노출 최대한 금지)
		for(Member member : findMembers) {
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("/restApi/v21/members getName->"+memberRtnDto.getName());
			System.out.println("/restApi/v21/members getSal->"+memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("/restApi/v21/members resultList.size()->"+resultList.size());
		return new Result(resultList.size(), resultList);
	}
	
	// Good API  람다  Version
	// 목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v22/members")
	public Result membersVer22() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v22/members findMembers.size()"+findMembers.size());
		// 자바 8에서 추가한 스트림(Streams)은 람다를 활용할 수 있는 기술 중 하나
		List<MemberRtnDto> memberCollect =
				// 4:03
					findMembers.stream()
							   .map(member->new MemberRtnDto(member.getName(), member.getSal()))
							   .collect(Collectors.toList())
							   ;
		
		System.out.println("/restApi/v22/members memberCollect.size()-->"+memberCollect.size());
		return new Result(memberCollect.size(),memberCollect);
	}
	
	// postman ---> Body --> raw---> JSON	 
    //  예시    {	    "name" : "kkk222"	    }
	// @RequestBody : Json(member)으로 온것을  --> Member member Setting
	// 저장은 post 매핑   4:30 6/12
	@PostMapping("/restApi/v1/memberSave")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		System.out.println("JpaRestApiController /restApi/v1/memberSave member->"+member);
		log.info("member.getName()-> {}.",member.getName());
		log.info("member.getSal()-> {}.",member.getSal());
		
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id);
	}
	
	// 목적  : Entity Member member --> 직접 화면이나 API위한 Setting 금지
	// 예시  : @NotEmpty  -->	@Column(name = "userName")
	@PostMapping("/restApi/v2/memberSave")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest cMember) {
		System.out.println("JpaRestApiController api/v2/memberSave cMember->"+cMember);
		log.info("member.getName()->", cMember.getName());
		log.info("member.getSal()->", cMember.getSal());
		// 원하는 것들만 세팅해서 다시 Member에 입력   ( 기존(위)에는 Member 그대로 받아서 입력했지만 CreateMemberRequest 한번 걸쳐준것 -> 보안 )
		Member member = new Member();
		member.setName(cMember.getName());
		member.setSal(cMember.getSal());
		
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id);
	}
	
	/*
	 *   단일 Id 조회 API
	 *   URI 상에서 '{ }' 로 감싸여있는 부분과 동일한 변수명을 사용하는 방법
	 *   해당 데이터가 있으면 업데이트를 하기에 
	 *   Get요청이 여러번 실행되어도 해당 데이터는 같은 상태이기에 멱등
	 */
	
	//@PathVariable -> uri에 있는 값을 받는것 --> path값을 변수값으로 받겠다
	@GetMapping("/restApi/v15/members/{id}")
	public Member membersVer15(@PathVariable("id") Long id) {
		System.out.println("JpaRestApiController /restApi/v15/members/{id}->"+id);
		// DML 작업 할 것 아니면 DTO로 돌려주는 것이 맞다
		Member findMember = memberService.findByMember(id);
		System.out.println("JpaRestApiController /restApi/v15/members/ findMember->"+findMember);
		
		return findMember;
	}
	
	// UPDATE          ( @PutMapping , @DELETEMAPPINT -> REST 일때 쓴다 보통은 겟, 포스트 )
	@PutMapping("/restApi/v21/members/{id}")
	public UpdateMemberResponse updateMemberV21 (@PathVariable("id") Long id,
			@RequestBody @Valid UpdateMemberRequest uMember) {
		System.out.println("JpaRestApiController updateMemberV21 id -> "+id);
		System.out.println("JpaRestApiController updateMemberV21 uMember -> "+uMember);
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		Member findMember = memberService.findByMember(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
	}
	
	@DeleteMapping("/restApi/v21/deleteMembers/{id}")
	public CreateMemberResponse deleteMemberV21(@PathVariable("id") Long id) {
		System.out.println("JpaRestApiController deleteMemberV21 id->"+id);
		memberService.deleteMember(id);
		return new CreateMemberResponse(id);
	}
	
	
	@Data 
	// 12:17 static 권장 이유 : 독립적으로 쓸때
	static class UpdateMemberRequest {
		@NotEmpty
		private String name;
		private Long sal;
	}
	
	@Data
	@AllArgsConstructor
	class UpdateMemberResponse {
		private Long id;
		private String name;
		private Long sal;
	}
	
	@Data
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
		private Long sal;
	}
	
	@Data
	@RequiredArgsConstructor
	// Data에 @RequiredArgsConstructor 속해있다 생략 가능
	class CreateMemberResponse {
		private final Long id;
	}
	
	
	// Inner Class
	// 내부에서 호출되어 쓰여질 때 사용 (& 외부에서 불러쓸 일 없다 )
	// 3:08 --> private 안쓰는 이유
	// public or private 생략 가능
	@Data
	@AllArgsConstructor
	class MemberRtnDto {
		private String 	name;
		private Long	sal;
	}
	// T는 제네릭 타입 → 여기선 List<MemberRtnDto>로 추론되어 사용됨 3:15 ?
	// T는 들어올 타입들을 유연하게 처리
	@Data
	@RequiredArgsConstructor
	class Result<T> {
		private final int 	totCount; // 총 인원 수   , JSON
//		private final Long	totsal;
		private final T   	data;		// 배열
	}
	
}
