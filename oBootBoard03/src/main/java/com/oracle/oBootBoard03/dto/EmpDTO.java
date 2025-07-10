package com.oracle.oBootBoard03.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpDTO {
	private int 		emp_no;
	private String		emp_password;
	private String 		emp_name;
	private String 		email;
	private String 		emp_tel;
	private long 		sal;
	private boolean 	del_status;
	private int 		dept_code;
	private LocalDate 	in_date;

}
