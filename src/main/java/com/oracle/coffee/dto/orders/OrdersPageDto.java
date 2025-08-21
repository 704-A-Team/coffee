package com.oracle.coffee.dto.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersPageDto {
	private int start;
	private int end;
	private String keyword;
	private int client_code;
}
