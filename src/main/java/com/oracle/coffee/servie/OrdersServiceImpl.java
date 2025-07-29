package com.oracle.coffee.servie;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dto.order.OrdersDto;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Override
	public int upsert(OrdersDto order) {
		if (order.getOrder_code() == 0) {
			// create
			
		} else {
			// update
		}
		
		return 0;
	}

}
