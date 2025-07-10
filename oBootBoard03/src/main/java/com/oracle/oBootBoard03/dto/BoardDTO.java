package com.oracle.oBootBoard03.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class BoardDTO {
	private int 		board_no;
	private String 		title;
	private String 		content;
	private int 		emp_no;
	private int 		read_count;
	private int 		ref;
	private int 		re_step;
	private int 		re_lvl;
	private LocalDate 	in_date;
}
