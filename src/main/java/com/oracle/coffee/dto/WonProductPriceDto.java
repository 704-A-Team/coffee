package com.oracle.coffee.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WonProductPriceDto {
	private int 		product_code;
	private String		start_date;
	private int 		price_reg_code;
	private String		end_date;
	private BigDecimal 	price;
	private Date		price_reg_date;
	
	// 조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
	
	private String 		productName;
	private String 		unitName;      // 제품 단위명
	private String 		typeName;      // 제품 유형명
	private String		regName;	   // 등록자명
	
	private String 		searchKeyword;
	private String 		errorMsg;
}
