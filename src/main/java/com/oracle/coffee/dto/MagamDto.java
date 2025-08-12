
package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
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
	
	// 마감목록 분류 조인용
	private String 	mlcontents;
	
	// 마감상세 분류 조인용
	private String 	mdcontents;
	
	// 월 마감 수량체크용
	private String 			product_name;
	private String 			unitName;
	private String 			monthYrmo; // setMonthYrmo()
}
