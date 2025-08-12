package com.oracle.coffee.dto.orders;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDetailApprovedDto {
	private int order_code;
	private List<Integer> enabled_products;
}
