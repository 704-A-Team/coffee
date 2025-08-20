package com.oracle.coffee.dto.km;

import lombok.Data;

@Data
public class WonCodeLackDTO {
	private int 		product_won_code;	// 원재료코드
	private String 		product_name;	// 원재료코드
	private int 		required_amount;	// 원재료코드
	private int 		usable_stock;	// 원재료코드
	private int 		storege;	// 원재료코드
	
	private String		status_msg;
	

}
