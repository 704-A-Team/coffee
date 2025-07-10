package com.oracle.oBootBoard03.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
					name = "deptcode_seq",				// 객체 (JPA 사용)
					sequenceName = "deptcode_seq_gen", 	// DB
					initialValue = 1000,
					allocationSize = 1
				)
@EntityListeners(AuditingEntityListener.class)
public class Dept {
	@Id
	@GeneratedValue(
					strategy = GenerationType.SEQUENCE,
					generator = "deptcode_seq"
				)
	private int 		dept_code;
	private String 		dept_name;
	private int 		dept_captain; // emp_no 를 가져온다
	private String 		dept_tel;
	private String 		dept_loc;
	@Builder.Default
	private boolean 	dept_gubun = false;	  					// 기본값: 0
	@CreatedDate
	private LocalDateTime 	in_date;	  	// 기본값: sysdate
	
	public void changeDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void changeDept_captain(int dept_captain) {
		this.dept_captain = dept_captain;
	}

	public void changeDept_tel(String dept_tel) {
		this.dept_tel = dept_tel;
	}

	public void changeDept_loc(String dept_loc) {
		this.dept_loc = dept_loc;
	}

	public void changeDept_gubun(boolean dept_gubun) {
		this.dept_gubun = dept_gubun;
	}

	public void changeIn_date(LocalDateTime in_date) {
		this.in_date = in_date;
	}

	
	
	
}
