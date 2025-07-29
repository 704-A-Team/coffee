package com.oracle.coffee.dto.order;

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
}
