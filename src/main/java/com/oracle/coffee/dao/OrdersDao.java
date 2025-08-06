package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;

public interface OrdersDao {
	List<OrdersProductDto> getProducts();
	void createOrders(OrdersDto order);
	void createOrdersDetails(List<OrdersDetailDto> ordersdetails);
	OrdersDto findByCode(int orderCode);
	void updateOrdersNote(OrdersDto order);
	void deleteOrdersDetails(int orderCode);
	void updateOrdersStatus(OrdersDto order);
}
