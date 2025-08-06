package com.oracle.coffee.dto.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersPageDto {
	private int start;
	private int end;
	private int client_code;
	
	public OrdersPageDto(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public OrdersPageDto(int start, int end, int client_code) {
		this.start = start;
		this.end = end;
		this.client_code = client_code;
	}
}
