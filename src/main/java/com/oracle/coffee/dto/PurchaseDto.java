package com.oracle.coffee.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseDto {
	private int 			purchase_code;
	private int 			purchase_client_code;
	private int 			purchase_type;
	private int 			purchase_status;
	private Date			purchase_ipgo_date;
	private int 			purchase_perm_code;
	private String 			purchase_refuse;
	private int 			purchase_reg_code;
	private Date			purchase_reg_date;
	
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
	private int			totalPrice;
	private String 		saupNum;
    private String 		bossName;
    private String 		clientTel;
    private String 		clientAddress;
    
    private int 		productCnt;
	
	private String 		searchType;
	private String 		searchKeyword;
	
	//security
	private String 		roles;
	
}
