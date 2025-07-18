package com.oracle.oBootBoard03.dto;

import java.time.LocalDateTime;

import com.oracle.oBootBoard03.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int 			board_no;
	private String 			title;
	private String 			content;
	private int 			emp_no;
	private int 			read_count;
	private int 			ref;
	private int 			re_step;
	private int 			re_lvl;
	private LocalDateTime 	in_date;
	
	// 조회용
	private int 		start;
	private int 		end;
	private String 		pageNum;
	private String		currentPage;
}
