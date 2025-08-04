package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseDto {
	private int 			purchase_code;
	private int 			purchase_client_code;
	private int 			purchase_status;
	private int 			purchase_perm_code;
	private String 			purchase_refuse;
	private int 			purchase_reg_code;
	private LocalDateTime	purchase_reg_date;
	
	private int				product_won_code;
	private int 			purchase_amount;
	private int 			purchase_danga;
	
	private int 			min_amount;
	private int 			auto_purchase_amount;
	
	//조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
	
	private String 		productName;
	private String 		clientName;
	private String 		unitName;
	private String 		statusName;
	private int 		provideAmount;
	private String		empPermName;
	private String		empRegName;
	//private int			totalPrice = purchase_danga*provideAmount/purchase_amount;
	
	private String 		searchType;
	private String 		searchKeyword;
	
}
