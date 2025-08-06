package com.oracle.coffee.dto.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersRefuseDto {
	private int order_code;
	private String reason;
	private int order_perm_code;
}
