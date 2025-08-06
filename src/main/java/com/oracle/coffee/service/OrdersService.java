package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

public interface OrdersService {
	public List<OrdersProductDto> getProducts();
	public int upsertInformation(OrdersDto order);
	public OrdersDto get(int orderCode);
	// public int autoApprove(int orderCode);
	public int approve(int orderCode);
	public void refuseOrCancel(OrdersRefuseDto refuse);
	public void request(int orderCode);
	public void delete(int orderCode);
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page);
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page, int clientCode);
}
