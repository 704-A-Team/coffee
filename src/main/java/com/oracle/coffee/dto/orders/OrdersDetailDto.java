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
	private String product_cd_contents;		// 단위 내용
	
	// product 테이블
	private String product_name;	// 제품명
	private int product_order_pack;	// 판매 단위 수량
	private int product_unit;		// 단위 (ea, g, ml)
	
	// product_price 테이블
	private int price;				// 현재 가격
	
	private boolean can_order;		// "수주 요청 전" 발주 가능 여부
}
