package com.oracle.coffee.servie;

import com.oracle.coffee.dto.order.OrdersDto;

public interface OrdersService {
	public int upsert(OrdersDto order);
}
