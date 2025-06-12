package com.oracle.oBootJpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Getter
//@Setter
//@ToString
// 위에 한방 처리하기
@Data

// 시퀀스 만들기 12:40 6/11       12:46
@SequenceGenerator(
					name = "member_seq_gen" , // seq 객체
					sequenceName = "member_seq_generator", // seq DB
					initialValue = 1,
					allocationSize = 1
				  )
@Table(name = "Member2")
public class Member {
	@Id
	@GeneratedValue(
					 strategy = GenerationType.SEQUENCE ,
					 generator = "member_seq_gen"
			        )
	@Column(name = "member_id", precision = 10)
	private Long 	id;
	@Column(name = "userName", length = 50)
	private String 	name;
	private Long 	sal;
	
	// 관계 설정
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	
	// 3:18 실제 컬럼으로 잡히지 않는다
	// 실제Column X --> Buffer용도
	@Transient
	private String teamname;
	@Transient
	private Long teamid;
}
