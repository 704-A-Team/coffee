package com.oracle.coffee.dto.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDetailDto {
	private int order_code;
	private int product_code;
	private int order_amount;
	private String order_ddate;
	private int order_datail_status;
	
	// join 조회용
	
	// bunryu 테이블
	private String detail_cd_contents;		// 상태 내용
		
	// product 테이블
	private String product_name;	// 제품명
	private String price;			// 현재 가격 / 수주 승인 가격 
	
	private boolean can_order;		// "수주 요청 전" 발주 가능 여부
	
	// orders 테이블에 승인 날짜가 없음
}
