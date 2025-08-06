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
	private int 			product_type;
	private Long 			product_yield;
	private int 			product_isdel;
	private int 			product_weight;
	private int 			product_isorder;
	private int 			product_pack;
	private int 			product_reg_code;
	private LocalDateTime 	product_reg_date;
	
	// 재고수량 계산용
	private int InventoryCount;
	
	// 분류 테이블 조인용
	private String unitName;
	private String typeName;
	private String isorderName;
	
	
	// 조회용
	private String 	search;
	private String 	pageNum;
	private int 	start;
	private String 	keyword;
	private int 	end;
	
	// Page 정보 
	private String 	currentPage;
}
