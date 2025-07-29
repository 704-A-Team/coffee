package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDto {
	
	private int 				emp_no; 					//사원코드
	private String				emp_name; 					//사원명
	private String 				emp_tel; 					//사원전화번호
	private int 				emp_dept_code;				//부서코드
	private int 				emp_grade;					//직급
	private int 				emp_sal; 					//급여
	private String 				emp_email; 					//이메일
	private boolean 			emp_isDel; 					//퇴직여부
	private int 				emp_register; 				//등록자(사원코드)
	private LocalDateTime 		emp_reg_date; 				//등록일
	
}
