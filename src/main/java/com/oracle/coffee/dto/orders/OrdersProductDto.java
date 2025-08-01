package com.oracle.coffee.dto.orders;

import lombok.Getter;

@Getter
public class OrdersProductDto {
	// 판매용 제품
	private int product_code;
	private String product_name;
	private int price;
}
