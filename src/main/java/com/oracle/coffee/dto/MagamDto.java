
package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MagamDto {
	
	private String 			month_yrmo;
	private int 			month_magam_status;
	private int 			product_code;
	private int 			month_magam_amount;
	private LocalDateTime 	month_magam_reg_date;
	
	// 조회용
	private String 	search;
	private String 	pageNum;
	private int 	start;
	private String 	keyword;
	private int 	end;
	
	// Page 정보 
	private String 	currentPage;
}
