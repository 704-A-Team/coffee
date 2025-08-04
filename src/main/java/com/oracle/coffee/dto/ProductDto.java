package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
	private int 			product_code;
	private String 			product_name;
	private String			product_contents;
	private int				product_unit;
	private int				product_type;
	private int 			product_yield;
	private int				product_weight;
	private int				product_isorder;
	private int				product_pack;
	private int 			product_isdel;
	private int				product_reg_code;
	private LocalDateTime	product_reg_date;
	
	// 조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
	
	private String 		unitName;      // 제품 단위명
	private String 		typeName;      // 제품 유형명
	private String 		isorderName;   // 납품 여부명
	private String		regName;	   // 등록자명
	
	private String 		searchType;
	private String 		searchKeyword;
	
	
	
}
