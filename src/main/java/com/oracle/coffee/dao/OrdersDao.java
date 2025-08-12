package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

public interface OrdersDao {
	List<OrdersProductDto> getProducts();
	void createOrders(OrdersDto order);
	void createOrdersDetails(List<OrdersDetailDto> ordersdetails);
	OrdersDto findByCode(int orderCode);
	void updateOrders(OrdersDto order);
	void deleteOrdersDetails(int orderCode);
	void requestOrders(OrdersDto order);
	void deleteOrders(int orderCode);
	int totalCount();
	int totalCount(int clientCode);
	List<OrdersListDto> list(OrdersPageDto page);
	void refuseOrders(OrdersDto order);
	void approveOrders(OrdersDto order);
	List<Integer> approveOrdersDetails(int order_code);
}
