package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;

public interface OrdersService {
	public List<OrdersProductDto> getProducts();
	public int upsertInformation(OrdersDto order);
	public OrdersDto get(int orderCode);
	// public int autoApprove(int orderCode);
	public int approve(int orderCode);
	public void refuseOrCancel(int orderCode);
	public void request(int orderCode);
	public void delete(int orderCode);
	public List<OrdersListDto> list(PageRequestDto page);
	public List<OrdersListDto> list(PageRequestDto page, int clientCode);
}
