package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.OrdersDao;
import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersServiceImpl implements OrdersService {

	private final OrdersDao ordersDao;
	
	@Override
	public List<OrdersProductDto> getProducts() {
		// 판매 가능한 제품 목록
		// (isorder = true) && (isdel = false) && (price 존재)
		return ordersDao.getProducts();
	}
	
	@Override
	public int upsertImpormation(OrdersDto order) {
		// 수주 정보 저장
		
		int orderCode = 0;
		// create
		if (order.getOrder_code() == 0) {	
			// orders 생성
			ordersDao.createOrders(order);
			orderCode = order.getOrder_code();	
		
		// update
		} else {
			// orders 비고 변경
			ordersDao.updateOrdersNote(order);
			orderCode = order.getOrder_code();
			// ordersDetail (전체 엎어치기)
			ordersDao.deleteOrdersDetails(orderCode);
		}
		
		// ordersDetail 추가
		List<OrdersDetailDto> details = order.getOrders_details();
		for (OrdersDetailDto detail : details) {
			detail.setOrder_code(orderCode);
		}
		ordersDao.createOrdersDetails(details);		
		
		return orderCode;
	}

	@Override
	public OrdersDto get(int orderCode) {
		OrdersDto order = ordersDao.findByCode(orderCode);
		return order;
	}
	
	@Override
	public int request(OrdersDto order) {
		// 수주 요청 (가맹점 -> 본사)
		// 요청상태(1)
		
		// 수주 저장
		int orderCode = upsertImpormation(order);
		// 수주 상태 변경
		int targetStatus = 1;
		order.setOrder_status(targetStatus);
		ordersDao.updateOrdersStatus(order);
		return orderCode;
	}

	@Override
	public int approve(int orderCode) {
		// 수주 승인 상태로 변경 (요청 상태에서 가능)
		return 0;
	}

	@Override
	public int refuseOrCancel(int orderCode) {
		// 수주 취소/반려 상태로 변경 (요청 상태에서 가능)
		// 취소(가맹점): 취소상태(3) + 승인자 null + order_refuse null
		// 반려(본사): 취소상태(3) + 승인자 코드 존재 + 거부사유 존재
		
		
		return 0;
	}

}
