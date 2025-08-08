package com.oracle.coffee.dto.orders;

import lombok.Getter;

@Getter
public class OrdersProductDto {
	// 판매용 제품
	private int product_code;
	private String product_name;
	private int price;
	private int product_order_pack;	// 판매 단위 수량
	private int product_unit;		// 단위 (ea, g, ml)
	
	private String cd_contents;		// 단위 내용
}
