package com.oracle.coffee.dto.km;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int 			board_code;
	private String 			board_title;
	private String 			board_contents;
	private int 			read_count;
	private int 			ref;
	private int 			ref_step;
	private int 			ref_lvl;
	private int 			board_reg_code;
	private LocalDateTime 	board_reg_date;
	
	// 조회용
	private int 		start;
	private int 		end;
	private String 		pageNum;
	private String		currentPage;
	
	//Join
	private String		dept_name;
}
