package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import com.oracle.coffee.domain.Dept;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptDto {
	
	private int					 dept_code;				//부서코드
	private String 				 dept_name;				//부서명
	private String 				 dept_tel;				//부서대표번호
	private boolean 			 dept_isDel;			//부서삭제구분
	private LocalDateTime		 dept_reg_date;			//등록일

	// 페이징 관련 
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	
	//페이지
	private String      currentPage;

	public DeptDto(Dept dept) {
		this.dept_code = dept.getDept_code();
		this.dept_name = dept.getDept_name();
		this.dept_tel = dept.getDept_tel();
		this.dept_isDel = dept.isDept_isDel(); // boolean은 is 사용
		this.dept_reg_date = dept.getDept_reg_date();
	}
}
