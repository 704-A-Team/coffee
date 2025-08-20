package com.oracle.coffee.dto.orders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	// mainPage 제품종류 개수 조회용
	private int 	productCnt;
	private String 	productName;
	
	// bunryu 테이블
	private String detail_cd_contents;		// 상태 내용
	private String product_cd_contents;		// 단위 내용
	
	// product 테이블
	private String product_name;	// 제품명
	private int product_order_pack;	// 판매 단위 수량
	private int product_unit;		// 단위 (ea, g, ml)
	
	// product_price 테이블
	private BigDecimal price;				// 현재 가격
	
	private boolean can_order;		// "수주 요청 전" 발주 가능 여부
	
	public void setOrder_ddate(String order_ddate) {
		
		if (order_ddate != null && !order_ddate.isEmpty()) {
			if (order_ddate.matches("\\d{8}")) {
				this.order_ddate = order_ddate;
			} else {
				LocalDate date = LocalDate.parse(order_ddate);
		        this.order_ddate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			}
		} else {
	        this.order_ddate = null;
	    }
	}
}
