package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;

public interface OrdersService {
	public List<OrdersProductDto> getProducts();
	public int upsertImpormation(OrdersDto order);
	public OrdersDto get(int orderCode);
	public int approve(int orderCode);
	public int refuseOrCancel(int orderCode);
	public int request(OrdersDto order);
}
