package com.oracle.coffee.VO;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseForm {
	private List<Integer> 			purchase_code;
	private List<Integer> 			purchase_client_code;
	private List<Integer> 			purchase_status;
	private List<Date>				purchase_ipgo_date;
	private List<Integer> 			purchase_perm_code;
	private String 					purchase_refuse;
	private int			 			purchase_reg_code;
	private List<Date>				purchase_reg_date;
	
	private List<Integer>			product_won_code;
	private List<Integer> 			purchase_amount;
	private List<Integer> 			purchase_danga;
	
	private List<Integer> 			min_amount;
	private List<Integer> 			auto_purchase_amount;
	
	//조회용
	private List<String>      	pageNum;  
	private List<Integer> 		start; 		 	   
	private List<Integer> 		end;
	private List<String>     	currentPage;
	
	private List<String> 		productName;
	private List<String> 		clientName;
	private List<String> 		unitName;
	private List<String> 		statusName;
	private List<Integer> 		provideAmount;
	private List<String>		empPermName;
	private List<String>		empRegName;
	
}
