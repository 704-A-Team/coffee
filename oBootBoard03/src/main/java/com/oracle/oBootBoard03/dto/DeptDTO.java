package com.oracle.oBootBoard03.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class DeptDTO {
	private int 		dept_code;
	private String 		dept_name;
	private int 		dept_captain; // emp_no 를 가져온다
	private String 		dept_tel;
	private String 		dept_loc;
	private boolean 	dept_gubun;
	private LocalDateTime 	in_date;
	// 조회용
	private int 		start;
	private int 		end;
	private String 		pageNum;
	private String		currentPage;
	
	public DeptDTO(int dept_code, String dept_name){
		this.dept_code = dept_code;
		this.dept_name = dept_name;
	}
}
