package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDto {
	private int 			product_code;
	private String 			product_name;
	private String  		product_contents;
	private int     		product_unit;
	private boolean 		product_type;
	private Long 			product_yield;
	private boolean 		product_isdel;
	private int 			product_weight;
	private boolean 		product_isorder;
	private int 			product_reg_code;
	private LocalDateTime 	product_reg_date;
	
	// 재고수량 계산용
	private int InventoryCount;
	
	// 조회용
	private String search;
	private String pageNum;
	private int start;
	private String keyword;
	private int end;
	
	// Page 정보 
	private String currentPage;
}
