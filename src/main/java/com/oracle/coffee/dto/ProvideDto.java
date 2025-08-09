package com.oracle.coffee.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProvideDto {
	private int 			provide_code;
	private int				product_won_code;
	private int				provide_client_code;
	private int				current_danga;
	private int				provide_amount;
	private int				provide_isdel;
	private Date			provide_reg_date;
	
	// 조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
	
	private String 		productName;
	private String 		clientName;
	private String 		unitName;
	
	private String 		searchType;
	private String 		searchKeyword;
}
